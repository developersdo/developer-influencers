package org.devdom.tracker.model.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@Entity
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name="Influencers.findTop10DevsInfluents", 
                                procedureName="findTop10DevsInfluents",
                                returnsResultSet=true,
                                resultClass=Influencer.class),
    @NamedStoredProcedureQuery( name="Influencers.findTop20DevsInfluents",
                                procedureName="findTop20DevsInfluents",
                                returnsResultSet=true,
                                resultClass=Influencer.class,
                                parameters={@StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class)}
                                )
})
public class Influencer  implements Serializable {    
    
    @Id
    @Column(name = "position")
    private int position;
    
    @Column(name = "from_id")
    private String fromId;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "likes_count")
    private int likesCount;
    
    @Column(name = "comments_count")
    private int messagesCount;
    
    @Column(name = "mentions_count")
    private int mentionsCount;
    
    @Column(name = "posts_count")
    private int postsCount;
    
    @Column(name = "ratio")
    private double ratio;
    
    @Column(name = "picture")
    private String picture;
    
    @Column(name = "group_id")
    private String groupId;
    
    @Column(name = "group_name")
    private String groupName;

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return the fromId
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * @param fromId the fromId to set
     */
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the likesCount
     */
    public int getLikesCount() {
        return likesCount;
    }

    /**
     * @param likesCount the likesCount to set
     */
    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    /**
     * @return the messagesCount
     */
    public int getMessagesCount() {
        return messagesCount;
    }

    /**
     * @param messagesCount the messagesCount to set
     */
    public void setMessagesCount(int messagesCount) {
        this.messagesCount = messagesCount;
    }

    /**
     * @return the postsCount
     */
    public int getPostsCount() {
        return postsCount;
    }

    /**
     * @param postsCount the postsCount to set
     */
    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    /**
     * @return the ratio
     */
    public double getRatio() {
        return ratio;
    }

    /**
     * @param ratio the ratio to set
     */
    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    /**
     * @return the picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the mentionsCount
     */
    public int getMentionsCount() {
        return mentionsCount;
    }

    /**
     * @param mentionsCount the mentionsCount to set
     */
    public void setMentionsCount(int mentionsCount) {
        this.mentionsCount = mentionsCount;
    }

}
