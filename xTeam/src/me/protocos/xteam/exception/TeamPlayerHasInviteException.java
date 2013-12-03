package me.protocos.xteam.exception;

public class TeamPlayerHasInviteException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -283431481956102118L;

	public TeamPlayerHasInviteException()
	{
		super("Player already has invitation");
	}
	public TeamPlayerHasInviteException(String message)
	{
		super(message);
	}
}
