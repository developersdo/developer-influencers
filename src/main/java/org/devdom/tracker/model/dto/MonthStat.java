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
 * @author Carlos Vásquez Polanco
 */
@Entity
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name="stat.findMonthsStat", 
                                procedureName="findMonthsStat",
                                returnsResultSet=true,
                                resultClass=MonthStat.class,
                                parameters={@StoredProcedureParameter(queryParameter="month",
                                                                      name="month",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class),
                                            @StoredProcedureParameter(queryParameter="year",
                                                                      name="year",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class),
                                            @StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class),
                                            @StoredProcedureParameter(queryParameter="from_id",
                                                                      name="from_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class)}
                                )
})
public class MonthStat implements Serializable {
    
    @Id
    @Column(name = "from_id")    
    private String fromId;
    @Column(name = "current_rating")
    private String currentRating;
    @Column(name = "current_position")
    private int currentPosition;
    @Column(name = "current_month")
    private int currentMonth;
    @Column(name = "current_interactions")
    private int currentInteractions;
    @Column(name = "current_reactions")
    private int currentReactions;
    @Column(name = "previous_rating")
    private String previousRating;
    @Column(name = "previous_position")
    private int previousPosition;
    @Column(name = "previous_month")
    private int previousMonth;
    @Column(name = "previous_interactions")
    private int previousInteractions;
    @Column(name = "previous_reactions")
    private int previousReactions;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(String currentRating) {
        this.currentRating = currentRating;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public int getCurrentInteractions() {
        return currentInteractions;
    }

    public void setCurrentInteractions(int currentInteractions) {
        this.currentInteractions = currentInteractions;
    }

    public int getCurrentReactions() {
        return currentReactions;
    }

    public void setCurrentReactions(int currentReactions) {
        this.currentReactions = currentReactions;
    }

    public String getPreviousRating() {
        return previousRating;
    }

    public void setPreviousRating(String previousRating) {
        this.previousRating = previousRating;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(int previousPosition) {
        this.previousPosition = previousPosition;
    }

    public int getPreviousMonth() {
        return previousMonth;
    }

    public void setPreviousMonth(int previousMonth) {
        this.previousMonth = previousMonth;
    }

    public int getPreviousInteractions() {
        return previousInteractions;
    }

    public void setPreviousInteractions(int previousInteractions) {
        this.previousInteractions = previousInteractions;
    }

    public int getPreviousReactions() {
        return previousReactions;
    }

    public void setPreviousReactions(int previousReactions) {
        this.previousReactions = previousReactions;
    }

    @Override
    public String toString() {
        return "MonthStat{" + "fromId=" + fromId + ", currentRating=" + currentRating + ", currentPosition=" + currentPosition + ", currentMonth=" + currentMonth + ", currentInteractions=" + currentInteractions + ", currentReactions=" + currentReactions + ", previousRating=" + previousRating + ", previousPosition=" + previousPosition + ", previousMonth=" + previousMonth + ", previousInteractions=" + previousInteractions + ", previousReactions=" + previousReactions + '}';
    }

}
