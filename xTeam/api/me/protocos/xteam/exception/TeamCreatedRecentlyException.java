package me.protocos.xteam.exception;

import me.protocos.xteam.data.configuration.Configuration;


public class TeamCreatedRecentlyException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5216741291942203085L;

	public TeamCreatedRecentlyException()
	{
		super("Team created in the last " + Configuration.CREATE_INTERVAL + " minutes");
	}
	public TeamCreatedRecentlyException(String message)
	{
		super(message);
	}
}
