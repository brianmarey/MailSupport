package com.careydevelopment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);
	
	private static final String MAIL_PROPERTIES = "/etc/tomcat8/resources/mail.properties";
	
	private String smtpHost;
	private String user;
	private String password;
	
	private MailSender() {
		
	}
	
	private static MailSender INSTANCE = null;


	public static MailSender getInstance() {
		if (INSTANCE == null) {
			synchronized (MailSender.class) {
				if (INSTANCE == null) {
					INSTANCE = new MailSender();
				}
			}
		}
		
		return INSTANCE;
	}
	
	public static void main (String[] args) {
		
	}
	
	
}
