package org.devdom.influencer.util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class EntityManagerFactory{

    private final javax.persistence.EntityManagerFactory emf;

    public EntityManagerFactory(){
        emf = Persistence.createEntityManagerFactory(Configuration.JPA_PU, Configuration.JPAConfig());
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
