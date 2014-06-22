package org.devdom.tracker;

import javax.servlet.ServletContextEvent;

/**
 *
 * @author Carlos Vasquez Polanco
 */
public class Start implements javax.servlet.ServletContextListener{

    private final Runnable worker = new Worker();
    private final Thread thread = new Thread(worker);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        thread.setName("w");
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        thread.stop();
    }

}
