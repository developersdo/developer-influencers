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
 * @author Carlos Vasquez
 */
@Entity
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name="GroupInformation.findAdminsByGroupId",
                                procedureName="findAdminsByGroupId",
                                returnsResultSet=true,
                                resultClass=GroupAdminsInformation.class,
                                parameters={@StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class
                                                                      )}
                                )
})
public class GroupAdminsInformation implements Serializable{

    @Id
    @Column(name = "rank")
    String rank;
    
    @Column(name = "group_id")
    String groupId;
    
    @Column(name = "first_name")
    String first_name;
    
    @Column(name = "last_name")
    String last_name;
    
    @Column(name = "uid")
    String uid;
    
    @Column(name = "position_id")
    String position;
    
    @Column(name = "ratio")
    String ratio;
    
    String picture;

    String fullName;
    
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getPicture() {
        return "https://graph.facebook.com/"+getUid()+"/picture?height=220&width=220";
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFullName() {
        return getFirst_name()+" "+getLast_name();
    }

}