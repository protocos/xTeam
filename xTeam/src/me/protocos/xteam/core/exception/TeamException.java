package me.protocos.xteam.core.exception;

public abstract class TeamException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2019349109085589773L;

	public TeamException()
	{
		super("ITeam exception");
	}
	public TeamException(String message)
	{
		super(message);
	}
}
