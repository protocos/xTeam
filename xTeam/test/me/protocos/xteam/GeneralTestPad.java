package me.protocos.xteam;

import static org.junit.Assert.fail;
import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.junit.Ignore;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	@Ignore
	public void testSMTPGmail()
	{
		final String username = "xteam.errors@gmail.com";
		final String password = "~g84fOz9!";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(username, password);
					}
				});

		try
		{

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
			message.setSubject("Test Subject");
			message.setText("Test");

			Transport.send(message);

			System.out.println("Done");

		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}
}