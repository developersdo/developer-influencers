package org.devdom.client.facebook;

import org.devdom.tracker.util.Configuration;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class FBConnect {
    
    private static FacebookFactory facebook;
    private static final ConfigurationBuilder cb = Configuration.getFacebookConfig();
    
    public static void main(String[] args)
    {
        try {
            Facebook fb = connect();            
            Post post = connect().getPost("201514949865358_841622732521240");
            PagableList<Like> likes = post.getLikes();
            
            
            
            System.out.println("cantidad "+ likes.size() );
        } catch (FacebookException ex) {
            Logger.getLogger(FBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Facebook connect(){
        if(facebook==null) 
            facebook = new FacebookFactory(cb.build());
        return facebook.getInstance();
    }
}
