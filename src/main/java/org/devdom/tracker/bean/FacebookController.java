package org.devdom.tracker.bean;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.devdom.tracker.model.dto.FacebookProfile;

/**
 *
 * @author Carlos Vasquez
 */

@ManagedBean
@SessionScoped
public class FacebookController implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

    public String getProfilePicture(){
        session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");
        if(profile==null)
            return "";
        if(profile.getPic_with_logo()==null)
            return "";
        return profile.getPic_with_logo();
    }
    
    public String getLoggedName(){
        session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");
        if(profile==null)
            return "";
        if(profile.getFirstName()==null || profile.getLastName()==null)
            return "";
        return profile.getFirstName() + " " + profile.getLastName();
    }

    public long getFacebookID(){
        session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");
        if(profile==null)
            return 0;
        return profile.getUid();
    }

    public String getAllPost(){
        try {
            Facebook facebook = (Facebook) session.getAttribute("facebook");
            Reading reading = new Reading();
            reading.limit(200);
            ResponseList<Post> post = facebook.getGroupFeed("201514949865358",reading);
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "valor";
    }
    
    public Post getPostInformation(String postId){
        Facebook facebook = (Facebook) session.getAttribute("facebook");
        try {
            return facebook.getPost(postId);
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isLogged(){
        facesContext = FacesContext.getCurrentInstance();
        session = (HttpSession) facesContext.getExternalContext().getSession(true);
        return session.getAttribute("facebook") != null;
    }

    public String getLoginButton(){
        return "<center><a id=\"fbbutton\" href=\"signin\">Iniciar sesión con facebook</a> <br/></center>";
    }
}