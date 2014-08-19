package org.devdom.influencer.util;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public interface FQL {
    String SELECT_USER_INFORMATION = 
            "select uid, first_name, last_name, birthday_date, education, education_history, email, pic_big, sex, work,"+
            " work_history, current_location from user where uid = :uid";
}
