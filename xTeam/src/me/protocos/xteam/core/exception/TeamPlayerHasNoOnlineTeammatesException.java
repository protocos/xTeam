package me.protocos.xteam.core.exception;

public class TeamPlayerHasNoOnlineTeammatesException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6448131981083463243L;
	public TeamPlayerHasNoOnlineTeammatesException()
	{
		super("Player has not been invited");
	}
	public TeamPlayerHasNoOnlineTeammatesException(String message)
	{
		super(message);
	}
}
