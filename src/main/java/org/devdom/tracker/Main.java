package org.devdom.tracker;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class Main {

    public static void main(String[] args) throws Exception{
        
        Runnable worker = new Worker();
        Thread thread = new Thread(worker);
        thread.setName("w");
        thread.start();
            
        /*
        String webappDirLocation = "src/main/webapp/";
        
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setShowStacks(true);
        
        ServletHandler servletHandler = new ServletHandler();
        //servletHandler.addServletWithMapping(org.devdom.skills.service.ErrorHandlerResource.class.getName(),"/");
        
        // The port that we should run on can be set into an environment variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8082";
        }
        
        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();

        ServletContextHandler sch = new ServletContextHandler(server, "");
        //sch.setSecurityHandler(getSecurityHandler());

        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);
        root.setDisplayName("Dev Dom - Influyentes");
        root.setHandler(servletHandler);
        root.setSecurityHandler(null);

        // Parent loader priority is a class loader setting that Jetty accepts.
        // By default Jetty will behave like most web containers in that it will
        // allow your application to replace non-server libraries that are part of the
        // container. Setting parent loader priority to true changes this behavior.
        // Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);
        //server.setHandler(server);
        server.addBean(errorHandler);
        //server.setHandler(servletHandler);

        server.setHandler(root);

        server.start();
        server.join();
        */
    }
}
