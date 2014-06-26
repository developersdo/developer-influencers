package org.devdom.influencer.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.influencer.model.dto.GraphCommentsStat;
import org.devdom.influencer.model.dto.GraphPostsStat;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class GraphDao {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    
    public List<GraphPostsStat> findPostsStats(){
        
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
        try{
            return (List<GraphCommentsStat>) em.createNamedQuery("Graph.findCommentsStats").getResultList();
        }finally{
            if(em.isOpen())
                em.close();
        }
    }
    
}
