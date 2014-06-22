package org.devdom.tracker.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
public class FacebookInteraction implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Column(name = "from_id")
    private String fromId;
    
    @Column(name = "to_id")
    private String toId;
    
    @Column(name = "created_time")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdTime;
    
    @Column(name = "type")
    private String type;
    
    @Id
    @Column(name = "object_id")
    private String objectId;
    
    @Column(name = "group_id")
    private String groupId;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
