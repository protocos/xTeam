package me.protocos.xteam.exception;

public class TeamAlreadyExistsException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 810922277391810067L;

	public TeamAlreadyExistsException()
	{
		super("ITeam already exist");
	}
	public TeamAlreadyExistsException(String message)
	{
		super(message);
	}
}
