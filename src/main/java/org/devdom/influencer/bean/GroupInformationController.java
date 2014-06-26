package org.devdom.influencer.bean;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.devdom.influencer.model.dao.GroupRatingDao;
import org.devdom.influencer.model.dto.GroupAdminsInformation;
import org.devdom.influencer.model.dto.GroupInformation;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@ManagedBean
@RequestScoped
public class GroupInformationController {
    
    private final GroupRatingDao dao = new GroupRatingDao();
    private final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    private final Map<String,String> request = externalContext.getRequestParameterMap();        
    private final String groupId = request.get("g");
    
    public GroupInformation getGroupInformation(){
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
        try{
            return dao.findGroupActivityByGroupId(groupId);
        }catch(Exception ex){
            Logger.getLogger(GroupInformationController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String getAdminsContainer(){
        
        List<GroupAdminsInformation> admins = dao.findAdminsByGroupId(groupId);
        
        String html;
        if(admins.isEmpty()){
            html = "<div style=\"border:solid 1px #E0DBCD;background-color:#F5F3EF;height:10px;\">";
        }else{
            html = "<div style=\"border:solid 1px #E0DBCD;background-color:#F5F3EF;height:200px;\">";
        }
        
        html+= "<div id=\"profile-container\">";
        for(GroupAdminsInformation admin : admins){
            html+="<a href=\"profile.xhtml?pid="+admin.getUid()+"\">";
            html+="<figure>";
            html+="<div style=\"background: url("+admin.getPicture()+") no-repeat center hsl(200,50%,50%); background-size: cover;\">";
            html+="<span class=\"figure-position\">\n";
            html+="#"+ admin.getPosition();
            html+="</span>";
            html+="</div>";
            html+="<figcaption>"+admin.getFullName()+"</figcaption>";
            html+="</figure></a>";
        }
        html += "</div></div>";

        return html;
    }

}
