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
@Table(name = "fb_locations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locations.findAll", query = "SELECT l FROM Locations l"),
    @NamedQuery(name = "Locations.findById", query = "SELECT l FROM Locations l WHERE l.id = :id"),
    @NamedQuery(name = "Locations.findByCity", query = "SELECT l FROM Locations l WHERE l.city = :city"),
    @NamedQuery(name = "Locations.findByState", query = "SELECT l FROM Locations l WHERE l.state = :state"),
    @NamedQuery(name = "Locations.findByCountry", query = "SELECT l FROM Locations l WHERE l.country = :country"),
    @NamedQuery(name = "Locations.findByLongitude", query = "SELECT l FROM Locations l WHERE l.longitude = :longitude"),
    @NamedQuery(name = "Locations.findByLatitude", query = "SELECT l FROM Locations l WHERE l.latitude = :latitude"),
    @NamedQuery(name = "Locations.findByName", query = "SELECT l FROM Locations l WHERE l.name = :name")})
public class Locations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "id")
    private String id;
    @Size(max = 150)
    @Column(name = "city")
    private String city;
    @Size(max = 150)
    @Column(name = "state")
    private String state;
    @Size(max = 150)
    @Column(name = "country")
    private String country;
    @Size(max = 30)
    @Column(name = "longitude")
    private String longitude;
    @Size(max = 30)
    @Column(name = "latitude")
    private String latitude;
    @Size(max = 600)
    @Column(name = "name")
    private String name;

    public Locations() {
    }

    public Locations(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation_state() {
        return state;
    }

    public void setLocation_state(String location_state) {
        this.state = location_state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Locations)) {
            return false;
        }
        Locations other = (Locations) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Locations{" + "id=" + id + ", city=" + city + ", state=" + state + ", country=" + country + ", longitude=" + longitude + ", latitude=" + latitude + ", name=" + name + '}';
    }
    
}
