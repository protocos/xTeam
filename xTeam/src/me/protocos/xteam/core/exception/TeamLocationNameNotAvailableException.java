package me.protocos.xteam.core.exception;

public class TeamLocationNameNotAvailableException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3669071932671149244L;
	public TeamLocationNameNotAvailableException()
	{
		super("Location name not available");
	}
	public TeamLocationNameNotAvailableException(String message)
	{
		super(message);
	}
}
