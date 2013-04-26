package me.protocos.xteam.core.exception;

public class TeamInvalidPageException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8311772095418480165L;
	public TeamInvalidPageException()
	{
		super("Not a valid page number");
	}
	public TeamInvalidPageException(String message)
	{
		super(message);
	}
}
