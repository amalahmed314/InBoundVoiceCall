
package com.twilio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amal ELF
 */
public class StoreMessage extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message = request.getParameter("message");

        DataBaseClass.saveMessageToDatabase(message);

        response.sendRedirect("inboundCall.html");
    }
}
