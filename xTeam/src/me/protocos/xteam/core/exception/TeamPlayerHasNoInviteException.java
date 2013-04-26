package me.protocos.xteam.core.exception;

public class TeamPlayerHasNoInviteException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6448131981083463243L;
	public TeamPlayerHasNoInviteException()
	{
		super("Player has not been invited");
	}
	public TeamPlayerHasNoInviteException(String message)
	{
		super(message);
	}
}
