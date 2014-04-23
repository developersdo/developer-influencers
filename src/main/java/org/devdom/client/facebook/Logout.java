package org.devdom.client.facebook;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class Logout extends HttpServlet{
    
    private static final long serialVersionUID = 5357658337449255998L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        request.getSession().invalidate();
        response.sendRedirect("index.xhtml");

    }
}
