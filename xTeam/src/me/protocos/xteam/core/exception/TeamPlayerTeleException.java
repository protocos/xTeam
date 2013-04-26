package me.protocos.xteam.core.exception;

public class TeamPlayerTeleException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 951431202742346862L;
	public TeamPlayerTeleException()
	{
		super("Player cannot teleport");
	}
	public TeamPlayerTeleException(String message)
	{
		super(message);
	}
}
