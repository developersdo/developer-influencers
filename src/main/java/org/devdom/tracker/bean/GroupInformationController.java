package org.devdom.tracker.bean;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.devdom.tracker.model.dao.GroupRatingDao;
import org.devdom.tracker.model.dto.GroupAdminsInformation;
import org.devdom.tracker.model.dto.GroupInformation;

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
    public GroupInformation getActivity(){
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
    
    public String getAdminsContainer(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> request = externalContext.getRequestParameterMap();

        String groupId = request.get("g");
        
        List<GroupAdminsInformation> admins = dao.findAdminsByGroupId(groupId);
        
        String html;
        if(admins.isEmpty()){
            html = "<div style=\"border:solid 1px #E0DBCD;background-color:#F5F3EF;height:10px;\">";
        }else{
            html = "<div style=\"border:solid 1px #E0DBCD;background-color:#F5F3EF;height:200px;\">";
        }
        
        html+= "<div id=\"profile-container\">";
        for(GroupAdminsInformation admin : admins){
            html+="<a href=\"profile.xhtml?"+admin.getUid()+"\">";
            html+="<figure>";
            html+="<div style=\"background: url("+admin.getPicture()+") no-repeat center hsl(200,50%,50%); background-size: cover;\"></div>";
            html+="<figcaption>"+admin.getFullName()+"</figcaption>";
            html+="</figure></a>";
        }
        html += "</div></div>";

        return html;
    }

}
