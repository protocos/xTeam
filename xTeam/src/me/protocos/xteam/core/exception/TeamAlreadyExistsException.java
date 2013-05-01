package me.protocos.xteam.core.exception;

public class TeamAlreadyExistsException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 810922277391810067L;

	public TeamAlreadyExistsException()
	{
		super("Team already exist");
	}
	public TeamAlreadyExistsException(String message)
	{
		super(message);
	}
}
