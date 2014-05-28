package org.devdom.tracker.model.dao;

import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Paging;
import facebook4j.Post;
import facebook4j.RawAPIResponse;
import facebook4j.Reading;
import facebook4j.ResponseList;
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
import javax.servlet.http.HttpServletRequest;
import org.devdom.tracker.model.dto.FacebookComment;
import org.devdom.tracker.model.dto.FacebookMentions;
import org.devdom.tracker.model.dto.FacebookPost;
import org.devdom.tracker.util.Configuration;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class FacebookDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    private static final ConfigurationBuilder cb = Configuration.getFacebookConfig();
    Facebook facebook = new FacebookFactory(cb.build()).getInstance();
    public FacebookDao(){ }

    /**
     * 
     * 
     * @return
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }

    /**
     * Método para sincronizar los comentarios que se reciban de un post
     * 
     * @deprecated 
     * @param em instancia del EntityManager
     * @param post objeto post con información del post que se procesa en el momento
     */
    public void syncMessageInformation(EntityManager em, Post post){

        PagableList<Comment> comments = post.getComments();
        comments.parallelStream().forEach((comment) -> {
            if(comment!=null){
                if( (comment.getId()!=null) && (post.getId()!=null) && (comment.getFrom()!=null) ){

                    String postId = post.getId().split("_")[1];
                    String commentId = comment.getId();
                    int likes = getCommentLikes(postId,commentId);

                    FacebookComment newComment = new FacebookComment();            
                    newComment.setCreateTime(comment.getCreatedTime());
                    newComment.setLikeCount(likes);
                    newComment.setFromId(comment.getFrom().getId());
                    newComment.setMessage(comment.getMessage());
                    newComment.setMessageId(commentId);
                    newComment.setPostId(postId);
                    em.merge(newComment);
                }
            }
        });
    }
    
    /**
    * Sincroniza la información de facebook para cada developer
    * 
    * @deprecated 
    * @param request request from servlet
    * @param groupId identificador del grupo que se desea revisar
    */
    public void syncGroupInformation(HttpServletRequest request, String groupId){
        EntityManager em = getEntityManager();

        try {
            
            Reading reading = new Reading();
            reading.limit(Configuration.POSTS_LIMIT);
            
            ResponseList<Post> group = null;
            Paging<Post> paging;
            int records = 0;

            for(int i=0; i<50;i++){
                em.getTransaction().begin();
                if(i==0){
                    group = facebook.getGroupFeed(groupId,reading);
                }

                records += group.size();
                group.stream().forEach((post) -> {
                    if(post.getFrom()!=null){
                        try{
                            int likes = getPostLikes(post);
                            FacebookPost newPost = new FacebookPost();
                            newPost.setPostId(post.getId().split("_")[1]);
                            newPost.setFromId(post.getFrom().getId());
                            newPost.setCreationDate(post.getCreatedTime());
                            newPost.setLikeCount(likes);
                            newPost.setMessage(post.getMessage());
                            em.merge(newPost);
                        }catch(Exception ex){
                            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        syncMessageInformation(em,post);
                    }
                });
                em.getTransaction().commit();
                paging = group.getPaging();
                group = facebook.fetchNext(paging);
            }

        } catch (FacebookException | IllegalArgumentException ex) {
            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }
    
    /**
    * Retorna la cantidad total de likes que contiene un post
    * 
    * @deprecated 
    * @param post con información del post que se encuentra recorriendo en ese momento
    * @return retorna la cantidad total de likes para un post
    */ 
    public int getPostLikes(Post post){
        Reading reading = new Reading();
        reading.limit(Configuration.POSTS_LIMIT);
        
        try {
            String postId = post.getId().split("_")[1];
            ResponseList<Like> likes = facebook.getPostLikes(postId/*post id*/,reading);
            return likes.size();
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * tomar la cantidad de tags de un post 
     * 
     * @deprecated 
     * @param post
     */
    public void getPostTags(Post post){
        
        post.getMessageTags().stream().forEach((tag) -> {
            System.out.println(post.getId()+"-tag.getId() -----> "+ tag.getId());
            System.out.println(post.getId()+"-tag.getName() -----> "+ tag.getName());
            System.out.println(post.getId()+"-tag.getType() -----> "+ tag.getType());
            System.out.println(post.getId()+"-tag.getCreatedTime() -----> "+ tag.getCreatedTime());
            System.out.println(post.getId()+"-tag.getLength() -----> "+ tag.getLength());
            System.out.println(post.getId()+"-tag.getMetadata() -----> "+ tag.getMetadata());
        });
    }
    
    /**
    * Retorna la cantidad total de likes que contiene un comentario
    * 
    * @deprecated 
    * @param postId id del post actual
    * @param commentId id del comentario actual    
    * @return retorna la cantidad total de likes para un comentario
    */
    public int getCommentLikes(String postId, String commentId){

        String id = postId+"_"+commentId;
        Reading reading = new Reading();
        reading.limit(Configuration.LIKES_LIMIT);

        try {
            ResponseList<Like> likes = facebook.getCommentLikes(id);
            return likes.size();
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * 
     * @deprecated 
     * @param post
     * @param em
     * @throws FacebookException
     * @throws JSONException 
     */
    private void syncPostTags(Post post, EntityManager em) throws FacebookException, JSONException {
        String relURL = "201514949865358_846925331990980?fields=id,message,message_tags,name,created_time,from,likes.limit(300).fields(id,name,username),comments.limit(1000).fields(from,id,like_count,message,message_tags,user_likes,created_time)";
        RawAPIResponse response = facebook.callGetAPI(relURL);
        JSONObject jsonObject = response.asJSONObject();
        
        System.out.println("jsonObject.length() => "+ jsonObject.length() );
        System.out.println("jsonObject.get(\"id\"); => "+ jsonObject.get("id") );

    }
    
    /**
     * 
     * Llamada al API de Facebook para retornar un JSON crudo para ser manipulado
     * con información referente a los post del grupo que se especifique según su ID
     * 
     * @deprecated 
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
                System.out.println("(nextPage)===> "+nextPage);

                int len = posts.length();
                int startLength = nextPage.indexOf(groupId);
                relURL = nextPage.substring(startLength,nextPage.length());
                System.out.println("(nextPage)===> "+relURL);
                
                for(int i=0;i<len;i++){
                    em.getTransaction().begin();
                    JSONObject post = posts.getJSONObject(i);
                    System.out.println("POST ID ******** => "+ post.getString("id"));
                    syncRawPost(post,em);
                    em.getTransaction().commit();

                }

                System.out.println("NEXT===> "+relURL);
                
            }
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }

    /**
     * Sincronizar la información contenida en un post
     * 
     * @deprecated 
     * @param post
     * @param em
     * @throws JSONException
     * @throws FacebookException 
     */
    private void syncRawPost(JSONObject post, EntityManager em) throws JSONException, FacebookException{
        String id = "201514949865358_846925331990980"; //post.getString("id");
        
        String relURL = id + "?fields=id,message,message_tags,name,created_time,from,likes.limit(300).fields(id,name,username),comments.limit(1000).fields(from,id,like_count,message,message_tags,user_likes,created_time)";
        RawAPIResponse response = facebook.callGetAPI(relURL);
        
        JSONObject json = response.asJSONObject();

        String postId = json.getString("id").split("_")[1];
        String message = json.getString("message");
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
        System.out.println("(POST) postId "+ postId);
        System.out.println("(POST) fromId "+ fromId);
        System.out.println("(POST) createdTime "+ createdTime);
        System.out.println("(POST) likes "+ likes);
        System.out.println("(POST) message "+ message);
        
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
     * @deprecated 
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
            String comment = message.getString("message");
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

            System.out.println("(Message) createTime "+ createTime);
            System.out.println("(Message) likes "+ likes);
            System.out.println("(Message) fromId "+ fromId);
            System.out.println("(Message) comment "+ comment);
            System.out.println("(Message) messageId "+ messageId);
            System.out.println("(Message) postId "+ postId);
        }
    }
    
    /**
     * Sincronizar información acerca de los mentions que se realizan en un post
     * o comentario
     * 
     * @deprecated 
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

            System.out.println("(MENTION) ("+type+") objectId "+ objectId);
            System.out.println("(MENTION) ("+type+") fromId "+ fromId);
            System.out.println("(MENTION) ("+type+") toId "+ toId);
            System.out.println("(MENTION) ("+type+") type "+ type);
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