package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dto.GroupInformation;
import org.devdom.tracker.model.dto.GroupRating;
import org.devdom.tracker.util.Configuration;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class GroupRatingDao {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    
    public GroupRatingDao(){
        
    }
    
    /**
     * 
     * @return 
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }

    /**
     * Listado de grupos con el rating que tiene el developer en cada uno de ellos
     * 
     * @param fromId
     * @return
     * @throws Exception 
     */
    public List<GroupRating> findGroupsRatingByUserId(String fromId)throws Exception{
        EntityManager em = getEntityManager();
        try{
            return (List<GroupRating>) em.createNamedQuery("GroupRating.findGroupsRatingByUserId")
                    .setParameter("from_id", fromId)
                    .getResultList();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }
    
    public GroupInformation findGroupInformationById(String groupId)throws Exception{
        EntityManager em = getEntityManager();
        try{
            return (GroupInformation) em.createNamedQuery("GroupInformation.findByGroupId")
                    .setParameter("group_id", groupId)
                    .getSingleResult();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }
}
