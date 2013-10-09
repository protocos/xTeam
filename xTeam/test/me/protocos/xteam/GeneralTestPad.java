package me.protocos.xteam;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import me.protocos.xteam.util.CommonUtil;
import org.junit.Ignore;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	@Ignore
	public void testSMTPGmail()
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		final String strAppend1 = CommonUtil.hexString(CommonUtil.lstatic);
		final String strAppend2 = CommonUtil.hexString(CommonUtil.pstatic);
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(strAppend1, strAppend2);
					}
				});

		try
		{

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(strAppend1));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(strAppend1));
			message.setSubject("Test Subject");
			message.setText("Test");

			Transport.send(message);

		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}
}