package org.devdom.tracker;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.RawAPIResponse;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dao.FacebookDao;
import org.devdom.tracker.model.dao.GroupRatingDao;
import org.devdom.tracker.model.dto.FacebookComment;
import org.devdom.tracker.model.dto.FacebookMember;
import org.devdom.tracker.model.dto.FacebookMentions;
import org.devdom.tracker.model.dto.FacebookPost;
import org.devdom.tracker.model.dto.GroupInformation;
import org.devdom.tracker.util.Configuration;
import org.devdom.tracker.util.Utils;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class Worker implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(Worker.class .getName());
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    private final GroupRatingDao groupDao = new GroupRatingDao();
    private static final ConfigurationBuilder cb = Configuration.getFacebookConfig();
    private Facebook facebook;

    /**
     * 
     * @return
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }

    @Override
    public void run() {
        facebook = new FacebookFactory(cb.build()).getInstance();

        List<GroupInformation> groups = getGroupList();
        if(groups!=null){

            for(GroupInformation group : groups){
                try{
                    LOGGER.log(Level.INFO, "Buscando miembros el grupo {0}", group.getGroupName());
                    //getRawMembersInGroup(group.getGroupId()); // Actualizar miembros en grupo
                    Thread.sleep(1000);
                    LOGGER.log(Level.INFO, "Buscando post y comentarios del grupo {0}", group.getGroupName());
                    getRawPostsInGroup(group.getGroupId()); // Actualizar interacciones de los miembros de los distintos grupos
                }catch(FacebookException | JSONException ex){
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                Thread.sleep(500);
                LOGGER.info("Sincronizando data anual de los grupos");
                groupDao.updTablesInformationYear();
            } catch (Exception ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(GroupInformation group : groups){
                try{
                    updateGroupsInformation(group.getGroupId(),group.getGroupName(),group.getMinInteractions());
                }catch(Exception ex){
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            LOGGER.info("Finalizacion de actualizacion");
        }
    }
    
    
    /**
     * 
     * Llamada al API de Facebook para retornar un JSON crudo para ser manipulado
     * con informacion referente a los post del grupo que se especifique segun su ID
     * 
     * @param groupId
     * @throws FacebookException
     * @throws JSONException 
     */
    public void getRawPostsInGroup(String groupId) throws FacebookException, JSONException {

        EntityManager em = emf.createEntityManager();
        int countCommit = 0;
        try{
            String relURL = groupId + "/feed?fields=id,message,message_tags,name,created_time,from,likes.limit(1000).fields(id),"
                + "comments.limit(1000).fields(id,comment_count,message_tags,message,created_time,user_likes,"
                + "from,like_count,comments,likes.limit(1000).fields(id,name,pic_crop,picture)),picture,with_tags&limit=150";
            for(int p=0;p<2;p++){
                RawAPIResponse response = facebook.callGetAPI(relURL);
                JSONObject json = response.asJSONObject();

                JSONArray posts = json.getJSONArray("data");
                String nextPage = json.getJSONObject("paging").getString("next");
                LOGGER.log(Level.INFO, "(nextPage)===> {0}", nextPage);

                int len = posts.length();
                int startLength = nextPage.indexOf(groupId);
                relURL = nextPage.substring(startLength,nextPage.length());
                LOGGER.log(Level.INFO, "(nextPage)===> {0}", relURL);

                for(int i=0;i<len;i++){
                    if(!em.getTransaction().isActive()){
                        em.getTransaction().begin();
                    }
                    JSONObject post = posts.getJSONObject(i);
                    syncRawPost(groupId,post,em);

                    LOGGER.log(Level.INFO, "POST GROUP ID  => {0}", groupId);
                    LOGGER.log(Level.INFO, "POST ID  => {0}", post.getString("id"));
                    LOGGER.log(Level.INFO, "PAGE -> {0} + row -> {1}", new Object[]{p, i});
                    LOGGER.log(Level.INFO, "Posts ===> {0}", len);

                    if(countCommit>=99){
                        LOGGER.info("guardando informacion...");
                        em.getTransaction().commit();
                        countCommit=-1;
                    }
                    countCommit++;
                }
                if(!em.getTransaction().isActive())
                    em.getTransaction().begin();

                LOGGER.log(Level.INFO, "NEXT===> {0}", relURL);
            }
        }catch (InterruptedException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(em.getTransaction().isActive()){
                LOGGER.info("guardando informacion...");
                em.getTransaction().commit();
            }
            if(em.isOpen())
                em.close();
        }
    }

    /**
     * Sincronizar la informacion contenida en un post
     * 
     * @param post
     * @param em
     * @throws JSONException
     * @throws FacebookException 
     */
    private void syncRawPost(String groupId, JSONObject post, EntityManager em) throws JSONException, FacebookException{
        String id = post.getString("id");
        
        //String relURL = id + "?fields=id,message,message_tags,name,created_time,from,likes.limit(500).fields(id,name,username),comments.limit(1500).fields(from,id,like_count,message,message_tags,user_likes,created_time)";
        //RawAPIResponse response = facebook.callGetAPI(relURL);
        
        JSONObject json = post; //response.asJSONObject();

        String postId = json.getString("id").split("_")[1];
        String message = json.isNull("message")?"":json.getString("message");
        Date createdTime = Utils.getDateFormatted(json.getString("created_time"));
        String fromId = json.getJSONObject("from").getString("id");
        int likes = (!json.isNull("likes"))                        
                ?json.getJSONObject("likes").getJSONArray("data").length()                        
                :0; //revisar si existen likes en el post
        boolean hasMessages = !(json.isNull("comments")); // verificar si existen comentarios en el post
        boolean hasMentions = !(json.isNull("message_tags"));
        
        FacebookPost newPost = new FacebookPost();
        newPost.setPostId(postId);
        newPost.setFromId(fromId);
        newPost.setCreationDate(createdTime);
        newPost.setLikeCount(likes);
        newPost.setMessage(message);
        newPost.setGroupId(groupId);
        em.merge(newPost); // crear o actualizar un post existente 

        LOGGER.log(Level.INFO, "(POST) postId {0}", postId);
        LOGGER.log(Level.INFO, "(POST) fromId {0}", fromId);
        LOGGER.log(Level.INFO, "(POST) createdTime {0}", createdTime);
        LOGGER.log(Level.INFO, "(POST) likes {0}", likes);
        LOGGER.log(Level.INFO, "(POST) message {0}", message);
        LOGGER.log(Level.INFO, "(POST) groupId {0}", groupId);

        if(hasMessages)
            syncRawMessages(groupId, postId, json.getJSONObject("comments").getJSONArray("data"), em);

        /*
        El API de facebook retorna un formato diferente para extraer los mentions 
        valiendose de dos arreglos
        */
        if(hasMentions){ 
            JSONArray tags = json.getJSONObject("message_tags").names();
            int len = tags.length();

            for(int i=0;i<len;i++){
                String tag = tags.getString(i);
                //JSONArray mentions = json.getJSONObject("message_tags").getJSONArray(tag);
                //for(int x=0;x<mentions.length();x++){ 
                JSONArray mention = json.getJSONObject("message_tags").getJSONArray(tag);
                syncRawMentions(groupId, postId, fromId, "POST", mention, createdTime, em);
                //}
            }
        }
    }

    /**
     * 
     * Sincronizar mensajes que se contienen en un post
     * 
     * @param groupId
     * @param postId
     * @param comments
     * @param em
     * @throws JSONException 
     */
    private void syncRawMessages(String groupId, String postId, JSONArray comments, EntityManager em) throws JSONException {
        int len = comments.length();
        for(int i=0;i<len;i++){
            JSONObject message = comments.getJSONObject(i);
            boolean hasMentions = !(message.isNull("message_tags"));
            boolean hasFrom = !(message.isNull("from"));

            if(hasFrom){ // Revisar si el comentario tiene el id del creador 
                FacebookComment newComment = new FacebookComment();
                Date createTime = Utils.getDateFormatted(message.getString("created_time"));
                int likes = Integer.parseInt(message.getString("like_count"));
                String fromId = message.getJSONObject("from").getString("id");
                String comment = message.isNull("message")?"":message.getString("message");
                String messageId = message.getString("id");

                LOGGER.log(Level.INFO, "(Message) createTime {0}", createTime);
                LOGGER.log(Level.INFO, "(Message) likes {0}", likes);
                LOGGER.log(Level.INFO, "(Message) fromId {0}", fromId);
                LOGGER.log(Level.INFO, "(Message) comment {0}", comment);
                LOGGER.log(Level.INFO, "(Message) messageId {0}", messageId);
                LOGGER.log(Level.INFO, "(Message) postId {0}", postId);
                LOGGER.log(Level.INFO, "(Message) groupId {0}", groupId);

                newComment.setCreateTime(createTime);
                newComment.setLikeCount(likes);
                newComment.setFromId(fromId);
                newComment.setMessage(comment);
                newComment.setMessageId(messageId);
                newComment.setPostId(postId);
                newComment.setGroupId(groupId);
                em.merge(newComment);

                if(hasMentions){
                    JSONArray mentions = message.getJSONArray("message_tags");
                    syncRawMentions(groupId, messageId, fromId, "MESSAGE", mentions, createTime, em);
                }
            }
        }
    }
    
    /**
     * Sincronizar informacion acerca de los mentions que se realizan en un post
     * o comentario
     * 
     * @param groupId
     * @param objectId
     * @param fromId
     * @param type
     * @param mentions
     * @param em
     * @throws JSONException 
     */
    private void syncRawMentions(String groupId, String objectId, String fromId, String type, JSONArray mentions, Date createdTime, EntityManager em) throws JSONException{

        int len = mentions.length();
        for(int i=0;i<len;i++){
            FacebookMentions newMention = new FacebookMentions();
            JSONObject mention = mentions.getJSONObject(i);
            String toId = mention.getString("id");

            LOGGER.log(Level.INFO, "(MENTION) ({0}) objectId {1}", new Object[]{type, objectId});
            LOGGER.log(Level.INFO, "(MENTION) ({0}) fromId {1}", new Object[]{type, fromId});
            LOGGER.log(Level.INFO, "(MENTION) ({0}) toId {1}", new Object[]{type, toId});
            LOGGER.log(Level.INFO, "(MENTION) ({0}) type {1}", new Object[]{type, type});
            LOGGER.log(Level.INFO, "(MENTION) ({0}) group id {1}", new Object[]{type, groupId});

            newMention.setFromId(fromId);
            newMention.setObjectId(objectId);
            newMention.setToId(toId);
            newMention.setType(type);
            newMention.setGroupId(groupId);
            newMention.setCreatedTime(createdTime);
            em.merge(newMention);
        }
    }

    /**
     * 
     * Metodo para extraer informacion de los miembros de un grupo
     * @param groupId
     * @throws FacebookException
     * @throws JSONException 
     */
    private void getRawMembersInGroup(String groupId) throws FacebookException, JSONException{
        EntityManager em = emf.createEntityManager();
        String relURL = groupId + "/members?fields=first_name,last_name,id,picture.type(square).height(80)&offset=5&limit=500";
        int counterCommit = 0;
        try{
            for(int p=0;p<30;p++){
                RawAPIResponse response = facebook.callGetAPI(relURL);
                JSONObject json = response.asJSONObject();

                JSONArray members = json.getJSONArray("data");
                String nextPage = json.getJSONObject("paging").getString("next");
                LOGGER.log(Level.INFO, "(nextPage)===> {0}", nextPage);

                int len = members.length();
                int startLength = nextPage.indexOf(groupId);
                relURL = nextPage.substring(startLength,nextPage.length());
                LOGGER.log(Level.INFO, "(nextPage)===> {0}", relURL);

                if(!em.getTransaction().isActive()){
                    em.getTransaction().begin();
                }
                for(int i=0;i<len;i++){
                    JSONObject member = members.getJSONObject(i);
                    LOGGER.log(Level.INFO, "PAGE -> {0}, member row -> {1}", new Object[]{p, i});
                    syncRawMember(groupId,member,em);
                    LOGGER.log(Level.INFO, "Members {0}  ===> ", len);
                    if(counterCommit>=99){
                        if(!em.getTransaction().isActive())
                            em.getTransaction().begin();

                        LOGGER.info("guardando informacion...");
                        em.getTransaction().commit();
                        
                        counterCommit = -1;
                    }
                    counterCommit++;
                }
                if(!em.getTransaction().isActive())
                    em.getTransaction().begin();

                LOGGER.info("guardando informacion...");
                em.getTransaction().commit();
                LOGGER.log(Level.INFO, "NEXT===> {0}", relURL);
            }
        }finally{
            if(em.getTransaction().isActive()){
                LOGGER.info("guardando informacion...");
                em.getTransaction().commit();
            }
            if(em.isOpen())
                em.close();
        }
    }

    /**
     * Actualizar información de miembros de grupos según el objeto JSON recibido
     * @param groupId
     * @param member
     * @param em
     * @throws JSONException
     * @throws FacebookException 
     */
    private void syncRawMember(String groupId, JSONObject member, EntityManager em) throws JSONException, FacebookException{

        String id = member.getString("id");
        String firstName = member.isNull("first_name")?"":member.getString("first_name");
        String lastName = member.isNull("last_name")?"":member.getString("last_name");
        String picture = member.getJSONObject("picture").getJSONObject("data").getString("url");
        
        FacebookMember user = new FacebookMember(id,firstName,lastName, picture);
        em.merge(user); // crear o actualizar usuario 
        /*
        LOGGER.log(Level.INFO, "(MEMBER) Id -> {0}", id);
        LOGGER.log(Level.INFO, "(MEMBER) firstName -> {0}", firstName);
        LOGGER.log(Level.INFO, "(MEMBER) lastName -> {0}", lastName);
        LOGGER.log(Level.INFO, "(MEMBER) picture -> {0}", picture);
        LOGGER.log(Level.INFO, "(MEMBER) groupId -> {0}", groupId);
        */
    }

    private void updateGroupsInformation(String groupId, String groupName, int minInteractions) throws Exception {

        LOGGER.log(Level.INFO, "Actualizando grupo {0}", groupName);
        groupDao.updGroupInformationByGroupById(groupId, minInteractions);
    }

    private List<GroupInformation> getGroupList() {
        try{
            return groupDao.findAllGroups();
        }catch(Exception ex){
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
