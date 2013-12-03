package me.protocos.xteam.exception;

public abstract class TeamException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2019349109085589773L;

	public TeamException()
	{
		super("Team exception");
	}
	public TeamException(String message)
	{
		super(message);
	}
}
