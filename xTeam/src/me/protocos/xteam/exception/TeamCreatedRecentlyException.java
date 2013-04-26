package me.protocos.xteam.exception;

import me.protocos.xteam.core.Data;

public class TeamCreatedRecentlyException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5216741291942203085L;

	public TeamCreatedRecentlyException()
	{
		super("ITeam created in the last " + Data.CREATE_INTERVAL + " minutes");
	}
	public TeamCreatedRecentlyException(String message)
	{
		super(message);
	}
}
