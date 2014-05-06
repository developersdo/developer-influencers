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
@Table(name = "dev_dom_post")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacebookPost.findAll", query = "SELECT u FROM FacebookPost u")
})
public class FacebookPost implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "post_id")
    private String postId;
    
    @Column(name = "from_id")
    private String fromId;
    
    @Column(name = "message")
    private String message;
    
    @Column(name = "like_count")
    private Integer likeCount;
    
    @Column(name = "created_time")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    
    @Column(name = "group_id")
    private String groupId;
    
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

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
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the likeCount
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * @param likeCount the likeCount to set
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
