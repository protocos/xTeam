package me.protocos.xteam.exception;

public class TeamAlreadyHasRallyException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2674061934637948415L;

	public TeamAlreadyHasRallyException()
	{
		super("Team already has a rally point");
	}

	public TeamAlreadyHasRallyException(String message)
	{
		super(message);
	}
}
