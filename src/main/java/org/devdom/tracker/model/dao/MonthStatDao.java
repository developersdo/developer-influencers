package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dto.YearStat;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class MonthStatDao {
    
    private static final long serialVersionUID = 1L;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    
    /**
     * Retorno de elemento del dashboard referente a un grupo
     * 
     * @param month
     * @param year
     * @param groupId
     * @param fromId
     * @return 
     */
    public List<YearStat> findMonthsStat(int month, int year, String groupId, String fromId){
        EntityManager em = emf.createEntityManager();
        try{
            return (List<YearStat>) em.createNamedQuery("stat.findMonthsStat")
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .setParameter("group_id", groupId)
                    .setParameter("from_id", fromId)
                    .getResultList();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }
    
    /**
     * 
     * @param fromId
     * @return 
     */
    public List<YearStat> findTopPositionsInTop(String fromId){
        EntityManager em = emf.createEntityManager();
        try{
            return (List<YearStat>) em.createNamedQuery("YearStat.findTopPositionsInTop")
                    .setParameter("from_id", fromId)
                    .getResultList();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }
}
