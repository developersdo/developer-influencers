package org.devdom.client.facebook;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.logging.Logger;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import org.devdom.influencer.util.Configuration;

/**
 *
 * @author Carlos Vasquez
 */
public class Oauth {

    private static final Logger logger = Logger.getLogger(Oauth.class);
    
    /**
     * 
     * @param accessType
     * @return 
     */
    public static String getNewToken(String accessType){
        logger.info("creando nuevo token");
        ConfigurationBuilder cb = Configuration.getFacebookConfig();
        facebook4j.conf.Configuration config = cb.build();
        String oathAppId = config.getOAuthAppId();
        String oathAppSecret = config.getOAuthAppSecret();
        String oauthUrl = config.getOAuthAccessTokenURL();
        String oldToken = config.getOAuthAccessToken();

        oauthUrl += "?grant_type=fb_exchange_token";
        oauthUrl += "&client_id="+oathAppId;
        oauthUrl += "&client_secret="+oathAppSecret;
        oauthUrl += "&fb_exchange_token="+oldToken;

        logger.info("oathAppId:"+oathAppId);
        logger.info("oathAppSecret:"+oathAppSecret);
        logger.info("token URL:"+ oauthUrl);

        try {
            return getRawFacebookCall(oauthUrl, accessType);
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return "";
        }
    }
    
    /**
     * 
     * @return
     * @throws JSONException 
     */
    public static JSONObject getNewJSONToken() throws JSONException{
        String newToken = getNewToken(AcceptType.APPLICATION_JSON);
        newToken = "{"+newToken+"}";
        newToken = newToken.replace("&expires=",",expires=");
        logger.info(newToken);
        return new JSONObject(newToken);
    }
    
    /**
     * 
     * @param url
     * @return 
     */
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
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
    
    /**
     * 
     * @param url
     * @param accept
     * @return
     * @throws Exception 
     */
    private static String getRawFacebookCall(String url, String accept) throws Exception{

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.accept(accept).get(ClientResponse.class);

        if (response.getStatus() != 200){
            throw new RuntimeException("HTTP error code : " + response.getStatus());
        }
        return response.getEntity(String.class); 

    }

    private interface AcceptType{
        String TEXT_PLAIN = "text/plain";
        String TEXT_HTML = "text/html";
        String IMAGE_JPG = "image/jpg";
        String APPLICATION_JSON = "application/json";
        String APPLICATION_PJSON = "application/pjson";
        String TEXT_JSON = "text/json";
    }

}
