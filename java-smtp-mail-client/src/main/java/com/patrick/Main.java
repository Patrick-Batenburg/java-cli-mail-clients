package com.patrick;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Main
{
    private static String username = "example";  // GMail Account Name, Username, or Email address
    private static String password = "tmntizpecbapdzys"; // GMail password or app password
    private static String recipient = "example@gmail.com";
    private static String host = "smtp.gmail.com";

	public static void main(String[] args)
	{
        String from = username;
        String[] to = { recipient }; // list of recipient email addresses
        String subject = "Java send SMTP mail example";
        String body = "Welcome to JavaMail!";
        sendMessage(from, password, to, subject, body);
    }

	private static void sendMessage(String from, String password, String[] to, String subject, String body)
	{
        Properties properties = System.getProperties();
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);

		try
		{
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
			for(int i = 0; i < to.length; i++)
			{
                toAddress[i] = new InternetAddress(to[i]);
            }

			for(int i = 0; i < toAddress.length; i++)
			{
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
		catch (AddressException e)
		{
            e.printStackTrace();
        }
		catch (MessagingException e)
		{
            e.printStackTrace();
        }
    }
}