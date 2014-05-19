package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
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
    @NamedStoredProcedureQuery( name="Graph.findPostsStats", 
                                procedureName="findPostsStats",
                                returnsResultSet=true,
                                resultClass=GraphStats.class),
    @NamedStoredProcedureQuery( name="Graph.findCommentsStats", 
                                procedureName="findCommentsStats",
                                returnsResultSet=true,
                                resultClass=GraphStats.class),
    @NamedStoredProcedureQuery( name="Graph.findPostsStatsByMonthAndYear", 
                                procedureName="findPostsStatsByMonthAndYear",
                                returnsResultSet=true,
                                resultClass=GraphStats.class,
                                parameters={@StoredProcedureParameter(queryParameter="month",
                                                                      name="month",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class),
                                            @StoredProcedureParameter(queryParameter="year",
                                                                      name="year",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class)
                                            })
})
public class GraphStats implements Serializable{
    
    @Column(name = "amount")
    private int amount;

    @Column(name = "months")
    private String months;

    @Column(name = "years")
    private int years;

    @Id
    @Column(name = "group_id", unique = false)
    private String groupId;

    @Column(name = "group_name")
    private String groupName;
    
    @Column(name = "group_column_id")
    private String groupColumnId;

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the months
     */
    public String getMonths() {
        return months;
    }

    /**
     * @param months the months to set
     */
    public void setMonths(String months) {
        this.months = months;
    }

    /**
     * @return the years
     */
    public int getYears() {
        return years;
    }

    /**
     * @param years the years to set
     */
    public void setYears(int years) {
        this.years = years;
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
     * @return the groupColumnId
     */
    public String getGroupColumnId() {
        return groupColumnId;
    }

    /**
     * @param groupColumnId the groupColumnId to set
     */
    public void setGroupColumnId(String groupColumnId) {
        this.groupColumnId = groupColumnId;
    }

}
