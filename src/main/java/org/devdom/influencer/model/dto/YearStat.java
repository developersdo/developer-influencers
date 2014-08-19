package org.devdom.influencer.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author Carlos Vásquez Polanco
 */
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name="YearStat.findMonthsStat", 
                                procedureName="findMonthsStat",
                                returnsResultSet=true,
                                resultClass=YearStat.class,
                                parameters={@StoredProcedureParameter(queryParameter="month",
                                                                      name="month",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class),
                                            @StoredProcedureParameter(queryParameter="year",
                                                                      name="year",
                                                                      direction=Direction.IN,
                                                                      type=Integer.class),
                                            @StoredProcedureParameter(queryParameter="group_id",
                                                                      name="group_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class),
                                            @StoredProcedureParameter(queryParameter="from_id",
                                                                      name="from_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class)}
                                ),
    @NamedStoredProcedureQuery( name="YearStat.findTopPositionsInTop", 
                                procedureName="findTopPositionsInTop",
                                returnsResultSet=true,
                                resultClass=YearStat.class,
                                parameters={@StoredProcedureParameter(queryParameter="from_id",
                                                                      name="from_id",
                                                                      direction=Direction.IN,
                                                                      type=String.class)}
                                )
})
@Entity
@XmlRootElement
public class YearStat implements Serializable {
    
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "from_id")    
    private String fromId;
    @Column(name = "current_rating")
    private String currentRating;
    @Column(name = "previous_rating")
    private String previousRating;
    @Column(name = "current_likes_count")
    private int currentLikesCount;
    @Column(name = "current_posts_likes_count")
    private int currentPostsLikesCount;
    @Column(name = "previous_posts_likes_count")
    private int previousPostsLikesCount;
    @Column(name = "current_comments_likes_count")
    private int currentCommentsLikesCount;
    @Column(name = "previous_comments_likes_count")
    private int previousCommentsLikesCount;
    @Column(name = "current_interactions_count")
    private int currentInteractionsCount;
    @Column(name = "previous_interactions_count")
    private int previousInteractionsCount;
    @Column(name = "current_posts_count")
    private int currentPostsCount;
    @Column(name = "previous_posts_count")
    private int previousPostsCount;
    @Column(name = "current_comments_count")
    private int current_comments_count;
    @Column(name = "previous_comments_count")
    private int previousCommentsCount;
    @Column(name = "current_mentions_count")
    private int currentMentionsCount;
    @Column(name = "previous_mentions_count")
    private int previousMentionsCount;
    @Column(name = "current_mentions_posts_count")
    private int currentMentionsPostsCount;
    @Column(name = "previous_mentions_posts_count")
    private int previousMentionsPostsCount;
    @Column(name = "current_mentions_comments_count")
    private int currentMentionsCommentsCount;
    @Column(name = "previous_mentions_comments_count")
    private int previousMentionsCommentsCount;
    @Column(name = "current_position")
    private int currentPosition;
    @Column(name = "previous_position")
    private int previousPosition;
    @Column(name = "created_time")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdTime;
    @Column(name = "rank_indicator")
    private String rankIndicator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(String currentRating) {
        this.currentRating = currentRating;
    }

    public String getPreviousRating() {
        return previousRating;
    }

    public void setPreviousRating(String previousRating) {
        this.previousRating = previousRating;
    }

    public int getCurrentLikesCount() {
        return currentLikesCount;
    }

    public void setCurrentLikesCount(int currentLikesCount) {
        this.currentLikesCount = currentLikesCount;
    }

    public int getCurrentPostsLikesCount() {
        return currentPostsLikesCount;
    }

    public void setCurrentPostsLikesCount(int currentPostsLikesCount) {
        this.currentPostsLikesCount = currentPostsLikesCount;
    }

    public int getPreviousPostsLikesCount() {
        return previousPostsLikesCount;
    }

    public void setPreviousPostsLikesCount(int previousPostsLikesCount) {
        this.previousPostsLikesCount = previousPostsLikesCount;
    }

    public int getCurrentCommentsLikesCount() {
        return currentCommentsLikesCount;
    }

    public void setCurrentCommentsLikesCount(int currentCommentsLikesCount) {
        this.currentCommentsLikesCount = currentCommentsLikesCount;
    }

    public int getPreviousCommentsLikesCount() {
        return previousCommentsLikesCount;
    }

    public void setPreviousCommentsLikesCount(int previousCommentsLikesCount) {
        this.previousCommentsLikesCount = previousCommentsLikesCount;
    }

    public int getCurrentInteractionsCount() {
        return currentInteractionsCount;
    }

    public void setCurrentInteractionsCount(int currentInteractionsCount) {
        this.currentInteractionsCount = currentInteractionsCount;
    }

    public int getPreviousInteractionsCount() {
        return previousInteractionsCount;
    }

    public void setPreviousInteractionsCount(int previousInteractionsCount) {
        this.previousInteractionsCount = previousInteractionsCount;
    }

    public int getCurrentPostsCount() {
        return currentPostsCount;
    }

    public void setCurrentPostsCount(int currentPostsCount) {
        this.currentPostsCount = currentPostsCount;
    }

    public int getPreviousPostsCount() {
        return previousPostsCount;
    }

    public void setPreviousPostsCount(int previousPostsCount) {
        this.previousPostsCount = previousPostsCount;
    }

    public int getCurrent_comments_count() {
        return current_comments_count;
    }

    public void setCurrent_comments_count(int current_comments_count) {
        this.current_comments_count = current_comments_count;
    }

    public int getPreviousCommentsCount() {
        return previousCommentsCount;
    }

    public void setPreviousCommentsCount(int previousCommentsCount) {
        this.previousCommentsCount = previousCommentsCount;
    }

    public int getCurrentMentionsCount() {
        return currentMentionsCount;
    }

    public void setCurrentMentionsCount(int currentMentionsCount) {
        this.currentMentionsCount = currentMentionsCount;
    }

    public int getPreviousMentionsCount() {
        return previousMentionsCount;
    }

    public void setPreviousMentionsCount(int previousMentionsCount) {
        this.previousMentionsCount = previousMentionsCount;
    }

    public int getCurrentMentionsPostsCount() {
        return currentMentionsPostsCount;
    }

    public void setCurrentMentionsPostsCount(int currentMentionsPostsCount) {
        this.currentMentionsPostsCount = currentMentionsPostsCount;
    }

    public int getPreviousMentionsPostsCount() {
        return previousMentionsPostsCount;
    }

    public void setPreviousMentionsPostsCount(int previousMentionsPostsCount) {
        this.previousMentionsPostsCount = previousMentionsPostsCount;
    }

    public int getCurrentMentionsCommentsCount() {
        return currentMentionsCommentsCount;
    }

    public void setCurrentMentionsCommentsCount(int currentMentionsCommentsCount) {
        this.currentMentionsCommentsCount = currentMentionsCommentsCount;
    }

    public int getPreviousMentionsCommentsCount() {
        return previousMentionsCommentsCount;
    }

    public void setPreviousMentionsCommentsCount(int previousMentionsCommentsCount) {
        this.previousMentionsCommentsCount = previousMentionsCommentsCount;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(int previousPosition) {
        this.previousPosition = previousPosition;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRankIndicator() {
        return rankIndicator;
    }

    public void setRankIndicator(String rankIndicator) {
        this.rankIndicator = rankIndicator;
    }

    @Override
    public String toString() {
        return "YearStat{" + "id=" + id + ", groupId=" + groupId + ", groupName=" + groupName + ", fromId=" + fromId + ", currentRating=" + currentRating + ", previousRating=" + previousRating + ", currentLikesCount=" + currentLikesCount + ", currentPostsLikesCount=" + currentPostsLikesCount + ", previousPostsLikesCount=" + previousPostsLikesCount + ", currentCommentsLikesCount=" + currentCommentsLikesCount + ", previousCommentsLikesCount=" + previousCommentsLikesCount + ", currentInteractionsCount=" + currentInteractionsCount + ", previousInteractionsCount=" + previousInteractionsCount + ", currentPostsCount=" + currentPostsCount + ", previousPostsCount=" + previousPostsCount + ", current_comments_count=" + current_comments_count + ", previousCommentsCount=" + previousCommentsCount + ", currentMentionsCount=" + currentMentionsCount + ", previousMentionsCount=" + previousMentionsCount + ", currentMentionsPostsCount=" + currentMentionsPostsCount + ", previousMentionsPostsCount=" + previousMentionsPostsCount + ", currentMentionsCommentsCount=" + currentMentionsCommentsCount + ", previousMentionsCommentsCount=" + previousMentionsCommentsCount + ", currentPosition=" + currentPosition + ", previousPosition=" + previousPosition + ", createdTime=" + createdTime + ", rankIndicator=" + rankIndicator + '}';
    }

}
