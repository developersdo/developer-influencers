package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.devdom.tracker.model.dao.InfluencersDao;
import org.devdom.tracker.model.dto.Influencers;

/**
 *
 * @author Carlos Vásquez Polanco
 */

@ManagedBean
@SessionScoped
public class InfluencersController implements Serializable{
    
    private static final long serialVersionUID = 1L;
    InfluencersDao dao = new InfluencersDao();
    
    /**
     * Listado de los 20 developers más influyentes de todos los grupos
     * @return 
     */
    public List<Influencers> getTopInfluencers(){
        try {
            return dao.findTop20DevsInfluents();
        } catch (Exception ex) {
            Logger.getLogger(InfluencersController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Utilizado para extraer tres developers ordenados por la posición y
     * teniendo como row central al developer que se pasa en el fromId
     * @param fromId
     * @param groupId
     * @return 
     */
    public List getPositionCarruselByUserIdAndGroupId(String fromId, String groupId){
        try {
            return dao.findPositionCarruselByUserIdAndGroupId(fromId, groupId);
        } catch (Exception ex) {
            Logger.getLogger(InfluencersController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
