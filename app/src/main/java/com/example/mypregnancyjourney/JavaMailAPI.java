package com.example.mypregnancyjourney;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void,Void,Void>{
    private Session session;
    private String email;
    private String subject;
    private String message;

    private final String appEmail="accst8319.pregnancy@gmail.com"; // Email account used for this application
    private final String appEmailpwd = "ioemckpvhkhjawyl"; // app specific password for the email account

    public JavaMailAPI(String email, String subject,String message){
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(appEmail, appEmailpwd); // Replace with your email and app-specific password
            }
        });
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(appEmail)); // Replace with your email
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
            Log.w("JavaMailAPI","Email sent successfully to "+ email);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("JavaMailAPI", "Error while sending email", e);
        }

        return null;
    }
}
