package org.devdom.tracker.bean;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.devdom.tracker.model.dao.TopInteractionDao;
import org.devdom.tracker.model.dto.TopInteraction;

/**
 *
 * @author Carlos Vasquez
 */
public class TopInteractionController {
    
    TopInteractionDao dao = new TopInteractionDao();
    private final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    private final Map<String,String> request = externalContext.getRequestParameterMap();        
    private final String groupId = request.get("g");
    
    public List<TopInteraction> getTopMostLikedPostByGroupId(){
        try {
            return dao.findTopMostLikedPostByGroupId(groupId);
        } catch (Exception ex) {
            Logger.getLogger(TopInteractionController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<TopInteraction> getTopMostCommentedPostByGroupId(){
        try {
            return dao.findTopMostCommentedPostByGroupId(groupId);
        } catch (Exception ex) {
            Logger.getLogger(TopInteractionController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<TopInteraction> getTopMostLikedCommentsByGroupId(){
        try {
            return dao.findTopMostLikedCommentsByGroupId(groupId);
        } catch (Exception ex) {
            Logger.getLogger(TopInteractionController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
