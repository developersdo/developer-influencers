package org.devdom.influencer.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.influencer.model.dto.TopInteraction;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class TopInteractionDao {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
   
    public List<TopInteraction> findTopMostLikedPostByGroupId(String groupId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (List<TopInteraction>) em.createNamedQuery("TopInteractions.findTopMostLikedPostByGroupId")
                    .setParameter("group_id", groupId)
                    .getResultList();
        }finally{
            if(em.isOpen()){
                em.close();
            }
        }
    }
    
    public List<TopInteraction> findTopMostCommentedPostByGroupId(String groupId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (List<TopInteraction>) em.createNamedQuery("TopInteractions.findTopMostCommentedPostByGroupId")
                    .setParameter("group_id", groupId)
                    .getResultList();
        }finally{
            if(em.isOpen()){
                em.close();
            }
        }
    }
    
    public List<TopInteraction> findTopMostLikedCommentsByGroupId(String groupId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (List<TopInteraction>) em.createNamedQuery("TopInteractions.findTopMostLikedCommentsByGroupId")
                    .setParameter("group_id", groupId)
                    .getResultList();
        }finally{
            if(em.isOpen()){
                em.close();
            }
        }
    }
    
}
