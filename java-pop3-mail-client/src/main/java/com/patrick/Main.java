package com.patrick;

import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;


public final class Main
{
    private static String username = "example";  // GMail Account Name, Username, or Email address
    private static String password = "mhewynryhmfoykne"; // GMail password or app password
    private static String recipient = "example@gmail.com";
    private static String host ="pop.gmail.com";
    private static int port = 995;

	public static void main(String[] args)
	{
        String from = username;
        String[] to = { recipient }; // list of recipient email addresses
        String subject = "Java send POP mail example";
        String body = "Welcome to JavaMail!";
        printMessageInfo();     
    }

    private static void POP3Protocol()
    {
        POP3Session pop3 = new POP3Session(host, username, password);

        try
        {
            System.out.println("Connecting to POP3 server...");
            pop3.connectAndAuthenticate();
            System.out.println("Connected to POP3 server.");

            int messageCount = pop3.getMessageCount();
            System.out.println("\nWaiting massages on POP3 server : " + messageCount);

            String[] messages = pop3.getHeaders();

            for (int i=0; i<messages.length; i++)
            {
                StringTokenizer messageTokens = new StringTokenizer(messages[i]);
                String messageId = messageTokens.nextToken();
                String messageSize = messageTokens.nextToken();
                String messageBody = pop3.getMessage(messageId);
                System.out.println("\n-------------------- messsage " + messageId + ", size=" + messageSize + " --------------------");
                System.out.print(messageBody);
                System.out.println("-------------------- end of message " + messageId + " --------------------");
            }
        }
        catch (Exception e)
        {
            pop3.close();
            System.out.println("Can not receive e-mail!");
            e.printStackTrace();
        }
    }

    private static void sendMessage(String from, String password, String[] to, String subject, String body)
    {
            //TODO: send message
    }

    private static void printMessageInfo()
	{
        Properties properties = System.getProperties();
        properties.setProperty("mail.pop3.ssl.socketFactory.class", "AlwaysTrustSSLContextFactory");
        properties.setProperty("mail.pop3.ssl.socketFactory.port", "995");
        properties.setProperty("mail.pop3.ssl.enable", "true"); 

        URLName url = new URLName("pop3", host, port, "", username, password);

        try
		{
            Session session = Session.getInstance(properties, null);
            Store store = session.getStore(url);
            store.connect();

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            Message[] messages = folder.getMessages();

            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++)
            {
               Message message = messages[i];
               System.out.println("---------------------------------");
               System.out.println("Email Number " + (i + 1));
               System.out.println("Subject: " + message.getSubject());
               System.out.println("From: " + message.getFrom()[0]);
               System.out.println("Text: " + message.getContent().toString());
            }

            System.out.println(store.isConnected());
        }
		catch (MessagingException e)
		{
            e.printStackTrace();
        }
        catch (IOException e)
        {
			e.printStackTrace();
		}
    }
}