package org.devdom.influencer.bean;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.devdom.influencer.model.dao.ProfileDao;
import org.devdom.influencer.model.dto.EducationHistory;
import org.devdom.influencer.model.dto.FacebookProfile;
import org.devdom.influencer.model.dto.MasterProfile;
import org.devdom.influencer.model.dto.Skill;
import org.devdom.influencer.model.dto.WorkHistory;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@ManagedBean
@RequestScoped
public class ProfileController {
    
    private final ProfileDao dao = new ProfileDao();
    private final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    private final Map<String,String> request = externalContext.getRequestParameterMap();        
    private final String pid = request.get("pid");
    private MasterProfile masterProfile;
    
    public MasterProfile getProfile(){
        if(!"".equals(pid)){
            FacebookProfile profile = getInformation();
            List<Skill> skills = getSkills();
            List<WorkHistory> works = this.getWorks();
            List<EducationHistory> eduation = this.getEduationInformation();

            masterProfile.setProfile(profile);
            masterProfile.setEducation(eduation);
            masterProfile.setSkill(skills);
            masterProfile.setWork(works);
            masterProfile.setFromId(pid);
            return masterProfile;
        }
        return null;
    }

    private String header(String title, String style1, String style2){
        String html = "<h4 style=\""+style1+"\"><span class=\"glyphicon glyphicon-star\"></span>&nbsp;"+title+"</h4><br/>";
               html += "<hr style=\" "+style2+" \"/>";
        return html;
    }

    private String header(String title, String style1){
        return header(title, style1,"color:blue;position:relative;top:-42px;");
    }

    private String header(String title){
        return header(title,"color:blue;");
    }

    public String getEducationPanel(){
        String html = header("Educación","color:blue;width:170px;");
        html += "<div style=\"position:relative;top:-55px;\">";
        html = getEduationInformation().stream().map((education) -> 
                "<h5 style=\"float:left;padding-left:20px;\"><span class=\"label label-default\">"+education.getInstitution()+"</span></h5>")
                .reduce(html, String::concat);
        html += "</div><br/>";
        return html;
    }

    public String getWorksPanel(){
        String html = header("Historial laboral","color:blue;width:170px;");
        html += "<div style=\"position:relative;top:-50px;\">";
        html = getWorks().stream().map((work) -> 
                "<h5 style=\"float:left;padding-left:20px;\"><span class=\"label label-default\">"+work.getWork()+"</span></h5>")
                .reduce(html, String::concat);
        html +="</div><br/>";
        return html;
    }

    public String getSkillsPanel(){
        String html = header("Skillset","color:blue;position:relative;float:left;height:80px;width:100%;top:-38px;","position:relative;top:22px;left:-250px;width:100%;top:10px;");
        html += "<div style=\"position:relative;top:-65px;\">";
        boolean first=true;
        String categoryId = "-1";

        for (Skill skill : getSkills()) {
            if(!categoryId.equals(skill.getCategoryId())){
                if(!first){
                    html+="<br/><br/>";
                }
                html += "<h5 style=\"width:430px;\">";
                html += "<span style=\"color:red;\" class=\"glyphicon glyphicon-chevron-right\"></span>&nbsp;"+ skill.getCategory();
                html += "</h5>";
                first=false;
            }
            html+="<h5 style=\"float:left;padding-left:20px;\"><span class=\"label label-default\">"+skill.getName()+"</span></h5>";
            categoryId = skill.getCategoryId();
        }
        html +="</div>";
        return html;
    }
    
    public FacebookProfile getInformation(){
        if(!"".equals(pid)){
            try {
                return dao.getProfileInformation(pid);
            } catch (Exception ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public List<Skill> getSkills(){
        if(!"".equals(pid)){
            try{
                return dao.getSkills(pid);
            }catch(Exception ex){
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public List<WorkHistory> getWorks(){

        if(!"".equals(pid)){
            try{
                return dao.getWorks(pid);
            }catch(Exception ex){
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public List<EducationHistory> getEduationInformation(){
        if(!"".equals(pid)){
            try{
                return dao.getEducationInformation(pid);
            }catch(Exception ex){
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
}
