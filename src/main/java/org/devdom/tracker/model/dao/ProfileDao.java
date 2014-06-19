package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dto.EducationHistory;
import org.devdom.tracker.model.dto.FacebookProfile;
import org.devdom.tracker.model.dto.Skill;
import org.devdom.tracker.model.dto.WorkHistory;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class ProfileDao {
    
    private static final long serialVersionUID = 1L;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    
    /**
     * 
     * @param profileId
     * @return
     * @throws Exception 
     */
    public FacebookProfile getProfileInformation(String profileId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (FacebookProfile) em.createNamedQuery("profile.findProfileInformation")
                    .setParameter("from_id", profileId)
                    .getSingleResult();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * 
     * @param profileId
     * @return
     * @throws Exception 
     */
    public List<Skill> getSkills(String profileId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return em.createNamedQuery("Skill.findSkillsByUid")
                    .setParameter("from_id",profileId)
                    .getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * 
     * @param profileId
     * @return
     * @throws Exception 
     */
    public List<WorkHistory> getWorks(String profileId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return em.createNamedQuery("WorkHistory.findWorkByUid")
                    .setParameter("from_id", profileId)
                    .getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    public List<EducationHistory> getEducationInformation(String profileId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return em.createNamedQuery("EducationHistory.findEducationByUid")
                    .setParameter("from_id", profileId)
                    .getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
}