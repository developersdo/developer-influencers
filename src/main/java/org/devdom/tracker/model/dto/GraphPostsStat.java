package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name="Graph.findPostsStats", 
                                procedureName="findPostsStats",
                                returnsResultSet=true,
                                resultClass=GraphPostsStat.class),
    @NamedStoredProcedureQuery( name="Graph.findPostsStatsByMonthAndYear", 
                                procedureName="findPostsStatsByMonthAndYear",
                                returnsResultSet=true,
                                resultClass=GraphPostsStat.class,
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
public class GraphPostsStat extends GraphResult implements Serializable{
    
    @Override
    public void setAmount(int amount) {
        super.setAmount(amount);
    }
    
    @Override
    public int getAmount() {
        return super.getAmount();
    }

    @Override
    public void setMonths(String months) {
        super.setMonths(months);
    }
    
    @Override
    public String getMonths() {
        return super.getMonths();
    }

    @Override
    public void setGroupId(String groupId) {
        super.setGroupId(groupId);
    }

    @Override
    public String getGroupId() {
        return super.getGroupId();
    }
    
    @Override
    public void setGroupName(String groupName) {
        super.setGroupName(groupName);
    }

    @Override
    public String getGroupName() {
        return super.getGroupName();
    }

    @Override
    public void setRank(int rank) {
        super.setRank(rank);
    }
    
    @Override
    public int getRank() {
        return super.getRank();
    }
    
    @Override
    public void setGroupColumnId(String groupColumnId) {
        super.setGroupColumnId(groupColumnId);
    }
    
    @Override
    public String getGroupColumnId() {
        return super.getGroupColumnId();
    }
    
    @Override
    public void setYears(int years) {
        super.setYears(years);
    }

    @Override
    public int getYears() {
        return super.getYears();
    }

}