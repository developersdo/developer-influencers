package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos Vasquez
 */
@Embeddable
public class GroupAdminPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id")
    private short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "group_id")
    private String groupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "uid")
    private String uid;

    public GroupAdminPK() {
    }

    public GroupAdminPK(short id, String groupId, String uid) {
        this.id = id;
        this.groupId = groupId;
        this.uid = uid;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (groupId != null ? groupId.hashCode() : 0);
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupAdminPK)) {
            return false;
        }
        GroupAdminPK other = (GroupAdminPK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.groupId == null && other.groupId != null) || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.devdom.tracker.model.dto.GroupAdminPK[ id=" + id + ", groupId=" + groupId + ", uid=" + uid + " ]";
    }
    
}
