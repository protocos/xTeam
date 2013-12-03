package me.protocos.xteam.exception;

public class TeamPlayerOfflineException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7475971066141492223L;

	public TeamPlayerOfflineException()
	{
		super("Player is not online");
	}

	public TeamPlayerOfflineException(String message)
	{
		super(message);
	}
}
