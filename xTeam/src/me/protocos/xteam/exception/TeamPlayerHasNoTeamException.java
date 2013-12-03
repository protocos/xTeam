package me.protocos.xteam.exception;

public class TeamPlayerHasNoTeamException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3772138569143910657L;
	public TeamPlayerHasNoTeamException()
	{
		super("Player does not have a team");
	}
	public TeamPlayerHasNoTeamException(String message)
	{
		super(message);
	}
}
