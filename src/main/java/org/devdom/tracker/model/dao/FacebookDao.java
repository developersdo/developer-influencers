package org.devdom.tracker.model.dao;

import javax.persistence.EntityManager;
import org.devdom.tracker.util.EntityManagerFactory;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class FacebookDao {
    
    private final EntityManagerFactory emf;
    
    public FacebookDao(){
        emf = new EntityManagerFactory();
    }

    public EntityManager getEntityManager(){
        return emf.getEntityManager();
    }
    
    public void syncInformation(){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        
        em.getTransaction().commit();
    }
}
