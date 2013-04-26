package me.protocos.xteam.core.exception;

public class TeamLocationNotFoundException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6634670827870264639L;
	public TeamLocationNotFoundException()
	{
		super("Location does not exist");
	}
	public TeamLocationNotFoundException(String message)
	{
		super(message);
	}
}
