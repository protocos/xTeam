package me.protocos.xteam.exception;

public class TeamPlayerLeaderLeavingException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6753351743369242953L;
	public TeamPlayerLeaderLeavingException()
	{
		super("Player cannot leave team as leader\nPlease set new team leader");
	}
	public TeamPlayerLeaderLeavingException(String message)
	{
		super(message);
	}
}
