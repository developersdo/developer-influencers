package org.devdom.tracker.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.devdom.tracker.model.dto.FacebookProfile;

/**
 *
 * @author Carlos Vasquez
 */
@ManagedBean
@RequestScoped
public class FacebookController implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

    /**
     * return la imagen guardada en session por el usuario que se encuentra logueado
     * desde Facebook
     * @return 
     */
    public String getProfilePicture(){
        session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");
        if(profile==null)
            return "";
        if(profile.getPic_with_logo()==null)
            return "";
        return profile.getPic_with_logo();
    }
    
    /**
     * Nombre completo de Facebook del usuario que se encuentro logueado
     * @return 
     */
    public String getLoggedName(){
        session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");
        if(profile==null)
            return "";
        if(profile.getFirstName()==null || profile.getLastName()==null)
            return "";
        return profile.getFirstName() + " " + profile.getLastName();
    }

    /**
     * Retorna el Id de Facebook del usuario logueado, en caso de que sea necesario
     * @return 
     */
    public long getFacebookID(){
        session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");
        if(profile==null)
            return 0;
        return profile.getUid();
    }
    
    /**
     * Método utilizado para evaludar si existe una cuenta logueada
     * @return 
     */
    public boolean isLogged(){
        facesContext = FacesContext.getCurrentInstance();
        session = (HttpSession) facesContext.getExternalContext().getSession(true);
        return session.getAttribute("facebook") != null;
    }

    /**
     * 
     * @return 
     */
    public String getLoginButton(){
        return "<br/><br/><center><a id=\"fbbutton\" href=\"signin\">Iniciar sesión con facebook</a> <br/></center>";
    }
}