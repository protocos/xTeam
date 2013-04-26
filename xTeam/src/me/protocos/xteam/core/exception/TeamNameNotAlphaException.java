package me.protocos.xteam.core.exception;

public class TeamNameNotAlphaException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4491602627610346954L;

	public TeamNameNotAlphaException()
	{
		super("ITeam name must be alphanumeric");
	}
	public TeamNameNotAlphaException(String message)
	{
		super(message);
	}
}
