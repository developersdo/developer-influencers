package org.devdom.tracker.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vásquez Polanco
 */

@Entity
@Table(name = "dev_dom_mentions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacebookMentions.findAll", query = "SELECT c FROM FacebookMentions c")
})
public class FacebookMentions  implements Serializable {
    
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

    /**
     * @return the fromId
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * @param fromId the fromId to set
     */
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    /**
     * @return the toId
     */
    public String getToId() {
        return toId;
    }

    /**
     * @param toId the toId to set
     */
    public void setToId(String toId) {
        this.toId = toId;
    }

    /**
     * @return the createdTime
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param objectId the objectId to set
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
