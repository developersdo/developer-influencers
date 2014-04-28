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
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }
    
    public List<Influencers> findTop10DevsInfluents(){
        EntityManager em = getEntityManager();
        try{
            return em.createNamedQuery("Influencers.findTop10DevsInfluents").getResultList();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }

}