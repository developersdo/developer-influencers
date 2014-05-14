package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    List<Influencers> influencers = null;
    
    /**
     * Listado de los 20 developers más influyentes de todos los grupos
     * @return 
     */
    public List<Influencers> getTopInfluencers(){
        try {
            return dao.findTop20DevsInfluents();
        } catch (Exception ex) {
            Logger.getLogger(InfluencersController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Utilizado para extraer tres developers ordenados por la posición y
     * teniendo como row central al developer que se pasa en el fromId
     * @return 
     */
    public List getPositionInformation(){
        FacebookController facebook = new FacebookController();
        System.out.println("entrando en getPositionInformation");
        final String FROM_ID = String.valueOf(facebook.getFacebookID());
        final String GROUP_ID = "0"; //Hace referencia al score universal de todos los grupos
        try {
            return dao.findPositionCarruselByUserIdAndGroupId(FROM_ID, GROUP_ID);
        } catch (Exception ex) {
            Logger.getLogger(InfluencersController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Retornar la primera posicion del listado de 3 influyentes
     * @return 
     */
    public Influencers getFirstPosition(){
        if(influencers==null)
            influencers = (List<Influencers>) getPositionInformation();
        
        if(influencers.size()>0)
            return influencers.get(0);
        
        return empty();
    }
    
    /**
     * Retornar la segunda posicion del listado de 3 influyentes
     * @return 
     */
    public Influencers getSecondPosition(){
        if(influencers==null)
            influencers = (List<Influencers>) getPositionInformation();
        
        if(influencers.size()>=2)
            return influencers.get(1);
        
        return empty();
    }
    
    /**
     * Retornar la tercera posicion del listado de 3 influyentes
     * @return 
     */
    public Influencers getThirdPosition(){
        if(influencers==null)
            influencers = (List<Influencers>) getPositionInformation();
        
        if(influencers.size()>=3)
            return influencers.get(2);
        
        return empty();
    }
    
    private Influencers empty(){
        Influencers emptyInfluencer = new Influencers();
        emptyInfluencer.setFromId(0);
        emptyInfluencer.setPosition(0);
        emptyInfluencer.setFullName("");
        return emptyInfluencer;
    }

}
