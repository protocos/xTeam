package me.protocos.xteam.exception;

import me.protocos.xteam.configuration.Configuration;


public class TeamPlayerMaxException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -595530029408123494L;

	public TeamPlayerMaxException()
	{
		super("Team teamPlayer limit reached: " + Configuration.MAX_PLAYERS);
	}
	public TeamPlayerMaxException(String message)
	{
		super(message);
	}
}
