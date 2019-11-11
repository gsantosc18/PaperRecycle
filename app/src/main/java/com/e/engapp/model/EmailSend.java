package com.e.engapp.model;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSend extends AsyncTask<Void,Void,Void> {

    private Session session;

    private String email, subject, message;

    public EmailSend(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public Void doInBackground(Void... params) {
        Properties props = new Properties();
        props.put("mail.smtp.host", EmailConfig.HOST);
        props.put("mail.smtp.socketFactory.port", EmailConfig.PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", EmailConfig.PORT);

        session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                //Authenticating the password
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EmailConfig.USER, EmailConfig.PASS);
                }
            });
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(EmailConfig.USER));
            //Adding receiver
            mm.addRecipient( Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);

            //Sending email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
