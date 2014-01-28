package me.protocos.xteam.exception;

public class TeamPlayerDisabledWorldException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 810922277391810067L;

	public TeamPlayerDisabledWorldException()
	{
		super("xTeam is not enabled in this world");
	}

	public TeamPlayerDisabledWorldException(String message)
	{
		super(message);
	}
}
