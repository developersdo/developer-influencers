package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@Entity
@Table(name = "fb_groups")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupInformation.findAll", 
                query = "SELECT g FROM GroupInformation g"),
    @NamedQuery(name = "GroupInformation.findByGroupId", 
                query = "SELECT g FROM GroupInformation g WHERE g.groupId = :group_id")
})
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name="GroupInformation.updTopInfluencers",
                                procedureName="updTopInfluencers",
                                returnsResultSet=false,
                                parameters={@StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class),
                                            @StoredProcedureParameter(queryParameter="min_interaction",
                                                                      name="min_interaction",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class)}
                                ),
    @NamedStoredProcedureQuery( name="GroupInformation.updTablesInformationYear",
                                procedureName="updTablesInformationYear",
                                returnsResultSet=false
                               )
})
public class GroupInformation implements Serializable {

    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "fb_id")
    private String groupId;
    
    @Column(name = "name")
    private String groupName;
    
    @Column(name = "post_amount")
    private int postAmount;
    
    @Column(name = "comments_amount")
    private int commentsAmount;
    
    @Column(name = "min_interactions")
    private int minInteractions;

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

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the postAmount
     */
    public int getPostAmount() {
        return postAmount;
    }

    /**
     * @param postAmount the postAmount to set
     */
    public void setPostAmount(int postAmount) {
        this.postAmount = postAmount;
    }

    /**
     * @return the commentsAmount
     */
    public int getCommentsAmount() {
        return commentsAmount;
    }

    /**
     * @param commentsAmount the commentsAmount to set
     */
    public void setCommentsAmount(int commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    /**
     * @return the minInteractions
     */
    public int getMinInteractions() {
        return minInteractions;
    }

    /**
     * @param minInteractions the minInteractions to set
     */
    public void setMinInteractions(int minInteractions) {
        this.minInteractions = minInteractions;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

}
