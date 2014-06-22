package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@Entity
@Table(name = "dev_dom_education")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Education.findAll", query = "SELECT e FROM Education e"),
    @NamedQuery(name = "Education.findByFromId", query = "SELECT e FROM Education e WHERE e.fromId = :fromId"),
    @NamedQuery(name = "Education.findByInstitutionId", query = "SELECT e FROM Education e WHERE e.institutionId = :institutionId"),
    @NamedQuery(name = "Education.findByType", query = "SELECT e FROM Education e WHERE e.type = :type"),
    @NamedQuery(name = "Education.findByConcentration", query = "SELECT e FROM Education e WHERE e.concentration = :concentration")})
public class Education implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "from_id")
    private String fromId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "institution_id")
    private String institutionId;
    @Size(max = 120)
    @Column(name = "type")
    private String type;
    @Size(max = 600)
    @Column(name = "concentration")
    private String concentration;

    public Education() {
    }

    public Education(String fromId) {
        this.fromId = fromId;
    }

    public Education(String fromId, String institutionId) {
        this.fromId = fromId;
        this.institutionId = institutionId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fromId != null ? fromId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Education)) {
            return false;
        }
        Education other = (Education) object;
        return (this.fromId != null || other.fromId == null) && (this.fromId == null || this.fromId.equals(other.fromId));
    }

    @Override
    public String toString() {
        return "Education{" + "fromId=" + fromId + ", institutionId=" + institutionId + ", type=" + type + ", concentration=" + concentration + '}';
    }
    
}
