package me.protocos.xteam.exception;

public class TeamNameAlreadyInUseException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 810922277391810067L;

	public TeamNameAlreadyInUseException()
	{
		super("Team name or tag already in use");
	}

	public TeamNameAlreadyInUseException(String message)
	{
		super(message);
	}
}
