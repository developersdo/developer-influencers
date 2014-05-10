package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@Entity
@Table(name = "dev_dom_user")
@XmlRootElement
public class FacebookMember implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uid")
    private String uid = "";
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "quiz_authorized")
    private boolean quizAuthorized;
    
    @Column(name = "authorization_code")
    private String authorizationCode;
    
    @Column(name = "roles")
    private String roles;
    
    @Column(name = "pic")
    private String pic;

    public FacebookMember() {
    }
    
    /**
     * Definir una nueva instancia desde el constructor
     * @param uid
     * @param firstName
     * @param lastName
     * @param pic 
     */
    public FacebookMember(String uid, String firstName, String lastName, String pic){
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pic = pic;
    }

    /**
     * @return Facebook Id
     */
    public String getId() {
        return getUid();
    }

    /**
     * @param id 
     */
    public void setId(String id) {
        this.setUid(id);
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the quizAuthorized
     */
    public boolean isQuizAuthorized() {
        return quizAuthorized;
    }

    /**
     * @param quizAuthorized the quizAuthorized to set
     */
    public void setQuizAuthorized(boolean quizAuthorized) {
        this.quizAuthorized = quizAuthorized;
    }

    /**
     * @return the authorizationCode
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * @param authorizationCode the authorizationCode to set
     */
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    /**
     * @return the roles
     */
    public String getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(String roles) {
        this.roles = roles;
    }

    /**
     * @return the pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic the pic to set
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

}
