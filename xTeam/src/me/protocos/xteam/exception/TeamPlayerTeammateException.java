package me.protocos.xteam.exception;

public class TeamPlayerTeammateException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4023733207866345427L;
	public TeamPlayerTeammateException()
	{
		super("Player teammate exception");
	}
	public TeamPlayerTeammateException(String message)
	{
		super(message);
	}
}
