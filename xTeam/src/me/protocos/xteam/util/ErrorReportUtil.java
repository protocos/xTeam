package me.protocos.xteam.util;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class ErrorReportUtil
{
	private static final String errorReporter = "xteam.errors@gmail.com";
	private String pluginPackageID;
	private Properties props;
	private Session session;

	public ErrorReportUtil(JavaPlugin plugin)
	{
		String packageString = plugin.getClass().getPackage().toString();
		this.pluginPackageID = packageString.substring(packageString.indexOf(' ') + 1, packageString.lastIndexOf('.') + 1);
		props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		session = Session.getInstance(props,
				new javax.mail.Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(errorReporter, "~g84fOz9!");
					}
				});
	}

	public void sendErrorReport(Exception e)
	{
		String errorReport = parseException(e);
		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("anonymous@protocos.me"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(errorReporter));
			message.setSubject("Testing Subject");
			message.setText(errorReport);

			Transport.send(message);

			System.out.println("Done");
		}
		catch (MessagingException e1)
		{
			throw new RuntimeException(e1);
		}
	}

	private String parseException(Exception e)
	{
		String error = "[ERROR] " + e.toString();
		for (StackTraceElement elem : e.getStackTrace())
		{
			if (elem.toString().contains(pluginPackageID))
			{
				error += ("\n\t@ " + elem.toString().replaceAll(pluginPackageID, ""));
			}
		}
		return error;
	}
}
