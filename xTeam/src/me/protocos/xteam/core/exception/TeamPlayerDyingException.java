package me.protocos.xteam.core.exception;

public class TeamPlayerDyingException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1634216650286220480L;
	public TeamPlayerDyingException()
	{
		super("Player cannot perform that action while dying");
	}
	public TeamPlayerDyingException(String message)
	{
		super(message);
	}
}
