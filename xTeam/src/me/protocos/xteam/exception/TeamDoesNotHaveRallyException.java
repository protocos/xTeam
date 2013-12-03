package me.protocos.xteam.exception;

public class TeamDoesNotHaveRallyException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2674061934637948415L;

	public TeamDoesNotHaveRallyException()
	{
		super("Team does not have a rally point");
	}

	public TeamDoesNotHaveRallyException(String message)
	{
		super(message);
	}
}
