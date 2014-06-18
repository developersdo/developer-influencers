package org.devdom.tracker.bean;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.devdom.tracker.model.dao.ProfileDao;
import org.devdom.tracker.model.dto.EducationHistory;
import org.devdom.tracker.model.dto.FacebookProfile;
import org.devdom.tracker.model.dto.MasterProfile;
import org.devdom.tracker.model.dto.Skill;
import org.devdom.tracker.model.dto.Work;
import org.devdom.tracker.model.dto.WorkHistory;

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
