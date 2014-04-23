package org.devdom.tracker.util;

import facebook4j.conf.ConfigurationBuilder;

/**
 *
 * @author Carlos Vásquez Polanco
 */

public class Configuration{

    private Configuration(){ }

    public static ConfigurationBuilder getFacebookConfig(){
       return new ConfigurationBuilder()
               .setDebugEnabled(true)
               .setOAuthPermissions("user_about_me,user_actions.music,user_actions.news,user_actions.video,user_activities,user_birthday,user_groups,user_hometown,user_interests,user_likes,user_location,user_notes,user_questions,user_relationship_details");

    }
    
}