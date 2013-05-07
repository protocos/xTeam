package me.protocos.xteam.core.exception;

public class TeamPlayerNeverPlayedException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3210143958454553137L;

	public TeamPlayerNeverPlayedException()
	{
		super("Player has never been on the server (capital letters matter)");
	}
	public TeamPlayerNeverPlayedException(String message)
	{
		super(message);
	}
}
