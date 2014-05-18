package org.devdom.tracker.bean;

import java.util.List;
import org.devdom.tracker.model.dao.GraphDao;
import org.devdom.tracker.model.dto.GraphStats;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class GraphController {
    
    private final GraphDao dao = new GraphDao();
    
    /**
     * Obtener valores para formar el gráfico de la cantidad de publicaciones por meses
     * @return 
     */
    public List<GraphStats> getPostsStats(){
        return dao.findPostsStats();
    }
    
    /**
     * Obtener valores para formar el gráfico de la cantidad de comentarios por meses
     * @return 
     */
    public List<GraphStats> getCommentsStats(){
        return dao.findCommentsStats();
    }
    
}
