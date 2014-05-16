package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dto.Influencer;
import org.devdom.tracker.model.dto.TopThreeInformation;
import org.devdom.tracker.util.Configuration;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class InfluencerDao {
    
    private static final long serialVersionUID = 1L;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    public InfluencerDao(){ }
    
    /**
     * 
     * @return 
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }
    
    /**
     * Top 20 de los developers más influyentes a nivel general
     * 
     * @param groupId
     * @return
     * @throws Exception 
     */
    public List<Influencer> findTop20DevsInfluents(String groupId) throws Exception{
        EntityManager em = getEntityManager();
        try{
            return (List<Influencer>) em.createNamedQuery("Influencers.findTop20DevsInfluents")
                    .setParameter("group_id", groupId)
                    .getResultList();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }
    
    /**
     * Retorna un listado de 3 developers. En medio la cuenta actual con las 
     * dos posiciones anteriores y siguientes a el
     * 
     * @param fromId
     * @param groupId
     * @return
     * @throws Exception 
     */
    public List<TopThreeInformation> findPositionCarruselByUserIdAndGroupId(String fromId, String groupId) throws Exception{
        EntityManager em = getEntityManager();
        try{
            
            System.out.println("FROMID -------> "+fromId);
            System.out.println("GROUPID -------> "+groupId);
            return (List<TopThreeInformation>)em.createNamedQuery("TopThreeInformation.findPositionCarruselByUserIdAndGroupId")
                    .setParameter("from_id", fromId)
                    .setParameter("group_id", groupId)
                    .getResultList();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }

}