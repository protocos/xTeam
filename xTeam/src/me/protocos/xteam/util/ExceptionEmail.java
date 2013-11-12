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

	public static ExceptionEmail parse(String error, String pluginPackageID)
	{
		ExceptionEmail email = new ExceptionEmail();
		String[] errorLines = error.split("\n");
		email.subject = errorLines[0];
		email.body = "[ERROR] " + errorLines[1];
		for (String elem : errorLines)
		{
			if (elem.contains(pluginPackageID))
			{
				email.body += ("\n\t@ " + elem.toString().replaceAll(pluginPackageID, ""));
			}
		}
		return email;
	}
}
