package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dto.GroupAdminsInformation;
import org.devdom.tracker.model.dto.GroupInformation;
import org.devdom.tracker.model.dto.GroupRating;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class GroupRatingDao {

    private final EntityManagerFactory emf;
    public GroupRatingDao(){
        emf = Persistence.createEntityManagerFactory("jpa");
    }

    /**
     * Listado de grupos con el rating que tiene el developer en cada uno de ellos
     * 
     * @param fromId
     * @return
     * @throws Exception 
     */
    public List<GroupRating> findGroupsRatingByUserId(String fromId)throws Exception{

        EntityManager em = emf.createEntityManager();
        try{
            return (List<GroupRating>) em.createNamedQuery("GroupRating.findGroupsRatingByUserId")
                    .setParameter("from_id", fromId)
                    .getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * General el top de los developers de un grupo determinado según el ID
     * @param groupId
     * @return
     * @throws Exception 
     */
    public GroupInformation findGroupInformationById(String groupId)throws Exception{

        EntityManager em = emf.createEntityManager();
        try{
            return (GroupInformation) em.createNamedQuery("GroupInformation.findByGroupId")
                    .setParameter("group_id", groupId)
                    .getSingleResult();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * 
     * @param dayofYear
     * @param year
     * @param groupId
     * @param minInteraction
     * @return
     * @throws Exception 
     */
    public GroupInformation findDayActivityGroupById(int dayofYear,int year,String groupId, int minInteraction)throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (GroupInformation) em.createNamedQuery("GroupInformation.updTopInfluencers_day")
                    .setParameter("min_interactions",minInteraction)
                    .setParameter("day_of_year", dayofYear)
                    .setParameter("in_year", year)
                    .setParameter("group_id", groupId)
                    .getSingleResult();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * Genera el top de los developers de un grupo determinado según el ID
     * @param groupId
     * @param minInteraction
     * @return 
     * @throws Exception 
     */
    public GroupInformation updGroupInformationByGroupById(String groupId, int minInteraction)throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (GroupInformation) em.createNamedQuery("GroupInformation.updTopInfluencers")
                    .setParameter("group_id", groupId)
                     .setParameter("min_interaction", minInteraction)
                    .getSingleResult();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * Genera el top de los developers de un grupo derterminado según el ID y el intervalo especificado
     * 
     * @param groupId
     * @param min
     * @param interval
     * @return
     * @throws Exception 
     */
    public GroupInformation updGroupInformationYearByIntervalAndId(String groupId, int min, int interval)throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (GroupInformation) em.createNamedQuery("GroupInformation.updTopInfluencers_year")
                    .setParameter("in_group_id", groupId)
                    .setParameter("in_min_interactions", min)
                    .setParameter("in_interval_month",interval)
                    .getSingleResult();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * Actualiza la información en los grupos referente a un año
     * @return 
     * @throws Exception 
     */
    public GroupInformation updTablesInformationYear()throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (GroupInformation) em.createNamedQuery("GroupInformation.updTablesInformationYear")
                    .getSingleResult();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    /**
     * Traer todos los datos informativos de los grupos
     * @return
     * @throws Exception 
     */
    public List<GroupInformation> findAllGroups() throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (List<GroupInformation>) em.createNamedQuery("GroupInformation.findAll")
                    .getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    public GroupInformation findGroupActivityByGroupId(String groupId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (GroupInformation) em.createNamedQuery("GroupInformation.findGroupActivityByGroupId")
                    .setParameter("group_id", groupId)
                    .getSingleResult();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * 
     * @param groupId
     * @return
     * @throws Exception 
     */
    public GroupInformation updTopInfluencersMonthlyByGroupId(String groupId) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            return (GroupInformation) em.createNamedQuery("GroupInformation.updTopInfluencers_monthBatch")
                    .setParameter("group_id", groupId)
                    .getSingleResult();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }

    public List<GroupAdminsInformation> findAdminsByGroupId(String groupId){
        EntityManager em = emf.createEntityManager();
        try{
            return (List<GroupAdminsInformation>) em.createNamedQuery("GroupInformation.findAdminsByGroupId")
                    .setParameter("group_id", groupId)
                    .getResultList();
        }finally{
            if(em.isOpen()){
                em.close();
            }
        }
    }
}
