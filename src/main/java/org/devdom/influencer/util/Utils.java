package org.devdom.influencer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.devdom.influencer.model.dao.FacebookDao;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class Utils {
    
    /**
     * Formatear fecha y convertirla de String a Date
     * 
     * @param date
     * @return 
     */
    public static Date getDateFormatted(String date){
        
        return getDateFormatted(date, "yyyy-MM-dd'T'HH:mm:ss");
    }
    /**
     * 
     * @param date
     * @param format
     * @return 
     */
    public static Date getDateFormatted(String date, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
    
    public static Date getDateFormatted(Date date, String format){
        return getDateFormatted(date.toString(), format);
    }
}
