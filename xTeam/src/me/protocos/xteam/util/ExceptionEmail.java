package me.protocos.xteam.util;

public class ExceptionEmail
{
	private Exception exception;
	private String subject;
	private String body;

	private ExceptionEmail()
	{

	}

	public Exception getException()
	{
		return exception;
	}

	public String getSubject()
	{
		return subject;
	}

	public String getBody()
	{
		return body;
	}
	public static ExceptionEmail parse(Exception e, String pluginPackageID)
	{
		ExceptionEmail email = new ExceptionEmail();
		String errorString = e.toString();
		email.subject = errorString.substring(errorString.lastIndexOf(".") + 1) + " from anonymous user: " + SystemUtil.getUUID();
		email.body = "[ERROR] " + errorString;
		for (StackTraceElement elem : e.getStackTrace())
		{
			if (elem.toString().contains(pluginPackageID))
			{
				email.body += ("\n\t@ " + elem.toString().replaceAll(pluginPackageID, ""));
			}
		}
		return email;
	}
}