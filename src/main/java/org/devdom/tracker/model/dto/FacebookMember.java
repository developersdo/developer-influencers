package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name = "uid")
    private String uid;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "pic")
    private String pic;
    
    @Column(name = "birthday_date")
    private String birthdayDate;

    @Column(name = "email")
    private String email;
    
    @Column(name = "sex")
    private String sex;

    @Column(name = "current_location_id")
    private String currentLocationId;
    
    public FacebookMember() {
    }
    
    /**
     * Definir una nueva instancia desde el constructor
     * @param uid
     * @param firstName
     * @param lastName
     * @param pic 
     */
    public FacebookMember(String uid /*Id de Facebook*/, String firstName, String lastName, String pic){
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

    /**
     * @return the birthdayDate
     */
    public String getBirthdayDate() {
        return birthdayDate;
    }

    /**
     * @param birthdayDate the birthdayDate to set
     */
    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(String currentLocationId) {
        this.currentLocationId = currentLocationId;
    }
    
    @Override
    public String toString() {
        return "FacebookMember{" + "uid=" + uid + ", firstName=" + firstName + ", lastName=" + lastName + ", pic=" + pic + ", birthdayDate=" + birthdayDate + ", email=" + email + '}';
    }

}