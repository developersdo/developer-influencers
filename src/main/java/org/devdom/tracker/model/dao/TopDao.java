package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dto.Top;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class TopDao {

    /**
     * Top 20 de los developers más influyentes a nivel general
     * 
     * @param groupId
     * @return
     * @throws Exception 
     */
    public List<Top> findTop20Devs(String groupId) throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();
        List<Top> list;
        try{
            list = (List<Top>) em.createNamedQuery("Top.findTop20DevsInfluents")
                    .setParameter("group_id", groupId)
                    .getResultList();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
            
            if(emf.isOpen())
                emf.close();
        }
        return list;
    }

}