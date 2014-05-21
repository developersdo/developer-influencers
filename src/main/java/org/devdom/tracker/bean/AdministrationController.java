package org.devdom.tracker.bean;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.util.Constants;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@ManagedBean
@ApplicationScoped
public class AdministrationController implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public String getMojarraVersion(){
        Package p = FacesContext.class.getPackage();
        return p.getImplementationTitle() + " " + p.getImplementationVersion();
    }

    public String getPrimefacesVersion(){
        return Constants.ContextParams.class.getPackage().getImplementationTitle() + 
                " " + Constants.ContextParams.class.getPackage().getImplementationVersion();
    }
}