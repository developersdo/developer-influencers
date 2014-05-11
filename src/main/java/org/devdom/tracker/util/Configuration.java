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

    public static int POSTS_LIMIT = 200; //Determina la cantidad máxima de post que se retornarán
    public static int LIKES_LIMIT = 300; // Determina la cantidad máxima de likes que se quiere buscar
    public static int OFFSET = 200; 
    public static String JPA_PU = "jpa";
    public static String[] SEEK_GROUPS = {"161328360736390" /*Hackers and Founders - Santo Domingo*/,
                                          "264382946926439" /*#VivaPHP!*/,
                                          "358999187465748" /*CodigoLibre_Developers*/,
                                          "201514949865358" /*Developers Dominicanos*/,
                                          "132533423551389" /*developers X*/,
                                          "150647751783730" /*Javascript Dominica na*/,
                                          "455974804478621" /*Mobile Developer Group*/,
                                          "220361121324698" /*DevelopersRD*/,
                                          "179210165492903" /*Caribbean SQL*/, 
                                          "137759453068575"/*Python Dominicana*/};

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