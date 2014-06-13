package org.devdom.tracker.bean;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.devdom.tracker.model.dao.GroupRatingDao;
import org.devdom.tracker.model.dao.StatDao;
import org.devdom.tracker.model.dto.FacebookProfile;
import org.devdom.tracker.model.dto.GroupInformation;
import org.devdom.tracker.model.dto.YearStat;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@ManagedBean
@RequestScoped
public class GroupInformationController {
    
    private final GroupRatingDao dao = new GroupRatingDao();
    
    public GroupInformation getGroupInformation(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> request = externalContext.getRequestParameterMap();
        
        String groupId = request.get("g");
        
        try {
            return dao.findGroupInformationById(groupId);
        } catch (Exception ex) {
            Logger.getLogger(GroupInformationController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Retornar informacion de las actividades de un grupo en especifico 
     * @return 
     */
    public GroupInformation getGroupActivityByGroupId(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> request = externalContext.getRequestParameterMap();

        String groupId = request.get("g");
        
        try{
            return dao.findGroupActivityByGroupId(groupId);
        }catch(Exception ex){
            Logger.getLogger(GroupInformationController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
