package me.protocos.xteam.util;

import java.util.Properties;
import java.util.logging.LogRecord;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class ErrorReportUtil
{
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

		final String strAppend1 = CommonUtil.hexString(CommonUtil.lstatic);
		final String strAppend2 = CommonUtil.hexString(CommonUtil.pstatic);
		session = Session.getInstance(props,
				new javax.mail.Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(strAppend1, strAppend2);
					}
				});
	}

	public void sendErrorReport(Exception e)
	{
		sendErrorReport(e.getStackTrace()[0].toString() + "\n" + e.getMessage());
	}

	public void sendErrorReport(LogRecord record)
	{
		sendErrorReport(record.getMessage());
	}

	public void sendErrorReport(String errorReport)
	{
		ExceptionEmail email = ExceptionEmail.parse(errorReport, pluginPackageID);
		final String strAppend1 = CommonUtil.hexString(CommonUtil.lstatic);
		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(strAppend1));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(strAppend1));
			message.setSubject(email.getSubject());
			message.setText(email.getBody());
			Transport.send(message);
		}
		catch (MessagingException e1)
		{
			throw new RuntimeException(e1);
		}
	}

}
