package com.eksamen.eksamen.Handler;

import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHandler {
  private final String username = "developerteam1234@gmail.com";
  private final String password = "Test1379";
  private Session session;

  private static EmailHandler ourInstance = new EmailHandler();
  public static EmailHandler getInstance() {
    return ourInstance;
  }


  private EmailHandler() {


    Properties props = new Properties();
    // Mail config
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.sfmtp.port", "465");
    // SSL config
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class",
      "javax.net.ssl.SSLSocketFactory");

    session = Session.getInstance(props,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      });
  }

  public void createMessage(String to, String subject, String text) {
    try {

      MimeMessage message = new MimeMessage(session);
      //message.setFrom(new InternetAddress(from));
      message.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse(to));
      message.setSubject(subject);
      message.setText(text, "UTF-8", "HTML");

      Transport.send(message);

      System.out.println("Done");

    } catch (MessagingException e) {
       throw new RuntimeException(e);
    }
  }
}

