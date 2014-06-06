package me.protocos.xteam.exception;

public class TeamInvalidCommandException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2065963142386150210L;

	public TeamInvalidCommandException()
	{
		super("Not a valid team command (use '/team help')");
	}

	public TeamInvalidCommandException(String message)
	{
		super(message);
	}
}
