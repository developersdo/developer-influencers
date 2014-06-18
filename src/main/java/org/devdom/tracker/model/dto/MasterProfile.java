package org.devdom.tracker.model.dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Vasquez Polanco
 */
@Entity
@XmlRootElement
public class MasterProfile implements Serializable {
    @Id
    private String fromId;
    @OneToOne
    private FacebookProfile profile;
    @ManyToOne
    private List<Skill> skill;
    @ManyToOne
    private List<WorkHistory> work;
    @ManyToOne
    private List<EducationHistory> education;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public FacebookProfile getProfile() {
        return profile;
    }

    public void setProfile(FacebookProfile profile) {
        this.profile = profile;
    }

    public List<Skill> getSkill() {
        return skill;
    }

    public void setSkill(List<Skill> skill) {
        this.skill = skill;
    }

    public List<WorkHistory> getWork() {
        return work;
    }

    public void setWork(List<WorkHistory> work) {
        this.work = work;
    }

    public List<EducationHistory> getEducation() {
        return education;
    }

    public void setEducation(List<EducationHistory> education) {
        this.education = education;
    }
    
}
