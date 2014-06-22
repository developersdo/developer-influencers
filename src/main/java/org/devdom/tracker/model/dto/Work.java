package org.devdom.tracker.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@Entity
@Table(name = "dev_dom_user_work")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Work.findAll", query = "SELECT w FROM Work w"),
    @NamedQuery(name = "Work.findByFromId", query = "SELECT w FROM Work w WHERE w.workPK.fromId = :fromId"),
    @NamedQuery(name = "Work.findByWorkId", query = "SELECT w FROM Work w WHERE w.workPK.workId = :workId"),
    @NamedQuery(name = "Work.findByCreatedTime", query = "SELECT w FROM Work w WHERE w.createdTime = :createdTime")})
public class Work implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkPK workPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public Work() {
    }

    public Work(WorkPK workPK) {
        this.workPK = workPK;
    }

    public Work(WorkPK workPK, Date createdTime) {
        this.workPK = workPK;
        this.createdTime = createdTime;
    }

    public Work(String fromId, String workId) {
        this.workPK = new WorkPK(fromId, workId);
    }

    public WorkPK getWorkPK() {
        return workPK;
    }

    public void setWorkPK(WorkPK workPK) {
        this.workPK = workPK;
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
        hash += (workPK != null ? workPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Work)) {
            return false;
        }
        Work other = (Work) object;
        return (this.workPK != null || other.workPK == null) && (this.workPK == null || this.workPK.equals(other.workPK));
    }

    @Override
    public String toString() {
        return "Work{" + "workPK=" + workPK + ", createdTime=" + createdTime + '}';
    }
    
}
