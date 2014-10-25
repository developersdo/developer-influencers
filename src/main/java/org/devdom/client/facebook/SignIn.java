package org.devdom.client.facebook;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.logging.Logger;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.devdom.influencer.util.Configuration;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class SignIn extends HttpServlet{

    private static final long serialVersionUID = -7453606094644144082L;
    private Facebook facebook;
    private static final ConfigurationBuilder cb = Configuration.getFacebookConfig();
    private static facebook4j.conf.Configuration configuration = cb.build();
    private static final Logger logger = Logger.getLogger(Callback.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            facebook = new FacebookFactory(configuration).getInstance();
            request.getSession().setAttribute("facebook", facebook);

            StringBuffer callbackURL = request.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/callback");
            response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
        }catch(IOException ex){
            logger.error(ex.getMessage(),ex);
        }
    }
    
    /**
     * 
     * @return
     * @throws FacebookException
     * @throws JSONException 
     */
    private AccessToken getRawFacebookExchangeToken() throws FacebookException, JSONException{
        
        String appId = configuration.getOAuthAppId();
        String secret = configuration.getOAuthAppSecret();
        String oldToken = configuration.getOAuthAccessToken();
        
        String url = "https://graph.facebook.com/oauth/access_token?client_id="+appId+"&client_secret="+secret+"&grant_type=fb_exchange_token&fb_exchange_token="+oldToken;

        logger.info("entro a generar el nuevo token con el URL "+ url);
        JSONObject json = getRawFacebookCall(url);

        return new AccessToken(json.getString("access_token"), json.getLong("expires"));
        
    }
    
    private JSONObject getRawFacebookCall(String url){

        try {
            Client client = Client.create();
            WebResource webResource = client
                .resource(url);
 
            ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);
 
            if (response.getStatus() != 200) {
                    throw new RuntimeException("HTTP error code : " + response.getStatus());
            }
 
            String output = response.getEntity(String.class); 
            output = "{"+output+"}";
            output = output.replace("&expires=", ",expires=");
            return new JSONObject(output);

        } catch (RuntimeException | JSONException ex) { 
            ex.printStackTrace();
        }
        return null;
    }
}
