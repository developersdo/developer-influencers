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
 * @author Ronny Placencia
 */
@Entity
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name="WorkHistory.findWorkByUid",
                                procedureName="findWorkByUid",
                                returnsResultSet=true,
                                resultClass=WorkHistory.class,
                                parameters={@StoredProcedureParameter(queryParameter="from_id",
                                                                      name="from_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class)}
                                )
})
public class WorkHistory implements Serializable {
    
    @Id
    @Column(name = "word_id")
    String workId;
    
    @Column(name = "work")
    String work;
    
    @Column(name = "category")
    String category;
    
    @Column(name = "from_id")
    String fromId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    @Override
    public String toString() {
        return "WorkHistory{" + "workId=" + workId + ", work=" + work + ", category=" + category + ", fromId=" + fromId + '}';
    }
    
}
