package me.protocos.xteam.core.exception;

public class TeamPlayerInviteException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5752669615372839223L;
	public TeamPlayerInviteException()
	{
		super("Player cannot be invited");
	}
	public TeamPlayerInviteException(String message)
	{
		super(message);
	}
}
