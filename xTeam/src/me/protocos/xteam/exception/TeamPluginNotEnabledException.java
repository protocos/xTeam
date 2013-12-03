package me.protocos.xteam.exception;

public class TeamPluginNotEnabledException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6879153157301274167L;
	public TeamPluginNotEnabledException()
	{
		super("Teams are not enabled on this server");
	}
	public TeamPluginNotEnabledException(String message)
	{
		super(message);
	}
}
