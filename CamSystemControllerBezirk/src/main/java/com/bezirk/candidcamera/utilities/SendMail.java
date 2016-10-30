package com.bezirk.candidcamera.utilities;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class SendMail
{
    public static void main(String [] args) {
        sendMail(); //For prototyping support: Running main() sends a test message
    }
    public static void sendMail() { //TODO: Parameterize
        //Prototyping default: You can check this mail at
        //https://www.mailinator.com/inbox2.jsp?to=cameramail42#/#public_maildirdiv
        //without any spam filters. Change the user ID if someone else is testing at the same time.
        String to = "CameraMail42@mailinator.com";//change accordingly
        String from = "camera@bezierk.example.com";//change accordingly
        String host = "localhost";//or IP address

        System.out.println("Sending mail notification to "+to);
        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        //compose the message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("New Recording");
            message.setText("Your security camera just put a new recording in your cloud storage.");

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");

        }catch (MessagingException mex) {mex.printStackTrace();}
    }
}
