package me.protocos.xteam.exception;

public class TeamPlayerAlreadyUsedRallyException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 810922277391810067L;

	public TeamPlayerAlreadyUsedRallyException()
	{
		super("Player already used rally command");
	}

	public TeamPlayerAlreadyUsedRallyException(String message)
	{
		super(message);
	}
}
