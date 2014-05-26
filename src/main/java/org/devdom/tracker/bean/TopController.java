package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.devdom.tracker.model.dto.Top;
import org.devdom.tracker.model.dao.TopDao;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@ManagedBean
@RequestScoped
public class TopController implements Serializable{

    List<Top> top20;
    /**
     * Listado de los 20 developers más influyentes de todos los grupos
     * @return 
     */ 
    public List<Top> getTop20Devs(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> request = externalContext.getRequestParameterMap();
        
        String groupId = request.get("g");
        try {
            TopDao topdao = new TopDao();
            top20 = topdao.findTop20Devs(groupId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return top20;
    }
    
}
