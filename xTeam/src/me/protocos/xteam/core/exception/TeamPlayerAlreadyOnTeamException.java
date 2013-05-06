package me.protocos.xteam.core.exception;

public class TeamPlayerAlreadyOnTeamException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8377139044271649148L;

	public TeamPlayerAlreadyOnTeamException()
	{
		super("Player is already on that team");
	}
	public TeamPlayerAlreadyOnTeamException(String message)
	{
		super(message);
	}
}
