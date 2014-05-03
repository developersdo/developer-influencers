package org.devdom.tracker.model.dao;

import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Paging;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import org.devdom.tracker.model.dto.FacebookComment;
import org.devdom.tracker.model.dto.FacebookPost;
import org.devdom.tracker.util.Configuration;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class FacebookDao {

    private static final long serialVersionUID = 1L;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    private static final ConfigurationBuilder cb = Configuration.getFacebookConfig();
    Facebook facebook = new FacebookFactory(cb.build()).getInstance();
    public FacebookDao(){ }

    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }

    /**
     * Método para sincronizar los comentarios que se reciban de un post
     * 
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
    * Retorna la cantidad total de likes que contiene un comentario
    *
    * @param commentId id del comentario actual
    * @param postId id del post actual
    * @return retorna la cantidad total de likes para un comentario
    */
    public int getCommentLikes(String postId, String commentId){
        String id = postId+"_"+commentId;
        System.out.println("@@@@@ comment_id @@@@@ => "+id);
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
}