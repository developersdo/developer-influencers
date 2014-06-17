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
    @NamedStoredProcedureQuery( name="EducationHistory.findWorkByUid",
                                procedureName="findWorkByUid",
                                returnsResultSet=true,
                                resultClass=EducationHistory.class,
                                parameters={@StoredProcedureParameter(queryParameter="from_id",
                                                                      name="from_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class)}
                                )
})
public class EducationHistory implements Serializable {
    
    @Id
    @Column(name = "institution_id")
    private String institutionId;
    
    @Column(name = "category")
    private String category;
            
    @Column(name = "institution")
    private String institution;
    
    @Column(name = "type")
    String type;
    
    @Column(name = "from_id")
    String fromId;

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    @Override
    public String toString() {
        return "EducationHistory{" + "institutionId=" + institutionId + ", category=" + category + ", institution=" + institution + ", type=" + type + ", fromId=" + fromId + '}';
    }
    
}
