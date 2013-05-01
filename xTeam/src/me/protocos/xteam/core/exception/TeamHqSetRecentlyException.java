package me.protocos.xteam.core.exception;

import me.protocos.xteam.core.Data;

public class TeamHqSetRecentlyException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8375737032352729313L;

	public TeamHqSetRecentlyException()
	{
		super("Team headquarters set in the last " + Data.HQ_INTERVAL + " hours");
	}
	public TeamHqSetRecentlyException(String message)
	{
		super(message);
	}
}
