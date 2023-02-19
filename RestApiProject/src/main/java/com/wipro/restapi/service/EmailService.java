package com.wipro.restapi.service;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmailService {
	static Logger log=LogManager.getLogger(EmailService.class.getName());
	
	public static void sendEmail(String msg,String sub,String to,String fname)
	{
		String host="smtp.gmail.com";  
		final String user="manikantajoy.tangudu@gmail.com"; 
		final String password="kupadfnhwdveiliz";		 		  
		Properties props = new Properties();  
		props.put("mail.smtp.host",host);  
		props.put("mail.smtp.port","587");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		   
		Session session = Session.getInstance(props,
		new javax.mail.Authenticator() 
		{  
		protected PasswordAuthentication getPasswordAuthentication() 
		{  
		return new PasswordAuthentication(user,password);  
		}  
		}
		);  
			  
		try 
		{  
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(user));  
		message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		message.setSubject(sub);  
		BodyPart mbp=new MimeBodyPart();
		mbp.setText(msg); 
		Multipart mp=new MimeMultipart();
		mp.addBodyPart(mbp);
		mbp=new MimeBodyPart();
		File f=new File("src/main/resources/static/invoices/"+fname);
		String filename=f.getAbsolutePath();
		DataSource source=new FileDataSource(filename);
		mbp.setDataHandler(new DataHandler(source));
		mbp.setFileName(f.getName());
		mp.addBodyPart(mbp);
		message.setContent(mp);
		Transport.send(message);  
		log.info("Order Invoice send Sucessfully");
		} 
		catch (MessagingException e) 
		{
		e.printStackTrace();
		}
	}
	public static void sendEmail(String msg,String sub,String to)
	{
		String host="smtp.gmail.com";  
		final String user="subhash12345696@gmail.com"; 
		final String password="jbygtcjyrrkdgpuv";		 		  
		Properties props = new Properties();  
		props.put("mail.smtp.host",host);  
		props.put("mail.smtp.port","587");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.auth", "true");  
		   
		Session session = Session.getInstance(props,
		new javax.mail.Authenticator() 
		{  
		@Override
		protected PasswordAuthentication getPasswordAuthentication() 
		{  
		return new PasswordAuthentication(user,password);  
		}  
		}
		);  
			  
		try 
		{  
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(user));  
		message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		message.setSubject(sub);  
		message.setText(msg);
				
		Transport.send(message);
		log.info("Status Update send to mail");
		} 
		catch (MessagingException e) 
		{
		e.printStackTrace();
		}
	}
}
