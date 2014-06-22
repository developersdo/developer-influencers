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
@Table(name = "education_institution")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EducationInstitution.findAll", query = "SELECT e FROM EducationInstitution e"),
    @NamedQuery(name = "EducationInstitution.findById", query = "SELECT e FROM EducationInstitution e WHERE e.id = :id"),
    @NamedQuery(name = "EducationInstitution.findByAbout", query = "SELECT e FROM EducationInstitution e WHERE e.about = :about"),
    @NamedQuery(name = "EducationInstitution.findByCategory", query = "SELECT e FROM EducationInstitution e WHERE e.category = :category"),
    @NamedQuery(name = "EducationInstitution.findByLocationCity", query = "SELECT e FROM EducationInstitution e WHERE e.locationCity = :locationCity"),
    @NamedQuery(name = "EducationInstitution.findByLocationCountry", query = "SELECT e FROM EducationInstitution e WHERE e.locationCountry = :locationCountry"),
    @NamedQuery(name = "EducationInstitution.findByLocationStreet", query = "SELECT e FROM EducationInstitution e WHERE e.locationStreet = :locationStreet"),
    @NamedQuery(name = "EducationInstitution.findByWebsite", query = "SELECT e FROM EducationInstitution e WHERE e.website = :website"),
    @NamedQuery(name = "EducationInstitution.findByName", query = "SELECT e FROM EducationInstitution e WHERE e.name = :name"),
    @NamedQuery(name = "EducationInstitution.findByLatitude", query = "SELECT e FROM EducationInstitution e WHERE e.latitude = :latitude"),
    @NamedQuery(name = "EducationInstitution.findByLongitude", query = "SELECT e FROM EducationInstitution e WHERE e.longitude = :longitude")})
public class EducationInstitution implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "id")
    private String id;
    @Size(max = 5000)
    @Column(name = "about")
    private String about;
    @Size(max = 120)
    @Column(name = "category")
    private String category;
    @Size(max = 300)
    @Column(name = "location_city")
    private String locationCity;
    @Size(max = 300)
    @Column(name = "location_country")
    private String locationCountry;
    @Size(max = 300)
    @Column(name = "location_street")
    private String locationStreet;
    @Size(max = 120)
    @Column(name = "website")
    private String website;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "latitude")
    private String latitude;
    @Size(max = 100)
    @Column(name = "longitude")
    private String longitude;

    public EducationInstitution() {
    }

    public EducationInstitution(String id) {
        this.id = id;
    }

    public EducationInstitution(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }

    public String getLocationStreet() {
        return locationStreet;
    }

    public void setLocationStreet(String locationStreet) {
        this.locationStreet = locationStreet;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EducationInstitution)) {
            return false;
        }
        EducationInstitution other = (EducationInstitution) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "EducationInstitution{" + "id=" + id + ", about=" + about + ", category=" + category + ", locationCity=" + locationCity + ", locationCountry=" + locationCountry + ", locationStreet=" + locationStreet + ", website=" + website + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

}
