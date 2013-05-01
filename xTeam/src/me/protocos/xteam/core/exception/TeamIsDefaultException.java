package me.protocos.xteam.core.exception;

public class TeamIsDefaultException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6465479807378316302L;

	public TeamIsDefaultException()
	{
		super("Team is default");
	}
	public TeamIsDefaultException(String message)
	{
		super(message);
	}
}
