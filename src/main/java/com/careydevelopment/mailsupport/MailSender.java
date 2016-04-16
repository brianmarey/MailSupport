package com.careydevelopment.mailsupport;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;

public class MailSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

	private static MailSender INSTANCE = null;

	private String smtpHost;	
	private MailAuthenticator authenticator;
	
	private MailSender() throws MailSenderException {
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.MAIL_PROPERTIES);
			
			smtpHost = props.getProperty("smtp.host");	
			String user = props.getProperty("smtp.user");
			String password = props.getProperty("smtp.password");
			
			authenticator = new MailAuthenticator(user,password);
		} catch (PropertiesFactoryException pe) {
			pe.printStackTrace();
			throw new MailSenderException ("Problem configuring mail system!");
		}
	}
	

	public static MailSender getInstance() throws MailSenderException {
		if (INSTANCE == null) {
			synchronized (MailSender.class) {
				if (INSTANCE == null) {
					INSTANCE = new MailSender();
				}
			}
		}
		
		return INSTANCE;
	}
	
	
	private Properties getMailProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "587");
		
		return props;
	}
	
	
	public void sendMail(String to, String from, String subject, String body) throws MessagingException, AddressException {	      
    	Properties props = getMailProperties();  
    	  
		Session session = Session.getInstance(props, authenticator);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);
		message.setText(body);

		Transport.send(message);

		LOGGER.info("Done sending mail to " + to);
	}	
	
	
	public static void main (String[] args) {
		String to = "careyb@mindspring.com";
		String from = "admin@careydevelopment.us";
		String subject = "A Brand New Test";
		String body = "Another test";
		
		//MailSender.getInstance().sendMail(to, from, subject, body);
	}
}
