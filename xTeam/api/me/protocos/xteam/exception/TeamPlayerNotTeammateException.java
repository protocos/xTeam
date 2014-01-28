package me.protocos.xteam.exception;

public class TeamPlayerNotTeammateException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1981047281611601175L;

	public TeamPlayerNotTeammateException()
	{
		super("Player is not a teammate");
	}
	public TeamPlayerNotTeammateException(String message)
	{
		super(message);
	}
}
