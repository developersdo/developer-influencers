package org.devdom.tracker.util;

import facebook4j.conf.ConfigurationBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Vásquez Polanco
 */

public class Configuration{

    private Configuration(){ }

    public static int POSTS_LIMIT = 4000; //Determina la cantidad máxima de post que se retornarán
    pulic static int LIKES_LIMIT = 300; // Determina la cantidad máxima de likes que se quiere buscar
    public static String JPA_PU = "jpa";
    public static String[] SEEK_GROUPS = {"201514949865358"};
    
    public static ConfigurationBuilder getFacebookConfig(){
       return new ConfigurationBuilder()
               .setDebugEnabled(true)
               .setOAuthPermissions("user_about_me,user_actions.music,user_actions.news,user_actions.video,user_activities,user_birthday,user_groups,user_hometown,user_interests,user_likes,user_location,user_notes,user_questions,user_relationship_details");

    }
    
    public static Map JPAConfig(){
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        return properties;
    }

}