package org.devdom.tracker.model.dao;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
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
import org.devdom.client.facebook.FBConnect;
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
    public FacebookDao(){ }

    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }
    
    public void syncInformation(String groupId){
        EntityManager em = getEntityManager();
        Facebook fb = new FacebookFactory(cb.build()).getInstance();

        try {
            em.getTransaction().begin();
            Reading reading = new Reading();
            reading.limit(Configuration.POST_LIMIT);
            ResponseList<Post> group = fb.getGroupFeed(groupId,reading);

            List<Post> posts = group.subList(1,group.size());            
            List<FacebookPost> list = em.createNamedQuery("FacebookPost.findAll").getResultList();

            posts.stream().forEach((post) -> {                
                FacebookPost newPost = new FacebookPost();
                newPost.setPostId(post.getId().split("_")[1]);
                newPost.setFromId(post.getFrom().getId());
                newPost.setCreationDate(post.getCreatedTime());
                newPost.setLikeCount(post.getLikes().size());
                newPost.setMessage(post.getMessage());

                boolean exists = false;
                for(FacebookPost postSaved : list){
                    if(postSaved.getFromId().equals(newPost.getFromId())){
                        exists = true;
                        break;
                    }
                }
                if(!exists)
                    em.persist(newPost);
            });
            em.getTransaction().commit();
        } catch (FacebookException ex) {
            Logger.getLogger(FBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}