package org.devdom.influencer.util;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public interface API {

    String FEEDS_BY_GRUOP_ID = ":group-id/feed?fields=id,message,message_tags,name,created_time,from,"
                             + "likes.limit(1000).fields(id),"
                             + "comments.limit(1000).fields(id,comment_count,message_tags,message,created_time,user_likes,"
                             + "from,like_count,comments,likes.limit(1000).fields(id,name,pic_crop,picture)),"
                             + "picture,with_tags&limit=100";
    
    String LIKES_IN_COMMENT = ":group-id_:post-id_:comment-id?fields=likes.limit(1000).fields(id),like_count,id,from,created_time";

    String MEMBERS_IN_GROUP = ":group-id/members?fields=first_name,last_name,id,administrator,"
            + "picture.type(square).height(80)&offset=5&limit=250";
    
    String WORK_INFORMATION_BY_WORK_ID = ":work-id?fields=id,category,name,picture";
    
    String PROFILE_INFORMATION = "SELECT uid, first_name, last_name, name, birthday_date, "
                    + " email, pic_big, sex, current_location "
                    + " FROM user WHERE uid = :profile-id ";
    
}