package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.util.Constants;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@ManagedBean
@RequestScoped
public class AdministrationController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private FacesContext facesContext = null;
    private UIViewRoot uiRoot = null;
    private String viewId = "";

    /**
     * Retornar la versión de JSF que se utiliza en el proyecto
     * @return 
     */
    public String getMojarraVersion(){
        Package p = FacesContext.class.getPackage();
        return p.getImplementationTitle() + " " + p.getImplementationVersion();
    }

    /**
     * Return la versión que se utiliza en el proyecto de PrimeFaces
     * @return 
     */
    public String getPrimefacesVersion(){
        return Constants.ContextParams.class.getPackage().getImplementationTitle() + 
                " " + Constants.ContextParams.class.getPackage().getImplementationVersion();
    }
    
    /**
     * Retornar el Id de grupo en el que se encuentra navegando el usuario
     * @return 
     */
    public String getActiveGroupId(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> request = externalContext.getRequestParameterMap();
        return (request.get("g")!=null)?request.get("g"):"";
        
    }

    /**
     * Retornar el indice del menú en que se encuentra activo según el viewId seleccionado
     * @return 
     */
    public int getActiveIndex(){
        facesContext = FacesContext.getCurrentInstance();
        uiRoot = facesContext.getViewRoot();
        viewId = uiRoot.getViewId();

        switch(viewId){
            case "/groupTop20.xhtml" : return 0;
            case "/groupDetails.xhtml" : return 1;
            case "/groupInfluencerYesterday.xhtml" : return 2;
            case "/groupInfluencerMonth.xhtml" : return 3;
            default : return 0;
        }
    }
}