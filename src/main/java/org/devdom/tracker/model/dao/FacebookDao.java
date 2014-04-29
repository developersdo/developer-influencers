package org.devdom.tracker.model.dao;

import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    private final Facebook fb = new FacebookFactory(cb.build()).getInstance();
    public FacebookDao(){ }

    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }
    
    public void syncMessageInformation(EntityManager em, Post post){

        PagableList<Comment> comments = post.getComments();
        comments.parallelStream().forEach((comment) -> {
            if( (comment.getId()!=null) && (post.getId()!=null) && (comment.getFrom()!=null) ){
                FacebookComment newComment = new FacebookComment();            
                newComment.setCreateTime(comment.getCreatedTime());
                newComment.setLikeCount(comment.getLikeCount());
                newComment.setFromId(comment.getFrom().getId());
                newComment.setMessage(comment.getMessage());
                newComment.setMessageId(comment.getId());
                newComment.setPostId(post.getId().split("_")[1]);
                em.merge(newComment);
            }
        });
    } 
    
    /**
    * Sincroniza la información de facebook para cada developer
    *
    * @param groupId identificador del grupo que se desea revisar
    */
    public void syncGroupInformation(String groupId){
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            Reading reading = new Reading();
            reading.limit(Configuration.POSTS_LIMIT);
            ResponseList<Post> group = fb.getGroupFeed(groupId,reading);

            List<Post> posts = group.subList(1,group.size());
            posts.stream().forEach((post) -> {
                
                if(post.getFrom()!=null){
                    int likes = getPostLikes(post);
                    FacebookPost newPost = new FacebookPost();
                    newPost.setPostId(post.getId().split("_")[1]);
                    newPost.setFromId(post.getFrom().getId());
                    newPost.setCreationDate(post.getCreatedTime());
                    newPost.setLikeCount(likes);
                    newPost.setMessage(post.getMessage());

                    em.merge(newPost);

                    //syncMessageInformation(em,post);
                }
            });
            em.getTransaction().commit();
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
        
    /**
    * Retorna la cantidad total de likes que contiene un post
    *
    * @param post con información del post que se encuentra recorriendo en ese momento
    * @return retorna la cantidad total de likes para un post
    */
    public int getPostLikes(Post post){
        try {
            Reading reading = new Reading();
            reading.limit(Configuration.POSTS_LIMIT);
            ResponseList<Like> likes = fb.getPostLikes(post.getId(),reading);
            return likes.size();
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}