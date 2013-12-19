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

	public static ExceptionEmail parse(Exception error, String pluginPackageID)
	{
		ExceptionEmail email = new ExceptionEmail();
		StackTraceElement[] errorLines = error.getStackTrace();
		email.subject = "Exception: " + error.toString() + " from anonymous user: " + SystemUtil.getUUID();
		email.body = "[ERROR] " + error.toString();
		for (StackTraceElement elem : errorLines)
		{
			if (elem.toString().contains(pluginPackageID))
			{
				email.body += ("\n\t@ " + elem.toString().replaceAll(pluginPackageID, ""));
			}
		}
		return email;
	}
}
