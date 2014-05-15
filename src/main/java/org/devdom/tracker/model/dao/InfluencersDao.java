package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dto.Influencers;
import org.devdom.tracker.util.Configuration;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class InfluencersDao {
    
    private static final long serialVersionUID = 1L;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    public InfluencersDao(){ }
    
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
     * @return
     * @throws Exception 
     */
    public List<Influencers> findTop20DevsInfluents() throws Exception{
        EntityManager em = getEntityManager();
        try{
            return em.createNamedQuery("Influencers.findTop10DevsInfluents").getResultList();
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
    public List<Influencers> findPositionCarruselByUserIdAndGroupId(String fromId, String groupId) throws Exception{
        EntityManager em = getEntityManager();
        try{
            return (List<Influencers>)em.createNamedQuery("Influencers.findPositionCarruselByUserIdAndGroupId")
                    .setParameter("from_id", fromId)
                    .setParameter("group_id", groupId)
                    .getResultList();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }

}