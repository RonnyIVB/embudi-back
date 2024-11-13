package com.satgy.embudi.general;

/**
 * @author Ronny
 */

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


public class Email
{
    private static String SMTP_AUTH_USER;
    private static String SMTP_AUTH_PWD;

    private static final Logger log = LoggerFactory.getLogger(Email.class);

    @Value("${app.emailAddress}")
    private String emailAddress;
    
    @Value("${app.emailUser}")
    private String emailUser;

    @Value("${app.emailPassword}")
    private String emailPassword;

    @Value("${app.SMTPAddress}")
    private String smtpAddress;

    @Value("${app.SMTPPort}")
    private String smtpPort;

    @Value("${app.emailStartTLS}")
    private String startTLS;

    @Value("${app.emailAuthentication}")
    private String emailAuthentication;
    
    public void testCorreo(final String to) {
        Email.SMTP_AUTH_USER = emailUser;
        Email.SMTP_AUTH_PWD = emailPassword;

        final Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpAddress);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.starttls.enable", startTLS);
        properties.put("mail.smtp.starttls.required", startTLS);
        properties.put("mail.smtp.ssl.trust", smtpAddress);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.debug", "false");

        Session session;
        if (emailAuthentication.equals("true")) {
            properties.put("mail.smtp.auth", "true");
            final Authenticator autenticador = new SMTPAuthenticator();
            session = Session.getInstance(properties, autenticador);
        }
        else {
            session = Session.getInstance(properties, (Authenticator)null);
        }
        try {
            final MimeMessage message = new MimeMessage(session);
            message.setFrom((Address)new InternetAddress(emailAddress));
            //message.setRecipient(Message.RecipientType.TO, (Address)new InternetAddress(to));
            message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            message.setSubject("Electronicos Test Correo");
            message.setSentDate(new Date());
            final MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText("Cuerpo Mensaje Linea1\nCuerpo Mensaje Linea 2");
            final Multipart multipart = (Multipart)new MimeMultipart();
            multipart.addBodyPart((BodyPart)messagePart);
            message.setContent(multipart);
            Transport.send(message);
            //System.out.println("Correo().enviarCorreo: OK");
        }
        catch (java.lang.NullPointerException e) { /*ignored*/ }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public String sendEmail(final String to, final String asunto, final String mensaje) {
        Email.SMTP_AUTH_USER = emailUser;
        Email.SMTP_AUTH_PWD = emailPassword;

        final Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpAddress);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.starttls.enable", startTLS);
        properties.put("mail.smtp.ssl.trust", smtpAddress);
        properties.setProperty("mail.debug", "false");

        Session session;
        if (emailAuthentication.equals("true")) {
            properties.put("mail.smtp.auth", "true");
            final Authenticator autenticador = new SMTPAuthenticator();
            session = Session.getInstance(properties, autenticador);
        }
        else {
            session = Session.getInstance(properties, (Authenticator)null);
        }
        try {
            final MimeMessage message = new MimeMessage(session);
            message.setFrom((Address)new InternetAddress(emailAddress));
            //message.setRecipient(Message.RecipientType.TO, (Address)new InternetAddress(to));
            message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            message.setSubject(asunto);
            message.setSentDate(new Date());
            final MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(mensaje);
            final Multipart multipart = (Multipart)new MimeMultipart();
            multipart.addBodyPart((BodyPart)messagePart);
            Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
            message.setContent(multipart);

            String datos = "usuario: " + emailUser
                    + " | contraseña: " + emailPassword
                    + " | dirección: " + emailAddress
                    + " | to: " + to
                    + " | asunto: " + asunto
                    + " | mensaje: " + mensaje
                    + " | direcciónSMTP: " + smtpAddress
                    + " | port: " +  smtpPort
                    + " | startTLS: " + startTLS
                    ;
            System.out.println(datos);
            log.debug(datos);

            Transport.send(message);
            System.out.println("Correo().enviarCorreo: OK");


            return "0";
        }
        catch (java.lang.NullPointerException e) {
            e.printStackTrace();
            return (e.getMessage());
        }
        catch (MessagingException e) {
            e.printStackTrace();
            return (e.getMessage());
        }
    }


    private static class SMTPAuthenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            final String username = Email.SMTP_AUTH_USER;
            final String password = Email.SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
}

