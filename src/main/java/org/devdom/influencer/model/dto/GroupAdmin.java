package org.devdom.influencer.model.dto;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vasquez
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "fb_group_admins")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupAdmin.findAll", query = "SELECT g FROM GroupAdmin g"),
    @NamedQuery(name = "GroupAdmin.findByGroupId", query = "SELECT g FROM GroupAdmin g WHERE g.groupAdminsPK.groupId = :groupId"),
    @NamedQuery(name = "GroupAdmin.findByUid", query = "SELECT g FROM GroupAdmin g WHERE g.groupAdminsPK.uid = :uid")})
public class GroupAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupAdminPK groupAdminsPK;

    public GroupAdmin() {
    }

    public GroupAdmin(GroupAdminPK groupAdminsPK) {
        this.groupAdminsPK = groupAdminsPK;
    }

    public GroupAdmin(String groupId, String uid) {
        this.groupAdminsPK = new GroupAdminPK(groupId, uid);
    }

    public GroupAdminPK getGroupAdminsPK() {
        return groupAdminsPK;
    }

    public void setGroupAdminsPK(GroupAdminPK groupAdminsPK) {
        this.groupAdminsPK = groupAdminsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupAdminsPK != null ? groupAdminsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupAdmin)) {
            return false;
        }
        GroupAdmin other = (GroupAdmin) object;
        if ((this.groupAdminsPK == null && other.groupAdminsPK != null) || (this.groupAdminsPK != null && !this.groupAdminsPK.equals(other.groupAdminsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.devdom.tracker.model.dto.GroupAdmin[ groupAdminsPK=" + groupAdminsPK + " ]";
    }
    
}
