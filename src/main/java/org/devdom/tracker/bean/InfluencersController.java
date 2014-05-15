package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.devdom.tracker.model.dao.GroupRatingDao;
import org.devdom.tracker.model.dao.InfluencersDao;
import org.devdom.tracker.model.dto.GroupRating;
import org.devdom.tracker.model.dto.Influencers;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Carlos Vásquez Polanco
 */

@ManagedBean
@SessionScoped
public class InfluencersController implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private final InfluencersDao dao = new InfluencersDao();
    private List<Influencers> influencers = null;
    private final List<GroupRating> gaugeRating = null;
    private MenuModel menu = new DefaultMenuModel();
    
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
    public List<Influencers> getPositionInformation(){
        FacebookController facebook = new FacebookController();

        final String FROM_ID = String.valueOf(facebook.getFacebookID());
        final String GROUP_ID = "1"; //Hace referencia al score universal de todos los grupos
        try {
            return (List<Influencers>)dao.findPositionCarruselByUserIdAndGroupId(FROM_ID, GROUP_ID);
        } catch (Exception ex) {
            Logger.getLogger(InfluencersController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Obtener la lista de ratings que tiene un developer en los distintos grupos
     * de desarrollo de aplicaciones
     * @return 
     */
    public List<GroupRating> getGroupsRating(){
        FacebookController facebook = new FacebookController();
        final String FROM_ID = String.valueOf(facebook.getFacebookID());
        
        GroupRatingDao daoGroup = new GroupRatingDao();
        try{
            return (List<GroupRating>) daoGroup.findGroupsRatingByUserId(FROM_ID);
        }catch(Exception ex){
            Logger.getLogger(InfluencersController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Retorna el primer rating de la lista
     * 
     * @return 
     */
    public GroupRating getFirstGaugeRating(){

        if(getRatingList().size()>=1)
            return gaugeRating.get(0);
        
        return emptyGroupRow();
    }
    
    /**
     * Retorna el segundo rating de la lista
     * 
     * @return 
     */
    public GroupRating getSecondGaugeRating(){

        if(getRatingList().size()>=2)
            return gaugeRating.get(1);
        
        return emptyGroupRow();
    }
    
    /**
     * Retorna el tercer rating de la lista
     * 
     * @return 
     */
    public GroupRating getThirdGaugeRating(){
        
        if(getRatingList().size()>=3)
            return getRatingList().get(1);
        
        return emptyGroupRow();
    }
    
    /**
     * retorna la lista con todos los rating en los grupos para un developer
     * 
     * @return 
     */
    public List<GroupRating> getRatingList(){
        if(gaugeRating==null)
            return (List<GroupRating>) getGroupsRating();
        else
            return gaugeRating;
    }
    
    /**
     * Retornar la primera posicion del listado de 3 influyentes
     * 
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
     * 
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
     * 
     * @return 
     */
    public Influencers getThirdPosition(){
        if(influencers==null)
            influencers = (List<Influencers>) getPositionInformation();
        
        if(influencers.size()>=3)
            return influencers.get(2);
        
        return empty();
    }
    
    private GroupRating emptyGroupRow(){
        GroupRating empty = new GroupRating();
        empty.setFromId(0);
        empty.setGroupId("0");
        empty.setGroupName("");
        empty.setPosition(0);
        return empty;
    }
    
    private Influencers empty(){
        Influencers emptyInfluencer = new Influencers();
        emptyInfluencer.setFromId(0);
        emptyInfluencer.setPosition(0);
        emptyInfluencer.setFullName("empty");
        return emptyInfluencer;
    }
    
    public MenuModel getMenuItems(){
        MenuModel model = new DefaultMenuModel();
        DefaultSubMenu submenu = new DefaultSubMenu();
        submenu.setLabel("Grupos");
        submenu.setId("influencersMenu");

        List<GroupRating> groups = getRatingList();

        groups.stream().forEach((group) -> {
            String displayValue = group.getGroupName() + " ("+group.getRatio()+")";
            
            DefaultMenuItem item = new DefaultMenuItem();
            item.setValue(displayValue);
            item.setId(group.getGroupId());
            item.setUrl("groupstats.xhtml");
            item.setStyle("font-size:12px;");
            submenu.addElement(item);
        });

        model.addElement(submenu);

        return model;
    }

}
