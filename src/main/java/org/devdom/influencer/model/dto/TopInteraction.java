package org.devdom.influencer.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import org.devdom.influencer.util.Utils;
import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@Entity
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name="TopInteractions.findTopMostLikedPostByGroupId",
                                procedureName="findTopMostLikedPostByGroupId",
                                returnsResultSet=true,
                                resultClass=TopInteraction.class,
                                parameters={@StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class)}
                                ),
    @NamedStoredProcedureQuery( name="TopInteractions.findTopMostCommentedPostByGroupId",
                                procedureName="findTopMostCommentedPostByGroupId",
                                returnsResultSet=true,
                                resultClass=TopInteraction.class,
                                parameters={@StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class)}
                                ),
    @NamedStoredProcedureQuery( name="TopInteractions.findTopMostLikedCommentsByGroupId",
                                procedureName="findTopMostLikedCommentsByGroupId",
                                returnsResultSet=true,
                                resultClass=TopInteraction.class,
                                parameters={@StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class)}
                                )
})
public class TopInteraction implements Serializable {
    
    @Id
    @Column(name = "uid")
    private String uid;
    @Id
    @Column(name = "object_id")
    private String objectId;
    @Id
    @Column(name = "group_id")
    private String groupId;

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "top_count")
    private String topCount;

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "group_name")
    private String groupName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName(){
        return getFirstName()+" "+getLastName();
    }
    
    public String getTopCount() {
        return topCount;
    }

    public void setTopCount(String topCount) {
        this.topCount = topCount;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPicture() {
        return "https://graph.facebook.com/"+getUid()+"/picture?height=50&width=50";
    }
    
    @Override
    public String toString() {
        return "TopInteractions{" + "uid=" + uid + ", objectId=" + objectId + ", groupId=" + groupId + ", firstName=" + firstName + ", lastName=" + lastName + ", topCount=" + topCount + ", createdTime=" + createdTime + ", groupName=" + groupName + '}';
    }
    
}
