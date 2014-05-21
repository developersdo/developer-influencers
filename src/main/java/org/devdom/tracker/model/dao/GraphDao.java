package org.devdom.tracker.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.devdom.tracker.model.dto.GraphCommentsStat;
import org.devdom.tracker.model.dto.GraphPostsStat;
import org.devdom.tracker.util.EntityManagerFactory;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class GraphDao {
    
    private final EntityManagerFactory emf = new EntityManagerFactory();

    public List<GraphPostsStat> findPostsStats(){
        
        EntityManager em = emf.getEntityManager();
        try{
            return (List<GraphPostsStat>) em.createNamedQuery("Graph.findPostsStats").getResultList();
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
    public List<GraphPostsStat> findPostsStatsByMonthAndYear(int month, int year){
        EntityManager em = emf.getEntityManager();
        try{
            return (List<GraphPostsStat>) em.createNamedQuery("Graph.findPostsStatsByMonthAndYear")
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }

    public List<GraphCommentsStat> findCommentsStats(){
        EntityManager em = emf.getEntityManager();
        try{
            return (List<GraphCommentsStat>) em.createNamedQuery("Graph.findCommentsStats").getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
}
