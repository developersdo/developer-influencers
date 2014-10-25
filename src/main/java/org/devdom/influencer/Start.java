package org.devdom.influencer;

import facebook4j.internal.logging.Logger;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class Start implements javax.servlet.ServletContextListener{

    private final Runnable worker = new Worker();
    private final Thread thread = new Thread(worker);
    private static final Logger logger = Logger.getLogger(Worker.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Aplicación inicializada!");
        logger.info("Comenzará el proceso de recolección pasiva de datos");
        thread.setName("w");
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Deteniendo hilos activos");
        thread.stop();
    }

}
