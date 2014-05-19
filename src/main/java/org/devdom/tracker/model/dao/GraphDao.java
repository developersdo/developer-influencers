package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dto.GraphStats;
import org.devdom.tracker.util.Configuration;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class GraphDao {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    
    /**
     * 
     * 
     * @return
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }
    
    public List<GraphStats> findPostsStats(){
        
        EntityManager em = getEntityManager();
        try{
            return (List<GraphStats>) em.createNamedQuery("Graph.findPostsStats").getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    /**
     * 
     * @param month
     * @param year
     * @return 
     */
    public List<GraphStats> findPostsStatsByMonthAndYear(int month, int year){
        EntityManager em = getEntityManager();
        try{
            return (List<GraphStats>) em.createNamedQuery("Graph.findPostsStatsByMonthAndYear")
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
    public List<GraphStats> findCommentsStats(){
        EntityManager em = getEntityManager();
        try{
            return (List<GraphStats>) em.createNamedQuery("Graph.findCommentsStats").getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
}
