package org.devdom.tracker.util;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public interface API {
    String FEEDS_BY_GRUOP_ID = ":group-id/feed?fields=id,message,message_tags,name,created_time,from,"
                             + "likes.limit(1000).fields(id),"
                             + "comments.limit(1000).fields(id,comment_count,message_tags,message,created_time,user_likes,"
                             + "from,like_count,comments,likes.limit(1000).fields(id,name,pic_crop,picture)),"
                             + "picture,with_tags&limit=20";
    
    String LIKES_IN_COMMENT = ":group-id_:post-id_:comment-id?fields=likes.limit(1000).fields(id),like_count,id,from,created_time";

    String MEMBERS_IN_GROUP = ":group-id/members?fields=first_name,last_name,id,"
            + "picture.type(square).height(80)&offset=5&limit=300";
    
    String WORK_INFORMATION_BY_WORK_ID = ":work-id?fields=id,category,name,picture";
    
}