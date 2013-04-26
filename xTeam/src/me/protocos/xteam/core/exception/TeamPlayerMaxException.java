package me.protocos.xteam.core.exception;

import me.protocos.xteam.core.Data;

public class TeamPlayerMaxException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -595530029408123494L;

	public TeamPlayerMaxException()
	{
		super("ITeam teamPlayer limit reached: " + Data.MAX_PLAYERS);
	}
	public TeamPlayerMaxException(String message)
	{
		super(message);
	}
}
