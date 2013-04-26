package me.protocos.xteam.core.exception;

public class TeamPlayerDemoteException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -978095919550133729L;
	public TeamPlayerDemoteException()
	{
		super("Player cannot be demoted");
	}
	public TeamPlayerDemoteException(String message)
	{
		super(message);
	}
}
