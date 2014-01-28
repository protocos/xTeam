package me.protocos.xteam.exception;

import me.protocos.xteam.data.configuration.Configuration;

public class TeamNameTooLongException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3974395143383574634L;

	public TeamNameTooLongException()
	{
		super("Team name too long, must be <= " + Configuration.TEAM_NAME_LENGTH + " characters");
	}

	public TeamNameTooLongException(String message)
	{
		super(message);
	}
}
