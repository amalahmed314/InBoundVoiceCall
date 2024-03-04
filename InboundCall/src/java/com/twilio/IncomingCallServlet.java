package com.twilio;

import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amal ELF
 */
@SuppressWarnings("serial")
@WebServlet("/receive-call")
public class IncomingCallServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
            try {
                
                String message = DataBaseClass.getMessageFromDatabase();
                
                System.out.println("Received message from form: " + message);
                
                VoiceResponse twiml = new VoiceResponse.Builder()
                        .say(new Say.Builder(message)
                                .build())
                        .build();
                System.out.println("Generated TwiML: " + twiml.toXml());
                
                response.setContentType("application/xml");
                
                try {
                    
                    response.getWriter().print(twiml.toXml());
                } catch (TwiMLException e) {
                    
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                
                
            } catch (SQLException ex) {

                Logger.getLogger(IncomingCallServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(IncomingCallServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

    

}
