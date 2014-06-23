package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
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
    
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private final ExternalContext externalContext = facesContext.getExternalContext();
    private final HttpSession session = (HttpSession) externalContext.getSession(true);
    private final Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

    private final AdministrationController admin = new AdministrationController();
    /**
     * return la imagen guardada en session por el usuario que se encuentra logueado
     * desde Facebook
     * @return 
     */
    public String getProfilePicture(){
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
    public String getFacebookID(){
        FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");
        if(profile==null)
            return "";
        return profile.getUid();
    }
    
    /**
     * Método utilizado para evaludar si existe una cuenta logueada
     * @return 
     */
    public boolean isLogged(){
        boolean logged = session.getAttribute("facebook") != null;
        if(!logged){
            session.setAttribute("lastViewID",admin.getViewId());

            if(parameterMap.containsKey("g"))
                session.setAttribute("g", parameterMap.get("g"));

            if(parameterMap.containsKey("pid"))
                session.setAttribute("pid", parameterMap.get("pid"));

        }
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