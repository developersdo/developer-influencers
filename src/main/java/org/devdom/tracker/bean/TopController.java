package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.devdom.tracker.model.dao.MonthStatDao;
import org.devdom.tracker.model.dao.TopDao;
import org.devdom.tracker.model.dto.FacebookProfile;
import org.devdom.tracker.model.dto.MonthStat;
import org.devdom.tracker.model.dto.Top;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@ManagedBean
@RequestScoped
public class TopController implements Serializable{

    List<Top> top;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    private final FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");
    
    /**
     * Listado de los 20 developers más influyentes de todos los grupos
     * @return 
     */ 
    public List<Top> getTop20Devs(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> request = externalContext.getRequestParameterMap();
        
        String groupId = request.get("g");
        try {
            TopDao topdao = new TopDao();
            top = topdao.findTop20Devs(groupId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return top;
    }
    
    public List<Top> getTop300Devs(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> request = externalContext.getRequestParameterMap();
        
        String groupId = request.get("g");
        try {
            TopDao topdao = new TopDao();
            top = topdao.findTop300Devs(groupId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return top;
    }
    
    public List<MonthStat> getTopPositionsInTop(){
        String uid = (profile!=null)?profile.getUid():"";
        MonthStatDao dao = new MonthStatDao();
        return dao.findTopPositionsInTop(uid);
    }
    
}
