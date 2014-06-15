package org.devdom.tracker;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.RawAPIResponse;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.logging.Logger;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.devdom.tracker.model.dao.GroupRatingDao;
import org.devdom.tracker.model.dto.Education;
import org.devdom.tracker.model.dto.EducationInstitution;
import org.devdom.tracker.model.dto.FacebookComment;
import org.devdom.tracker.model.dto.FacebookMember;
import org.devdom.tracker.model.dto.FacebookMentions;
import org.devdom.tracker.model.dto.FacebookPost;
import org.devdom.tracker.model.dto.GroupInformation;
import org.devdom.tracker.model.dto.Location;
import org.devdom.tracker.model.dto.Work;
import org.devdom.tracker.model.dto.WorkInstitution;
import org.devdom.tracker.model.dto.WorkPK;
import org.devdom.tracker.util.Configuration;
import org.devdom.tracker.util.FQL;
import org.devdom.tracker.util.Utils;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class Worker implements Runnable{

    private static final Logger logger = Logger.getLogger(Worker.class);
    //private static final Logger LOGGER = Logger.getLogger(Worker.class .getName());
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    private final GroupRatingDao groupDao = new GroupRatingDao();
    private static final ConfigurationBuilder cb = Configuration.getFacebookConfig();
    private Facebook facebook;

    /**
     * 
     * @return
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager(Configuration.JPAConfig());
    }

    @Override
    public void run() {
        facebook = new FacebookFactory(cb.build()).getInstance();

        List<GroupInformation> groups = getGroupList();
        if(groups!=null){
            groups.stream().forEach((group) -> {
                try{
                    logger.info("Buscando miembros el grupo "+ group.getGroupName());
                    getRawMembersInGroup(group.getGroupId()); // Actualizar miembros en grupo
                    Thread.sleep(1000);
                    //LOGGER.log(Level.INFO, "Buscando post y comentarios del grupo {0}", group.getGroupName());
                    //getRawPostsInGroup(group.getGroupId()); // Actualizar interacciones de los miembros de los distintos grupos
                }catch(FacebookException | JSONException | InterruptedException ex){
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
                    logger.info("Intervalo 0 para grupo "+ group.getGroupName());
                    updateGroupsInformationWithInterval(group.getGroupId(),group.getGroupName(),group.getMinInteractions(),0);
                    logger.info("Intervalo 1 para grupo "+ group.getGroupName());
                    updateGroupsInformationWithInterval(group.getGroupId(),group.getGroupName(),group.getMinInteractions(),1);
                }catch(Exception ex){
                    logger.error(ex.getMessage(),ex);
                }
            });
            /*
            groups.stream().forEach((group) -> {
                try{
                    updateGroupMonthStat(group.getGroupId(), group.getGroupName());
                }catch(Exception ex){
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            */
            logger.info("Finalizacion de actualizacion");
        }
    }
    
    
    /**
     * 
     * Llamada al API de Facebook para retornar un JSON crudo para ser manipulado
     * con informacion referente a los post del grupo que se especifique segun su ID
     * 
     * @param groupId
     * @throws FacebookException
     * @throws JSONException 
     */
    public void getRawPostsInGroup(String groupId) throws FacebookException, JSONException {

        EntityManager em = emf.createEntityManager();
        int countCommit = 0;
        String relURL = groupId + "/feed?fields=id,message,message_tags,name,created_time,from,likes.limit(1000).fields(id),"
                + "comments.limit(1000).fields(id,comment_count,message_tags,message,created_time,user_likes,"
                + "from,like_count,comments,likes.limit(1000).fields(id,name,pic_crop,picture)),picture,with_tags&limit=50";
        for(int p=0;p<2;p++){
            RawAPIResponse response = facebook.callGetAPI(relURL);
            JSONObject json = response.asJSONObject();
            
            JSONArray posts = json.getJSONArray("data");
            String nextPage = json.getJSONObject("paging").getString("next");
            logger.info("(nextPage)===> "+ nextPage);
            
            int len = posts.length();
            int startLength = nextPage.indexOf(groupId);
            relURL = nextPage.substring(startLength,nextPage.length());
            logger.info("(nextPage)===> "+ relURL);
            
            for(int i=0;i<len;i++){
                if(!em.getTransaction().isActive()){
                    em.getTransaction().begin();
                }
                JSONObject post = posts.getJSONObject(i);
                syncRawPost(groupId,post,em);
                
                logger.info("POST GROUP ID  => "+ groupId);
                logger.info("POST ID  => "+ post.getString("id"));
                logger.info("PAGE -> "+p+" + row -> "+i);
                logger.info("Posts ===> "+len);
                
                if(countCommit>=99){
                    logger.info("guardando informacion...");
                    em.getTransaction().commit();
                    countCommit=-1;
                }
                countCommit++;
            }
            if(!em.getTransaction().isActive())
                em.getTransaction().begin();
            
            logger.info("NEXT===> {0}", relURL);
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

        JSONObject json = post; //response.asJSONObject();

        String postId = json.getString("id").split("_")[1];
        String message = json.isNull("message")?"":json.getString("message");
        Date createdTime = Utils.getDateFormatted(json.getString("created_time"));
        String fromId = json.getJSONObject("from").getString("id");
        int likes = (!json.isNull("likes"))                        
                ?json.getJSONObject("likes").getJSONArray("data").length()                        
                :0; //revisar si existen likes en el post
        boolean hasMessages = !(json.isNull("comments")); // verificar si existen comentarios en el post
        boolean hasMentions = !(json.isNull("message_tags"));
        
        FacebookPost newPost = new FacebookPost();
        newPost.setPostId(postId);
        newPost.setFromId(fromId);
        newPost.setCreationDate(createdTime);
        newPost.setLikeCount(likes);
        newPost.setMessage(message);
        newPost.setGroupId(groupId);
        em.merge(newPost); // crear o actualizar un post existente 

        logger.info("(POST) postId ", postId);
        logger.info("(POST) fromId ", fromId);
        logger.info("(POST) createdTime ", createdTime.toString());
        logger.info("(POST) likes ", String.valueOf(likes));
        logger.info("(POST) message ", message);
        logger.info("(POST) groupId ", groupId);

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
    private void syncRawMessages(String groupId, String postId, JSONArray comments, EntityManager em) throws JSONException {
        int len = comments.length();
        for(int i=0;i<len;i++){
            JSONObject message = comments.getJSONObject(i);
            boolean hasMentions = !(message.isNull("message_tags"));
            boolean hasFrom = !(message.isNull("from"));

            if(hasFrom){ // Revisar si el comentario tiene el id del creador 
                FacebookComment newComment = new FacebookComment();
                Date createTime = Utils.getDateFormatted(message.getString("created_time"));
                int likes = Integer.parseInt(message.getString("like_count"));
                String fromId = message.getJSONObject("from").getString("id");
                String comment = message.isNull("message")?"":message.getString("message");
                String messageId = message.getString("id");

                logger.info("(Message) createTime "+ createTime.toString());
                logger.info("(Message) likes "+ String.valueOf(likes));
                logger.info("(Message) fromId "+ fromId);
                logger.info("(Message) comment "+ comment);
                logger.info("(Message) messageId "+ messageId);
                logger.info("(Message) postId "+ postId);
                logger.info("(Message) groupId "+ groupId);

                newComment.setCreateTime(createTime);
                newComment.setLikeCount(likes);
                newComment.setFromId(fromId);
                newComment.setMessage(comment);
                newComment.setMessageId(messageId);
                newComment.setPostId(postId);
                newComment.setGroupId(groupId);
                em.merge(newComment);

                if(hasMentions){
                    JSONArray mentions = message.getJSONArray("message_tags");
                    syncRawMentions(groupId, messageId, fromId, "MESSAGE", mentions, createTime, em);
                }
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

            logger.info("(MENTION) "+type+" objectId "+ objectId);
            logger.info("(MENTION) "+type+" fromId "+fromId);
            logger.info("(MENTION) "+type+" toId "+toId);
            logger.info("(MENTION) "+type+" type "+type);
            logger.info("(MENTION) "+type+" group id "+groupId);

            newMention.setFromId(fromId);
            newMention.setObjectId(objectId);
            newMention.setToId(toId);
            newMention.setType(type);
            newMention.setGroupId(groupId);
            newMention.setCreatedTime(createdTime);
            em.merge(newMention);
        }
    }

    /**
     * 
     * Metodo para extraer informacion de los miembros de un grupo
     * @param groupId
     * @throws FacebookException
     * @throws JSONException 
     */
    private void getRawMembersInGroup(String groupId) throws FacebookException, JSONException, Exception{
        EntityManager em = emf.createEntityManager();
        String relURL = groupId + "/members?fields=first_name,last_name,id,picture.type(square).height(80)&offset=5&limit=300";
        int counterCommit = 0;
        try{
            for(int p=0;p<50;p++){
                RawAPIResponse response = facebook.callGetAPI(relURL);
                JSONObject json = response.asJSONObject();

                JSONArray members = json.getJSONArray("data");
                String nextPage = json.getJSONObject("paging").getString("next");
                logger.info("(nextPage)===> "+ nextPage);

                int len = members.length();
                int startLength = nextPage.indexOf(groupId);
                relURL = nextPage.substring(startLength,nextPage.length());
                logger.info("(nextPage)===> "+ relURL);

                if(!em.getTransaction().isActive()){
                    em.getTransaction().begin();
                }
                for(int i=0;i<len;i++){
                    JSONObject member = members.getJSONObject(i);
                    logger.info("PAGE -> "+p+", member row -> "+i);
                    syncRawMember(groupId,member,em);
                    logger.info("Members ===> "+ len);
                    if(counterCommit>=200){
                        if(!em.getTransaction().isActive())
                            em.getTransaction().begin();

                        logger.info("guardando informacion...");
                        em.getTransaction().commit();
                        
                        counterCommit = -1;
                    }
                    counterCommit++;
                }
                if(!em.getTransaction().isActive())
                    em.getTransaction().begin();

                logger.info("guardando informacion...");
                em.getTransaction().commit();
                logger.info("NEXT===>"+ relURL);
            }
        }finally{
            if(em.getTransaction().isActive()){
                logger.info("guardando informacion...");
                em.getTransaction().commit();
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
        String firstName = member.isNull("first_name")?"":member.getString("first_name");
        String lastName;
        String picture = member.getJSONObject("picture").getJSONObject("data").getString("url");
        String currentMemberLocation = "";
        String birthDay;

        JSONObject userInformation = getUserAutheticatedInformation(id);
        logger.info("Guardando informacion para usuario "+firstName);

        String email = userInformation.isNull("email")?"":userInformation.getString("email");
        String sex = userInformation.isNull("sex")?"":userInformation.getString("sex");
        firstName = userInformation.isNull("first_name")?"":userInformation.getString("first_name");
        lastName = userInformation.isNull("last_name")?"":userInformation.getString("last_name");
        id = userInformation.isNull("uid")?"":userInformation.getString("uid");
        birthDay = userInformation.isNull("birthday_date")?"":userInformation.getString("birthday_date");
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

        FacebookMember profile = new FacebookMember();
        profile.setBirthdayDate(birthDay);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setEmail(email);
        profile.setSex(sex);
        profile.setCurrentLocationId(currentMemberLocation);
        profile.setUid(id);
        em.merge(profile);

        logger.info("(MEMBER) Id -> "+ id);
        logger.info("(MEMBER) firstName -> "+ firstName);
        logger.info("(MEMBER) lastName -> "+ lastName);
        logger.info("(MEMBER) picture -> "+ picture);
        logger.info("(MEMBER) groupId -> "+ groupId);
    }
    
    /**
     * Buscar información de usuariosque han suscrito a la aplicación
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
                logger.info("guardando informacion de trabajo "+uid);
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
            }catch(Exception ex){
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
                    logger.info("Educacion (Institucion) UID "+uid);
                    logger.info("Educacion (Institucion) institutionID "+institutionID);
                    logger.info("Educacion (Institucion) type "+type);
                    Education newEducation = new Education();
                    newEducation.setFromId(uid);
                    newEducation.setInstitutionId(institutionID);
                    newEducation.setType(type);
                    syncRawInstitutionInformation(institutionID, em);
                    em.merge(newEducation);
                }
            }catch(Exception ex){
                logger.error(ex.getMessage(),ex);
            }
            
        }
    }

    private void syncRawWorkInformation(String work, EntityManager em) throws FacebookException, JSONException {
       
        String url = work + "?fields=id,category,name,picture";
        RawAPIResponse response = facebook.callGetAPI(url);
        JSONObject json = response.asJSONObject();
        
        String category = json.getString("category");
        String id = json.getString("id");
        String name = json.getString("name");
        String picture = json.getJSONObject("picture").getJSONObject("data").getString("url");
        
        WorkInstitution newWork = new WorkInstitution();
        newWork.setCategory(category);
        newWork.setId(id);
        newWork.setName(name);
        newWork.setPicture(picture);
        em.merge(newWork);
    }
    
    private void syncLocation(JSONObject location, String id, EntityManager em) throws JSONException {
        String country  = location.isNull("country")?"":location.getString("country");
        String city = location.isNull("city")?"":location.getString("city");
        String latitude = location.isNull("latitude")?"":location.getString("latitude");
        String longitude = location.isNull("longitude")?"":location.getString("longitude");
        String name = location.isNull("name")?"":location.getString("name");
        String state = location.isNull("state")?"":location.getString("state");
        String locationId = location.isNull("id")?"":location.getString("id");
        
        try{
            if(!"".equals(locationId)){
                logger.info("Guardando ubicacion : "+name);
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
            
            logger.info("About: "+ about);
            logger.info("category: "+ category);
            logger.info("id: "+ id);
            logger.info("Latitude: "+ latitude);
            logger.info("Longitude: "+ longitude);
            logger.info("City: "+ city);
            logger.info("Country: "+ country);
            logger.info("Street: "+ street);
            logger.info("Name: "+ name);
            logger.info("website:"+ website);

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

}
