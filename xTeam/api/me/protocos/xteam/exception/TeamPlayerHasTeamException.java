package me.protocos.xteam.exception;

public class TeamPlayerHasTeamException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5816341461498319575L;
	public TeamPlayerHasTeamException()
	{
		super("Player already has a team");
	}
	public TeamPlayerHasTeamException(String message)
	{
		super(message);
	}
}
