package org.devdom.tracker.model.dto;

import java.io.Serializable;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class FacebookProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    private long uid = 0;
    private String firstName;
    private String lastName;
    private String birthday;
    private String email;
    private String pic_with_logo;
    private String sex;

    /**
     * @return the uid
     */
    public long getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(long uid) {
        this.uid = uid;
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
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the pic_with_logo
     */
    public String getPic_with_logo() {
        return pic_with_logo;
    }

    /**
     * @param pic_with_logo the pic_with_logo to set
     */
    public void setPic_with_logo(String pic_with_logo) {
        this.pic_with_logo = pic_with_logo;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }
    
}
