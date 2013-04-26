package me.protocos.xteam.exception;

public class TeamIsDefaultException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6465479807378316302L;

	public TeamIsDefaultException()
	{
		super("ITeam is default");
	}
	public TeamIsDefaultException(String message)
	{
		super(message);
	}
}
