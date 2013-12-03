package me.protocos.xteam.exception;

import me.protocos.xteam.core.Configuration;


public class TeamLocationMaxException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745577285523752218L;

	public TeamLocationMaxException()
	{
		super("Location limit reached: " + Configuration.MAX_NUM_LOCATIONS);
	}
	public TeamLocationMaxException(String message)
	{
		super(message);
	}
}
