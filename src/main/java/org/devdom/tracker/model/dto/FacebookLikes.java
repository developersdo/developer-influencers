package org.devdom.tracker.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "dev_dom_likes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacebookLikes.findAll", query = "SELECT c FROM FacebookLikes c")
})
public class FacebookLikes extends FacebookInteraction implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * @return the fromId
     */
    @Override
    public String getFromId() {
        return super.getFromId();
    }

    /**
     * @param fromId the fromId to set
     */
    @Override
    public void setFromId(String fromId) {
        super.setFromId(fromId);
    }

    /**
     * @return the toId
     */
    @Override
    public String getToId() {
        return super.getToId();
    }

    /**
     * @param toId the toId to set
     */
    @Override
    public void setToId(String toId) {
        super.setToId(toId);
    }

    /**
     * @return the createdTime
     */
    @Override
    public Date getCreatedTime() {
        return super.getCreatedTime();
    }

    /**
     * @param createdTime the createdTime to set
     */
    @Override
    public void setCreatedTime(Date createdTime) {
        super.setCreatedTime(createdTime);
    }

    /**
     * @return the type
     */
    @Override
    public String getType() {
        return super.getType();
    }

    /**
     * @param type the type to set
     */
    @Override
    public void setType(String type) {
        super.setType(type);
    }

    /**
     * @return the objectId
     */
    @Override
    public String getObjectId() {
        return super.getObjectId();
    }

    /**
     * @param objectId the objectId to set
     */
    @Override
    public void setObjectId(String objectId) {
        super.setObjectId(objectId);
    }

    /**
     * @return the groupId
     */
    @Override
    public String getGroupId() {
        return super.getGroupId();
    }

    /**
     * @param groupId the groupId to set
     */
    @Override
    public void setGroupId(String groupId) {
        super.setGroupId(groupId);
    }

}
