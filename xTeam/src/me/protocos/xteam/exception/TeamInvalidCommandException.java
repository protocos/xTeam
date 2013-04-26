package me.protocos.xteam.exception;

public class TeamInvalidCommandException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4774506979536580599L;

	public TeamInvalidCommandException()
	{
		super("Not a valid command");
	}
	public TeamInvalidCommandException(String message)
	{
		super(message);
	}
}
