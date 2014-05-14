package org.devdom.tracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.devdom.tracker.model.dao.FacebookDao;

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return formatter.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(FacebookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }

}
