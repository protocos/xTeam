package me.protocos.xteam.exception;

public class TeamPlayerNeverPlayedException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3210143958454553137L;
	public TeamPlayerNeverPlayedException()
	{
		super("Player has never been on the server");
	}
	public TeamPlayerNeverPlayedException(String message)
	{
		super(message);
	}
}
