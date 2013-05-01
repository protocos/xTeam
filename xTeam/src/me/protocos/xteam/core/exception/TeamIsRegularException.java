package me.protocos.xteam.core.exception;

public class TeamIsRegularException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8319664687087693862L;

	public TeamIsRegularException()
	{
		super("Team is a regular team");
	}
	public TeamIsRegularException(String message)
	{
		super(message);
	}
}
