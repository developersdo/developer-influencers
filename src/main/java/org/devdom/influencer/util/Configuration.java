package org.devdom.influencer.util;

import facebook4j.conf.ConfigurationBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Vásquez Polanco
 */

public interface Configuration{ 
    
    int POSTS_LIMIT = 200; //Determina la cantidad máxima de post que se retornarán
    int LIKES_LIMIT = 300; // Determina la cantidad máxima de likes que se quiere buscar
    int OFFSET = 200; 
    String JPA_PU = "jpa";
    String[] SEEK_GROUPS = { 
                                          "150647751783730" /*Javascript Dominicana*/,
                                          "455974804478621" /*Mobile Developer Group*/,
                                          "220361121324698" /*DevelopersRD*/,
                                          "179210165492903" /*Caribbean SQL*/,
                                          "137759453068575" /*Python Dominicana*/,
                                          "161328360736390" /*Hackers and Founders - Santo Domingo*/,                                          
                                          "358999187465748" /*CodigoLibre_Developers*/,
                                          "132533423551389" /*developers X*/,
                                          "634620033215438" /*C#.do*/,
                                          "264382946926439" /*#VivaPHP!*/,
                                          "201514949865358" /*Developers Dominicanos*/,
    };

    static ConfigurationBuilder getFacebookConfig(){
       return new ConfigurationBuilder()
               .setDebugEnabled(true)
               .setOAuthAppId("118535914888096")
               .setOAuthAppSecret("9a52f6713237cfcbb59fef66c750b46a")
                   .setOAuthAccessToken("CAAGA8ZCvdzxUBAGILu6pJG4sLcvCcX8uZCFOI64BQh6RGeEm3ojNx0PpcIRv5Dbf5UAdGlzZBLXtLL0lZCnll35inKD4gWLoJkBX0GptEzpiCFVOAhJa9enYqJcZCIGb8V7JH0afJk6NUggyRbXbfTENAqi7FbddTaiJux2IEZBgGR1eRqISJewicySsFy7IvVNrMrYHF3lAZDZD")
               .setOAuthPermissions("email,user_likes,user_location,user_birthday,user_hometown,user_questions,user_groups,user_work_history,user_education_history,friends_work_history,email");
    }
    
    static Map JPAConfig(){
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://ec2-50-19-213-136.compute-1.amazonaws.com:3306/devs?zeroDateTimeBehavior=convertToNull");
        properties.put("javax.persistence.jdbc.password", "w311d0n3");
        properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        properties.put("javax.persistence.jdbc.user", "devuser");
        return properties;
    }
    
    

}