package org.devdom.influencer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.logging.Logger;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.client.facebook.Oauth;
import org.devdom.influencer.model.dao.GroupRatingDao;
import org.devdom.influencer.model.dto.Education;
import org.devdom.influencer.model.dto.EducationInstitution;
import org.devdom.influencer.model.dto.FacebookComment;
import org.devdom.influencer.model.dto.FacebookLikes;
import org.devdom.influencer.model.dto.FacebookMember;
import org.devdom.influencer.model.dto.FacebookMentions;
import org.devdom.influencer.model.dto.FacebookPost;
import org.devdom.influencer.model.dto.GroupAdmin;
import org.devdom.influencer.model.dto.GroupAdminPK;
import org.devdom.influencer.model.dto.GroupInformation;
import org.devdom.influencer.model.dto.Location;
import org.devdom.influencer.model.dto.Work;
import org.devdom.influencer.model.dto.WorkInstitution;
import org.devdom.influencer.model.dto.WorkPK;
import org.devdom.influencer.util.API;
import org.devdom.influencer.util.Configuration;
import org.devdom.influencer.util.FQL;
import org.devdom.influencer.util.Utils;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class Worker implements Runnable{

    private static final Logger logger = Logger.getLogger(Worker.class);
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    private final GroupRatingDao groupDao = new GroupRatingDao();
    private static final ConfigurationBuilder cb = Configuration.getFacebookConfig();
    private static facebook4j.conf.Configuration configuration; // = cb.build();
    private Facebook facebook;
    private AccessToken accessToken;
    private final int SEC = 1000;
    private final int MIN = SEC * 60;
    private final int TIME_TO_SEEK = MIN * 15;

    /**
     * 
     * @return
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }

    @Override
    public void run() {
        int idx = 0;
        
        try{
            final JSONObject JToken = Oauth.getNewJSONToken();
            String newToken = JToken.getString("access_token");

            logger.info("New Token > "+newToken);

            cb.setOAuthAccessToken(newToken);
            configuration = cb.build();
            facebook = new FacebookFactory(configuration).getInstance();
        }catch(JSONException ex){
            logger.error(ex.getMessage(),ex);
        }
        
        while(true){
            try {
                logger.info("revision -> "+ idx);
                seek(idx);
                Thread.sleep(TIME_TO_SEEK);
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage(),ex);
            }
            if(idx==100){
                idx = 0;
            }
            ++idx;
        }
    }
    
    public void seek(int idx){
        
        List<GroupInformation> groups = getGroupList();
        if(groups!=null){
            /* */
            if(idx==100){
                groups.stream().forEach((group) -> {
                    try{
                        logger.info("Buscando miembros el grupo "+ group.getGroupName());
                        getRawMembersInGroup(group); // Actualizar miembros en grupo
                    }catch(InterruptedException ex){
                        logger.error(ex.getMessage(),ex);
                    }catch (FacebookException | JSONException ex) {
                        logger.error(ex.getMessage(),ex);
                    }catch (Exception ex){
                        java.util.logging.Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            /* */
            groups.stream().forEach((group) -> {
                try{
                    Thread.sleep(100);
                    logger.info("Buscando post y comentarios del grupo "+group.getGroupName());
                    getRawPostsInGroup(group.getGroupId(), idx); // Actualizar interacciones de los miembros de los distintos grupos
                }catch(InterruptedException ex){
                    logger.error(ex.getMessage(),ex);
                } catch (FacebookException | JSONException ex) {
                    logger.error(ex.getMessage(),ex);
                } catch (Exception ex) {
                    logger.error(ex.getMessage(),ex);
                }
            });
            
            try {
                Thread.sleep(500);
                logger.info("Sincronizando data anual de los grupos");
                groupDao.updTablesInformationYear();
            } catch (Exception ex) {
                logger.error(ex.getMessage(),ex);
            }

            groups.stream().forEach((group) -> {
                try{
                    updGroupInformationByGroupById(group.getGroupId(),group.getGroupName(), group.getMinInteractions());
                }catch(Exception ex){
                    logger.error(ex.getMessage(), ex);
                }
            });
            
            groups.stream().forEach((group) -> {
                try{
                    logger.info("Intervalo 0 para grupo "+ group.getGroupName());
                    updateGroupsInformationWithInterval(group.getGroupId(),group.getGroupName(),group.getMinInteractions(),0);
                    logger.info("Intervalo 1 para grupo "+ group.getGroupName());
                    updateGroupsInformationWithInterval(group.getGroupId(),group.getGroupName(),group.getMinInteractions(),1);
                }catch(Exception ex){
                    logger.error(ex.getMessage(),ex);
                }
            });
            
            groups.stream().forEach((group) -> {
                try{
                    logger.info("Actualizando top diario para el grupo group "+group.getGroupName()+" dev_dom_user_dashboard_days_"+group.getGroupId()+"");
                    updateTopGroupInfluencersDay(group.getGroupId(), group.getGroupName(), group.getMinInteractions());
                }catch(Exception ex){
                    logger.error(ex.getMessage(),ex);
                }
            });
            
            groups.stream().forEach((group) -> {
                try{
                    logger.info("Actualizando información de grupos mensual "+ group.getGroupName());
                    updateGroupMonthStat(group.getGroupId(), group.getGroupName());
                }catch(Exception ex){
                    logger.error(ex.getMessage(),ex);
                }
            });
            
            logger.info("Finalizacion de actualizacion");
        }
    }
    
    /**
     * 
     * @return
     * @throws FacebookException
     * @throws JSONException 
     */
    private AccessToken getRawFacebookExchangeToken() throws FacebookException, JSONException{
        String url = "https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token";
        String appId = configuration.getOAuthAppId(); 
        String secret = configuration.getOAuthAppSecret(); 
        String oldToken = configuration.getOAuthAccessToken(); 

        url += "&client_id="+appId+
               "&client_secret="+secret+
               "&fb_exchange_token="+oldToken;

        logger.info("entro a generar el nuevo token con el URL "+ url);
        JSONObject json = getRawFacebookCall(url);

        return new AccessToken(json.getString("access_token"), json.getLong("expires"));
        
    }       
    
    private JSONObject getRawFacebookCall(String url){
        
        logger.info("URL -> "+ url);
        
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
            logger.error(ex.getMessage(),ex);
        }
        return null;
    }

    /**
     * 
     * Llamada al API de Facebook para retornar un JSON crudo para ser manipulado
     * con informacion referente a los post del grupo que se especifique segun su ID
     * 
     * @param groupId
     * @param idx
     * @throws FacebookException
     * @throws JSONException 
     */
    public void getRawPostsInGroup(String groupId, int idx) throws FacebookException, JSONException {
        logger.info("ENTRO------------------------->");
        EntityManager em = emf.createEntityManager();
        int countCommit = 0;
        String relURL = API.FEEDS_BY_GRUOP_ID.replace(":group-id", groupId);
        try{
            for(int p=0;p<=5;p++){
                RawAPIResponse response = facebook.callGetAPI(relURL);
                JSONObject json = response.asJSONObject();
                logger.info("cantidad !!!!!! "+json.length());
                JSONArray posts = json.getJSONArray("data");
                String nextPage = json.getJSONObject("paging").getString("next");

                int len = posts.length();
                int startLength = nextPage.indexOf(groupId);
                relURL = nextPage.substring(startLength,nextPage.length());

                for(int i=0;i<len;i++){
                    if(!em.getTransaction().isActive()){
                        em.getTransaction().begin();
                    }
                    JSONObject post = posts.getJSONObject(i);
                    syncRawPost(groupId,post,em);

                    logger.info("("+idx+") POST GROUP ID  => "+ groupId);
                    logger.info("("+idx+") POST ID  => "+ post.getString("id"));
                    logger.info("("+idx+") PAGE -> "+p+" + row -> "+i);
                    logger.info("("+idx+") Posts ===> "+len);

                    if(countCommit>=1000){
                        logger.info("guardando informacion...");
                        em.getTransaction().commit();
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex1) {
                            logger.error(ex1.getMessage(), ex1);
                        }
                        countCommit=-1;
                    }
                    countCommit++;
                }
                if(!em.getTransaction().isActive()){
                    em.getTransaction().begin();
                }
                em.getTransaction().commit();
            }
        }catch(FacebookException | JSONException ex){
            logger.error(ex.getMessage(), ex);
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.getTransaction().commit();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex1) {
                logger.error(ex1.getMessage(), ex1);
            }
        }
        finally{
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.getTransaction().commit();
            em.close();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex1) {
                logger.error(ex1.getMessage(), ex1);
            }
        }
    }

    /**
     * Sincronizar la informacion contenida en un post
     * 
     * @param post
     * @param em
     * @throws JSONException
     * @throws FacebookException 
     */
    private void syncRawPost(String groupId, JSONObject post, EntityManager em) throws JSONException, FacebookException{

        JSONObject json = post;
        String postId = json.getString("id").split("_")[1];
        String message = json.isNull("message")?"":json.getString("message");
        Date createdTime = Utils.getDateFormatted(json.getString("created_time"));
        String fromId = json.getJSONObject("from").getString("id");
        int likesCount = (!json.isNull("likes"))                        
                ?json.getJSONObject("likes").getJSONArray("data").length()                        
                :0; //revisar si existen likes en el post
        boolean hasMessages = !(json.isNull("comments")); // verificar si existen comentarios en el post
        boolean hasMentions = !(json.isNull("message_tags"));
        
        try{
            FacebookPost newPost = new FacebookPost();
            newPost.setPostId(postId);
            newPost.setFromId(fromId);
            newPost.setCreationDate(createdTime);
            newPost.setLikeCount(likesCount);
            newPost.setMessage(message);
            newPost.setGroupId(groupId);
            em.merge(newPost); // crear o actualizar un post existente 
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
        }

        if(likesCount>0){
            JSONArray likes = json.getJSONObject("likes").getJSONArray("data");
            syncRawLikes(groupId, postId, fromId, "POST", likes,createdTime, em);
        }

        if(hasMessages)
            syncRawMessages(groupId, postId, json.getJSONObject("comments").getJSONArray("data"), em);
        
        /*
        El API de facebook retorna un formato diferente para extraer los mentions 
        valiendose de dos arreglos
        */
        if(hasMentions){ 
            JSONArray tags = json.getJSONObject("message_tags").names();
            int len = tags.length();

            for(int i=0;i<len;i++){
                String tag = tags.getString(i);
                JSONArray mention = json.getJSONObject("message_tags").getJSONArray(tag);
                syncRawMentions(groupId, postId, fromId, "POST", mention, createdTime, em);
            }
        }
    }

    /**
     * 
     * Sincronizar mensajes que se contienen en un post
     * 
     * @param groupId
     * @param postId
     * @param comments
     * @param em
     * @throws JSONException 
     */
    private void syncRawMessages(String groupId, String postId, JSONArray comments, EntityManager em) throws JSONException, FacebookException {
        int len = comments.length();
        for(int i=0;i<len;i++){
            JSONObject message = comments.getJSONObject(i);
            boolean hasMentions = !(message.isNull("message_tags"));
            boolean hasFrom = !(message.isNull("from"));

            if(hasFrom){ // Revisar si el comentario tiene el id del creador
                FacebookComment newComment = new FacebookComment();
                Date createTime = Utils.getDateFormatted(message.getString("created_time"));
                int likesCount = Integer.parseInt(message.getString("like_count"));
                String fromId = message.getJSONObject("from").getString("id");
                String comment = message.isNull("message")?"":message.getString("message");
                String messageId = message.getString("id");

                try{
                    newComment.setCreateTime(createTime);
                    newComment.setLikeCount(likesCount);
                    newComment.setFromId(fromId);
                    newComment.setMessage(comment);
                    newComment.setMessageId(messageId);
                    newComment.setPostId(postId);
                    newComment.setGroupId(groupId);
                    em.merge(newComment);
                }catch(Exception ex){
                    logger.error(ex.getMessage(), ex);
                }

                if(likesCount>0){
                    syncRawMessageLikes(groupId, postId, messageId, em);
                }

                if(hasMentions){
                    JSONArray mentions = message.getJSONArray("message_tags");
                    syncRawMentions(groupId, messageId, fromId, "MESSAGE", mentions, createTime, em);
                }
            }
        }
    }
    
    private void syncRawLikes(String groupId, String objectId, String toId, String type, JSONArray likes, Date createdTime, EntityManager em) throws JSONException {
        int len = likes.length();
        for(int i=0;i<len;i++){
            FacebookLikes newLike = new FacebookLikes();
            JSONObject like = likes.getJSONObject(i);
            String fromId = like.getString("id");

            try{
                newLike.setCreatedTime(createdTime);
                newLike.setFromId(fromId);
                newLike.setGroupId(groupId);
                newLike.setObjectId(objectId);
                newLike.setToId(toId);
                newLike.setType(type);
                em.merge(newLike);
            }catch(Exception ex){
                logger.error(ex.getMessage(), ex);
            }
        }
    }
    
    /**
     * Sincronizar informacion acerca de los mentions que se realizan en un post
     * o comentario
     * 
     * @param groupId
     * @param objectId
     * @param fromId
     * @param type
     * @param mentions
     * @param em
     * @throws JSONException 
     */
    private void syncRawMentions(String groupId, String objectId, String fromId, String type, JSONArray mentions, Date createdTime, EntityManager em) throws JSONException{

        int len = mentions.length();
        for(int i=0;i<len;i++){
            FacebookMentions newMention = new FacebookMentions();
            JSONObject mention = mentions.getJSONObject(i);
            String toId = mention.getString("id");

            try{
                newMention.setFromId(fromId);
                newMention.setObjectId(objectId);
                newMention.setToId(toId);
                newMention.setType(type);
                newMention.setGroupId(groupId);
                newMention.setCreatedTime(createdTime);
                em.merge(newMention);
            }catch(Exception ex){
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 
     * Metodo para extraer informacion de los miembros de un grupo
     * @param group
     * @throws FacebookException
     * @throws JSONException 
     */
    private void getRawMembersInGroup(GroupInformation group) throws FacebookException, JSONException, Exception{
        EntityManager em = emf.createEntityManager();
        String relURL = API.MEMBERS_IN_GROUP.replace(":group-id", group.getGroupId());

        int counterCommit = 0;
        try{
            for(int p=0;p<=112;p++){
                RawAPIResponse response = facebook.callGetAPI(relURL);
                JSONObject json = response.asJSONObject();

                JSONArray members = json.getJSONArray("data");
                String nextPage = json.getJSONObject("paging").getString("next");

                int len = members.length();
                int startLength = nextPage.indexOf(group.getGroupId());
                relURL = nextPage.substring(startLength,nextPage.length());

                if(!em.getTransaction().isActive()){
                    em.getTransaction().begin();
                }
                for(int i=0;i<len;i++){
                    JSONObject member = members.getJSONObject(i);
                    logger.info("GROUP-> "+group.getGroupName());
                    logger.info("PAGE -> "+p+", member row -> "+i);

                    syncRawMember(group.getGroupId(),member,em);

                    if(counterCommit>=200){
                        if(!em.getTransaction().isActive()){
                            em.getTransaction().begin();
                        }
                        logger.info("guardando informacion...");
                        em.getTransaction().commit();
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex1) {
                            logger.error(ex1.getMessage(), ex1);
                        }
                        counterCommit = -1;
                    }
                    counterCommit++;
                }
                if(!em.getTransaction().isActive()){
                    em.getTransaction().begin();
                }

                logger.info("guardando informacion...");
                em.getTransaction().commit();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex1) {
                    logger.error(ex1.getMessage(), ex1);
                }
            }
        }finally{
            if(em.getTransaction().isActive()){
                logger.info("guardando informacion...");
                em.getTransaction().commit();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex1) {
                    logger.error(ex1.getMessage(), ex1);
                }
            }
            if(em.isOpen())
                em.close();
        }
    }

    /**
     * Actualizar información de miembros de grupos según el objeto JSON recibido
     * @param groupId
     * @param member
     * @param em
     * @throws JSONException
     * @throws FacebookException 
     */
    private void syncRawMember(String groupId, JSONObject member, EntityManager em) throws JSONException, FacebookException, Exception{

        String id = member.getString("id");
        String firstName;
        String lastName;
        String picture = member.getJSONObject("picture").getJSONObject("data").getString("url");
        String currentMemberLocation = "";
        String birthDay;
        boolean administrator = member.isNull("administrator")?false:member.getBoolean("administrator");

        JSONObject userInformation = getUserAutheticatedInformation(id);

        String email = userInformation.isNull("email")?"":userInformation.getString("email");
        String sex = userInformation.isNull("sex")?"":userInformation.getString("sex");
        firstName = userInformation.isNull("first_name")?"":userInformation.getString("first_name");
        lastName = userInformation.isNull("last_name")?"":userInformation.getString("last_name");
        id = userInformation.isNull("uid")?"":userInformation.getString("uid");
        birthDay = userInformation.isNull("birthday_date")?"":userInformation.getString("birthday_date");

        logger.info("administrator: "+administrator);
        
        //Listado de informacion educativa
        if(!userInformation.isNull("education")){
            JSONArray education = userInformation.getJSONArray("education");
            syncMemberEducationInformation(education,id,em);
        }
        
        //Listado de los trabajos anteriores y actual
        if(!userInformation.isNull("work")){
            JSONArray work = userInformation.getJSONArray("work");
            syncMemberWorksInformation(work,id,em);
        }
        
        // Manejar informacion de la ubicacion actual de un miembro
        if(!userInformation.isNull("current_location")){
            JSONObject currentLocation = userInformation.getJSONObject("current_location");
            currentMemberLocation = currentLocation.isNull("id")?"":currentLocation.getString("id");
            syncLocation(currentLocation,id,em);
        }
        
        
        if(administrator){
            syncGroupAdministrator(groupId,id, em);
        }
        
        try{
            FacebookMember profile = new FacebookMember();
            profile.setBirthdayDate(birthDay);
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            profile.setEmail(email);
            profile.setSex(sex);
            profile.setCurrentLocationId(currentMemberLocation);
            profile.setUid(id);
            em.merge(profile);
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
        }
    }
    
    /**
     * Buscar informacion de usuariosque han suscrito a la aplicación
     * 
     * @param uid
     * @return
     * @throws FacebookException 
     */
    private JSONObject getUserAutheticatedInformation(String uid) throws FacebookException, JSONException{

        return facebook.executeFQL( FQL.SELECT_USER_INFORMATION.replaceAll(":uid", uid) )
                .getJSONObject(0);
        
    }

    private void updateGroupsInformationWithInterval(String groupId, String groupName, int min, int interval) throws Exception{
        groupDao.updGroupInformationYearByIntervalAndId(groupId, min, interval);
    }

    private void updateTopGroupInfluencersDay(String groupId, String groupName, int min) throws Exception{
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int fromDay = dayOfYear-8;
        int year = calendar.get(Calendar.YEAR);
        
        if(fromDay<=0){
            fromDay = 365+fromDay;
            --year;
            for(;fromDay<=366;fromDay++){
                logger.info("day of year "+fromDay+" - "+year);
                groupDao.findDayActivityGroupById(fromDay, year, groupId, min);
            }
            fromDay=1;
            ++year;
        }
        
        for(;fromDay<=dayOfYear;fromDay++){
            logger.info("day of year "+fromDay+" - "+year);
            groupDao.findDayActivityGroupById(fromDay, year, groupId, min);
        }

    }

    private List<GroupInformation> getGroupList() {
        try{
            return groupDao.findAllGroups();
        }catch(Exception ex){
            logger.error(ex.getMessage(),ex);
        }
        return null;
    }

    private void updateGroupMonthStat(String groupId, String groupName) throws Exception {
        logger.info("Actualizando Top Mensual del grupo {0}", groupName);
        groupDao.updTopInfluencersMonthlyByGroupId(groupId);
    }

    private void syncMemberWorksInformation(JSONArray works, String uid, EntityManager em) throws JSONException, FacebookException {

        int len = works.length();
        for(int i=0;i<len;i++){
            try{
                JSONObject employer = works.getJSONObject(i).getJSONObject("employer");
                String id = employer.getString("id");
                String name = employer.getString("name");

                WorkPK newWork = new WorkPK();
                newWork.setFromId(uid);
                newWork.setWorkId(id);
                
                Work work = new Work();
                work.setWorkPK(newWork);
                work.setCreatedTime(new Date());

                em.merge(work);

                syncRawWorkInformation(id,em);
            }catch(JSONException | FacebookException ex){
                logger.error(ex.getMessage(),ex);
            }
        }
    }
    
    private void syncMemberEducationInformation(JSONArray educations, String uid, EntityManager em) throws JSONException, FacebookException {

        int len = educations.length();

        for(int i=0;i<len;i++){
            try{
                JSONObject education = educations.getJSONObject(i);
                JSONObject school = education.getJSONObject("school");
                String type = education.isNull("type")?"":education.getString("type");
                String institutionID = school.isNull("id")?"":school.getString("id");
                if("College".equals(type)){

                    Education newEducation = new Education();
                    newEducation.setFromId(uid);
                    newEducation.setInstitutionId(institutionID);
                    newEducation.setType(type);
                    syncRawInstitutionInformation(institutionID, em);
                    em.merge(newEducation);
                    
                }
            }catch(JSONException | FacebookException ex){
                logger.error(ex.getMessage(),ex);
            }
            
        }
    }

    private void syncRawWorkInformation(String work, EntityManager em) throws FacebookException, JSONException {
       
        String url = API.WORK_INFORMATION_BY_WORK_ID.replace(":work-id", work);
        RawAPIResponse response = facebook.callGetAPI(url);
        JSONObject json = response.asJSONObject();
        
        String category = json.getString("category");
        String id = json.getString("id");
        String name = json.getString("name");
        try{
            WorkInstitution newWork = new WorkInstitution();
            newWork.setCategory(category);
            newWork.setId(id);
            newWork.setName(name);
            em.merge(newWork);
        }catch(Exception ex){
            logger.error(ex.getMessage(),ex);
        }
    }
    
    public static void syncLocation(JSONObject location, String id, EntityManager em) throws JSONException {
        String country  = location.isNull("country")?"":location.getString("country");
        String city = location.isNull("city")?"":location.getString("city");
        String latitude = location.isNull("latitude")?"":location.getString("latitude");
        String longitude = location.isNull("longitude")?"":location.getString("longitude");
        String name = location.isNull("name")?"":location.getString("name");
        String state = location.isNull("state")?"":location.getString("state");
        String locationId = location.isNull("id")?"":location.getString("id");

        try{
            if(!"".equals(locationId)){
                Location newLocation = new Location();
                newLocation.setCity(city);
                newLocation.setCountry(country);
                newLocation.setId(locationId);
                newLocation.setLatitude(latitude);
                newLocation.setState(state);
                newLocation.setLongitude(longitude);
                newLocation.setName(name);
                em.merge(newLocation);
            }
        }catch(Exception ex){
            logger.error(ex.getMessage(),ex);
        }
    }

    private void syncGroupAdministrator(String groupId, String id, EntityManager em) {
        try{
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            if(groupId!=null && id!=null){
                logger.info("GROUP "+groupId);
                logger.info("ID "+id);
                GroupAdminPK newAdmin = new GroupAdminPK();
                newAdmin.setGroupId(groupId);
                newAdmin.setUid(id);
                logger.info(newAdmin.toString());;
                GroupAdmin admin = new GroupAdmin(newAdmin);
                logger.info(admin.toString());
                em.merge(admin);
                em.getTransaction().commit();
                Thread.sleep(30000);
            }
        }catch(Exception ex){
            logger.error(ex.getMessage(),ex);
        }
    }

    private void syncRawInstitutionInformation(String institutionId, EntityManager em) throws FacebookException, JSONException {

        RawAPIResponse response = facebook.callGetAPI(institutionId);
        JSONObject json = response.asJSONObject();
        String id = json.getString("id");
        String name = json.getString("name");
        String about = json.isNull("about")?"":json.getString("about");
        String category = json.isNull("category")?"":json.getString("category");
        String website = json.isNull("website")?"":json.getString("website");
        String latitude = "";
        String longitude = "";
        String city = "";
        String country = "";
        String street = "";

        category = category.toUpperCase();
        try{
            if(!json.isNull("location")){
                JSONObject location = json.getJSONObject("location");
                latitude = location.isNull("latitude")?"":location.getString("latitude");
                longitude = location.isNull("longitude")?"":location.getString("longitude");
                city = location.isNull("city")?"":location.getString("city");
                country = location.isNull("country")?"":location.getString("country");
                street = location.isNull("street")?"":location.getString("street");
            }
            if("COLLEGE,UNIVERSITY".contains(category)){
                EducationInstitution educationInstitution = new EducationInstitution();
                educationInstitution.setAbout(about);
                educationInstitution.setCategory(category);
                educationInstitution.setId(id);
                educationInstitution.setLatitude(latitude);
                educationInstitution.setLatitude(longitude);
                educationInstitution.setLocationCity(city);
                educationInstitution.setLocationCountry(country);
                educationInstitution.setLocationStreet(street);
                educationInstitution.setName(name);
                educationInstitution.setWebsite(website);
                em.merge(educationInstitution);
            }
        }catch(Exception ex){
            logger.error(ex.getMessage(),ex);
        }
    }
    
    private void syncRawMessageLikes(String groupId, String postId, String messageId, EntityManager em) throws FacebookException, JSONException {
        String url = API.LIKES_IN_COMMENT.replace(":group-id",groupId);
        url = url.replace(":post-id", postId);
        url = url.replace(":comment-id", messageId);

        RawAPIResponse response = facebook.callGetAPI(url);
        JSONObject json = response.asJSONObject();

        int likesCount = json.isNull("like_count")?0:json.getInt("like_count");
        if(likesCount>0){
            String toId = json.getJSONObject("from").getString("id");
            Date createdTime = Utils.getDateFormatted(json.getString("created_time"));

            if(!json.isNull("likes")){
                JSONArray likes = json.getJSONObject("likes").getJSONArray("data");
                int len = likes.length();

                for(int i=0;i<len;i++){
                    try{
                        String fromId = likes.getJSONObject(i).getString("id");
                        FacebookLikes newLike = new FacebookLikes();
                        newLike.setCreatedTime(createdTime);
                        newLike.setFromId(fromId);
                        newLike.setGroupId(groupId);
                        newLike.setObjectId(messageId);
                        newLike.setToId(toId);
                        newLike.setType("MESSAGE");
                        em.merge(newLike);
                    }catch(Exception ex){
                        logger.error(ex.getMessage(), ex);
                    }
                }
            }
        }
    }

    private void updGroupInformationByGroupById(String groupId, String groupName, int minInteractions) throws Exception {
        logger.info("Actualizando Rating general para el grupo "+ groupName);
        groupDao.updGroupInformationByGroupById(groupId, minInteractions);
    }

}
