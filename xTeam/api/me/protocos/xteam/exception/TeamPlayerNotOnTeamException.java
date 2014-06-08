package me.protocos.xteam.exception;

public class TeamPlayerNotOnTeamException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7475971066141492223L;

	public TeamPlayerNotOnTeamException()
	{
		super("Player not on team");
	}

	public TeamPlayerNotOnTeamException(String message)
	{
		super(message);
	}
}
