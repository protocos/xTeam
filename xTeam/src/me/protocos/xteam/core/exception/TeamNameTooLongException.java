package me.protocos.xteam.core.exception;

import me.protocos.xteam.core.Configuration;


public class TeamNameTooLongException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3974395143383574634L;

	public TeamNameTooLongException()
	{
		super("Team name too long, must be <= " + Configuration.TEAM_TAG_LENGTH + " characters");
	}
	public TeamNameTooLongException(String message)
	{
		super(message);
	}
}
