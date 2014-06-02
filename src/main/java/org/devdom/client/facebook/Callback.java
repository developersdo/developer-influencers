package org.devdom.client.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.devdom.tracker.Worker;
import org.devdom.tracker.bean.FacebookController;
import org.devdom.tracker.model.dto.FacebookMember;
import org.devdom.tracker.model.dto.FacebookProfile;
import org.devdom.tracker.util.Utils;


/**
 *
 * @author Carlos Vasquez Polanco
 */
public class Callback extends HttpServlet{
    
    private static final long serialVersionUID = 6305643034487441839L;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        String oauthCode = request.getParameter("code");
        try {
            facebook.getOAuthAccessToken(oauthCode);
            setProfile(request,facebook);
        } catch (FacebookException e){
            request.getSession().invalidate();
            response.sendRedirect("index.xhtml");
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
    
    private void setProfile(HttpServletRequest request, Facebook facebook){
        FacebookProfile profile = new FacebookProfile();
        try {
            String query = "SELECT uid, first_name, last_name, name, birthday_date, "
                    + "email, pic_big, sex FROM user WHERE uid = me() ";
            JSONArray jsonArray = facebook.executeFQL(query);
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    profile.setUid(jsonObject.getString("uid"));
                    profile.setFirstName(jsonObject.getString("first_name"));
                    profile.setLastName(jsonObject.getString("last_name"));
                    profile.setEmail(jsonObject.getString("email"));
                    profile.setBirthday(jsonObject.getString("birthday_date"));
                    profile.setPic_with_logo(jsonObject.getString("pic_big"));
                    profile.setSex(jsonObject.getString("sex"));
                    request.getSession().setAttribute("profile", profile);
                } catch (JSONException ex) {
                    Logger.getLogger(FacebookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                updateMember(profile);
            } catch (Exception ex) {
                Logger.getLogger(Callback.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*
            Runnable worker = new Worker();
            Thread thread = new Thread(worker);
            thread.setName("w");
            thread.start();
            */
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param profile 
     */
    private void updateMember(FacebookProfile profile) throws Exception{

        EntityManager em = emf.createEntityManager();
        try{ 
            em.getTransaction().begin();
            FacebookMember member = new FacebookMember();
            member.setUid(profile.getUid());
            member.setBirthdayDate(Utils.getDateFormatted(profile.getBirthday(),"MM/dd/yyyy"));
            member.setFirstName(profile.getFirstName());
            member.setLastName(profile.getLastName());
            member.setPic(profile.getPic_with_logo());
            em.merge(member);
            em.getTransaction().commit();
        }finally{
            if(em!=null|em.isOpen())
                em.close();
        }
    }
}