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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dao.FacebookDao;
import org.devdom.tracker.model.dto.FacebookComment;
import org.devdom.tracker.model.dto.FacebookMentions;
import org.devdom.tracker.model.dto.FacebookPost;
import org.devdom.tracker.util.Configuration;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class Worker implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(Worker.class .getName());
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    private static final ConfigurationBuilder cb = Configuration.getFacebookConfig();
    private final Facebook facebook = new FacebookFactory(cb.build()).getInstance();

    /**
     * 
     * @return
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }

    @Override
    public void run() {
        for(String groupId : Configuration.SEEK_GROUPS){
            try{
                getRawPostsInGroup(groupId);
            }catch(FacebookException | JSONException ex){
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /**
     * 
     * Llamada al API de Facebook para retornar un JSON crudo para ser manipulado
     * con información referente a los post del grupo que se especifique según su ID
     * 
     * @param groupId
     * @throws FacebookException
     * @throws JSONException 
     */
    public void getRawPostsInGroup(String groupId) throws FacebookException, JSONException {
        
        EntityManager em = getEntityManager();
        try{
            String relURL = groupId + "/feed?fields=id&limit=500";
            
            for(int p=0;p<50;p++){
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
                    em.getTransaction().begin();
                    JSONObject post = posts.getJSONObject(i);
                    LOGGER.log(Level.INFO, "POST ID ******** => {0}", post.getString("id"));
                    syncRawPost(post,em);
                    em.getTransaction().commit();
                    LOGGER.log(Level.INFO, "{0}Posts ===> ", len);
                    Thread.sleep(10000);
                }
                LOGGER.log(Level.INFO, "NEXT===> {0}", relURL);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }

    /**
     * Sincronizar la información contenida en un post
     * 
     * @param post
     * @param em
     * @throws JSONException
     * @throws FacebookException 
     */
    private void syncRawPost(JSONObject post, EntityManager em) throws JSONException, FacebookException{
        String id = post.getString("id");
        
        String relURL = id + "?fields=id,message,message_tags,name,created_time,from,likes.limit(300).fields(id,name,username),comments.limit(1000).fields(from,id,like_count,message,message_tags,user_likes,created_time)";
        RawAPIResponse response = facebook.callGetAPI(relURL);
        
        JSONObject json = response.asJSONObject();

        String postId = json.getString("id").split("_")[1];
        String message = json.isNull("message")?"":json.getString("message");
        Date createdTime = getDateFormatted(json.getString("created_time"));
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
        em.merge(newPost); // crear o actualizar un post existente
        LOGGER.log(Level.INFO, "(POST) postId {0}", postId);
        LOGGER.log(Level.INFO, "(POST) fromId {0}", fromId);
        LOGGER.log(Level.INFO, "(POST) createdTime {0}", createdTime);
        LOGGER.log(Level.INFO, "(POST) likes {0}", likes);
        LOGGER.log(Level.INFO, "(POST) message {0}", message);

        if(hasMessages)
            syncRawMessages(postId,json.getJSONObject("comments").getJSONArray("data"),em);

        /*
        El API de facebook retorna un formato diferente para extraer los mentions 
        valiendose de dos arreglos
        */
        if(hasMentions){ 
            JSONArray tags = json.getJSONObject("message_tags").names();
            int len = tags.length();

            for(int i=0;i<len;i++){
                String tag = tags.getString(i);
                JSONArray mentions = json.getJSONObject("message_tags").getJSONArray(tag);
                for(int x=0;x<mentions.length();x++){
                    JSONArray collection = json.getJSONObject("message_tags").getJSONArray(tag);
                    syncRawMentions(postId, fromId, "POST", collection, em);
                }
            }
        }
    }

    /**
     * 
     * Sincronizar mensajes que se contienen en un post
     * 
     * @param postId
     * @param comments
     * @param em
     * @throws JSONException 
     */
    private void syncRawMessages(String postId, JSONArray comments, EntityManager em) throws JSONException {
        int len = comments.length();
        for(int i=0;i<len;i++){
            FacebookComment newComment = new FacebookComment();
            JSONObject message = comments.getJSONObject(i);
            boolean hasMentions = !(message.isNull("message_tags"));
            
            Date createTime = getDateFormatted(message.getString("created_time"));
            int likes = Integer.parseInt(message.getString("like_count"));
            String fromId = message.getJSONObject("from").getString("id");
            String comment = message.isNull("message")?"":message.getString("message");
            String messageId = message.getString("id");

            newComment.setCreateTime(createTime);
            newComment.setLikeCount(likes);
            newComment.setFromId(fromId);
            newComment.setMessage(comment);
            newComment.setMessageId(messageId);
            newComment.setPostId(postId);
            em.merge(newComment);
            
            if(hasMentions){
                JSONArray collection = message.getJSONArray("message_tags");
                syncRawMentions(messageId, fromId, "MESSAGE", collection, em);
            }

            LOGGER.log(Level.INFO, "(Message) createTime {0}", createTime);
            LOGGER.log(Level.INFO, "(Message) likes {0}", likes);
            LOGGER.log(Level.INFO, "(Message) fromId {0}", fromId);
            LOGGER.log(Level.INFO, "(Message) comment {0}", comment);
            LOGGER.log(Level.INFO, "(Message) messageId {0}", messageId);
            LOGGER.log(Level.INFO, "(Message) postId {0}", postId);

        }
    }
    
    /**
     * Sincronizar información acerca de los mentions que se realizan en un post
     * o comentario
     * 
     * @param objectId
     * @param fromId
     * @param type
     * @param mentions
     * @param em
     * @throws JSONException 
     */
    private void syncRawMentions(String objectId, String fromId, String type, JSONArray mentions, EntityManager em) throws JSONException{

        int len = mentions.length();
        for(int i=0;i<len;i++){
            FacebookMentions newMention = new FacebookMentions();
            JSONObject mention = mentions.getJSONObject(i);
            
            String toId = mention.getString("id");
            newMention.setFromId(fromId);
            newMention.setObjectId(objectId);
            newMention.setToId(toId);
            newMention.setType(type);
            em.merge(newMention);

            LOGGER.log(Level.INFO, "(MENTION) ({0}) objectId {1}", new Object[]{type, objectId});
            LOGGER.log(Level.INFO, "(MENTION) ({0}) fromId {1}", new Object[]{type, fromId});
            LOGGER.log(Level.INFO, "(MENTION) ({0}) toId {1}", new Object[]{type, toId});
            LOGGER.log(Level.INFO, "(MENTION) ({0}) type {1}", new Object[]{type, type});

        }
    }

    /**
     * Formatear fecha y convertirla de String a Date
     * 
     * @param date
     * @return 
     */
    private Date getDateFormatted(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return formatter.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
    
}
