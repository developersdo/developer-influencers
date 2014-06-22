package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@Embeddable
public class WorkPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "from_id")
    private String fromId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "work_id")
    private String workId;

    public WorkPK() {
    }

    public WorkPK(String fromId, String workId) {
        this.fromId = fromId;
        this.workId = workId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fromId != null ? fromId.hashCode() : 0);
        hash += (workId != null ? workId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkPK)) {
            return false;
        }
        WorkPK other = (WorkPK) object;
        if ((this.fromId == null && other.fromId != null) || (this.fromId != null && !this.fromId.equals(other.fromId))) {
            return false;
        }
        return (this.workId != null || other.workId == null) && (this.workId == null || this.workId.equals(other.workId));
    }

    @Override
    public String toString() {
        return "WorkPK{" + "fromId=" + fromId + ", workId=" + workId + '}';
    }

}
