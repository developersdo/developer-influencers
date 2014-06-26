package org.devdom.influencer.bean;

import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.devdom.influencer.model.dao.StatDao;
import org.devdom.influencer.model.dto.YearStat;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@ManagedBean
@RequestScoped
public class StatsController {
    
    private final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    private final Map<String,String> request = externalContext.getRequestParameterMap();        
    private final String pid = request.get("pid");

    public List<YearStat> getPositionsInTop(){        
        StatDao dao = new StatDao();
        return dao.findTopPositionsInTop(pid);
    }
}