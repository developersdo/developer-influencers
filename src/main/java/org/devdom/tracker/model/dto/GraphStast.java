package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class GraphStast implements Serializable{
    
    @Column(name = "amount")
    private int amount;
    
    @Column(name = "months")
    private int months;
    
    @Column(name = "years")
    private int years;
    
    @Column(name = "group_id")
    private String groupId;

    @Column(name = "group_name")
    private String groupName;

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
    public int getMonths() {
        return months;
    }

    /**
     * @param months the months to set
     */
    public void setMonths(int months) {
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

}
