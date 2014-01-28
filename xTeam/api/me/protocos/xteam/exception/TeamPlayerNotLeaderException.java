package me.protocos.xteam.exception;

public class TeamPlayerNotLeaderException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1535253606889106517L;
	public TeamPlayerNotLeaderException()
	{
		super("Player not team leader");
	}
	public TeamPlayerNotLeaderException(String message)
	{
		super(message);
	}
}
