package org.devdom.tracker.util;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public interface FQL {
    String SELECT_USER_INFORMATION = 
            "select uid, first_name, last_name, birthday_date, education, education_history, email, pic_big, sex, work,"+
            " work_history, current_location from user where uid = :uid";
}


/**
final public class FQL {
    
    public static final String SELECT_USER_INFORMATION = 
            "select uid, first_name, last_name, birthday_date, education, education_history, email, pic_big, sex, work,"+
            " work_history, current_location from user where uid = ?";
 

}
*/