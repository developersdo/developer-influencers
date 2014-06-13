package org.devdom.tracker.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.devdom.tracker.model.dao.StatDao;
import org.devdom.tracker.model.dto.FacebookProfile;
import org.devdom.tracker.model.dto.YearStat;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@ManagedBean
@RequestScoped
public class StatsController {
    
    public List<YearStat> getPositionsInTop(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacebookProfile profile = (FacebookProfile) session.getAttribute("profile");

        String uid = (profile!=null)?profile.getUid():"";
        StatDao dao = new StatDao();
        return dao.findTopPositionsInTop(uid);
        
    }
}
