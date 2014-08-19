package org.devdom.influencer.model.dto;

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
@Table(name = "work_institution")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkInstitution.findAll", query = "SELECT w FROM WorkInstitution w"),
    @NamedQuery(name = "WorkInstitution.findById", query = "SELECT w FROM WorkInstitution w WHERE w.id = :id"),
    @NamedQuery(name = "WorkInstitution.findByName", query = "SELECT w FROM WorkInstitution w WHERE w.name = :name"),
    @NamedQuery(name = "WorkInstitution.findByCategory", query = "SELECT w FROM WorkInstitution w WHERE w.category = :category")})
public class WorkInstitution implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "id")
    private String id;
    @Size(max = 200)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "category")
    private String category;

    public WorkInstitution() {
    }

    public WorkInstitution(String id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkInstitution)) {
            return false;
        }
        WorkInstitution other = (WorkInstitution) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "WorkInstitution{" + "id=" + id + ", name=" + name + ", category=" + category + '}';
    }

}