package org.devdom.tracker.bean;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.devdom.tracker.model.dao.InfluencerDao;
import org.devdom.tracker.model.dto.Influencer;

/**
 *
 * @author Carlos Vásquez Polanco
 */ 
@ManagedBean
public class TopController {

    /**
     * Listado de los 20 developers más influyentes de todos los grupos
     * @return 
     */
    public List<Influencer> getTop20(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> request = externalContext.getRequestParameterMap();
        
        String groupId = request.get("g");
        try {
            InfluencerDao daoInf = new InfluencerDao();
            return daoInf.findTop20DevsInfluencer(groupId);
        } catch (Exception ex) {
            Logger.getLogger(InfluencerController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
