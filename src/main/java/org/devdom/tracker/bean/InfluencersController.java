package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.devdom.tracker.model.dao.InfluencersDao;
import org.devdom.tracker.model.dto.Influencers;

/**
 *
 * @author Carlos Vásquez Polanco
 */

@ManagedBean
@SessionScoped
public class InfluencersController implements Serializable{
    
    private static final long serialVersionUID = 1L;
    InfluencersDao dao = new InfluencersDao();
    
    public List<Influencers> getTopInfluencers(){
        return dao.findTop10DevsInfluents();
    }
    
}
