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
                                returnsResultSet=true,
                                resultClass=GroupInformation.class,
                                parameters={@StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class),
                                            @StoredProcedureParameter(queryParameter="min_interaction",
                                                                      name="min_interaction",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class)}
                                ),
    @NamedStoredProcedureQuery( name="GroupInformation.updTablesInformationYear",
                                procedureName="updTablesInformationYear",
                                returnsResultSet=true,
                                resultClass=GroupInformation.class
                               ),
    @NamedStoredProcedureQuery( name="GroupInformation.findGroupActivityByGroupId",
                                procedureName="findGroupActivityByGroupId",
                                returnsResultSet=true,
                                resultClass=GroupInformation.class,
                                parameters={@StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class
                                                                      )}
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
    
    @Column(name = "active_members_in_year")
    private int activeMembersInYear;
    
    @Column(name = "post_per_day")
    private int postPerDay;
    
    @Column(name = "comments_per_day")
    private int commentsPerDay;
    
    @Column(name = "created_time_from")
    private String createdTimeFrom;
    
    @Column(name = "created_time_to")
    private String createdTimeTo; 

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

    /**
     * @return the activeMembersInYear
     */
    public int getActiveMembersInYear() {
        return activeMembersInYear;
    }

    /**
     * @param activeMembersInYear the activeMembersInYear to set
     */
    public void setActiveMembersInYear(int activeMembersInYear) {
        this.activeMembersInYear = activeMembersInYear;
    }

    /**
     * @return the postPerDay
     */
    public int getPostPerDay() {
        return postPerDay;
    }

    /**
     * @param postPerDay the postPerDay to set
     */
    public void setPostPerDay(int postPerDay) {
        this.postPerDay = postPerDay;
    }

    /**
     * @return the commentsPerDay
     */
    public int getCommentsPerDay() {
        return commentsPerDay;
    }

    /**
     * @param commentsPerDay the commentsPerDay to set
     */
    public void setCommentsPerDay(int commentsPerDay) {
        this.commentsPerDay = commentsPerDay;
    }

    /**
     * @return the createdTimeFrom
     */
    public String getCreatedTimeFrom() {
        return createdTimeFrom;
    }

    /**
     * @param createdTimeFrom the createdTimeFrom to set
     */
    public void setCreatedTimeFrom(String createdTimeFrom) {
        this.createdTimeFrom = createdTimeFrom;
    }

    /**
     * @return the createdTimeTo
     */
    public String getCreatedTimeTo() {
        return createdTimeTo;
    }

    /**
     * @param createdTimeTo the createdTimeTo to set
     */
    public void setCreatedTimeTo(String createdTimeTo) {
        this.createdTimeTo = createdTimeTo;
    }

}
