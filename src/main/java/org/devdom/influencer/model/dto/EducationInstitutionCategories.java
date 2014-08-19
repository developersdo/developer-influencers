package org.devdom.influencer.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@Entity
@Table(name = "education_institution_categories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EducationInstitutionCategories.findAll", query = "SELECT e FROM EducationInstitutionCategories e"),
    @NamedQuery(name = "EducationInstitutionCategories.findById", query = "SELECT e FROM EducationInstitutionCategories e WHERE e.id = :id"),
    @NamedQuery(name = "EducationInstitutionCategories.findByName", query = "SELECT e FROM EducationInstitutionCategories e WHERE e.name = :name"),
    @NamedQuery(name = "EducationInstitutionCategories.findByCreatedTime", query = "SELECT e FROM EducationInstitutionCategories e WHERE e.createdTime = :createdTime")})
public class EducationInstitutionCategories implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "name")
    private String name;
    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public EducationInstitutionCategories() {
    }

    public EducationInstitutionCategories(String id) {
        this.id = id;
    }

    public EducationInstitutionCategories(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EducationInstitutionCategories)) {
            return false;
        }
        EducationInstitutionCategories other = (EducationInstitutionCategories) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "EducationInstitutionCategories{" + "id=" + id + ", name=" + name + ", createdTime=" + createdTime + '}';
    }
    
}
