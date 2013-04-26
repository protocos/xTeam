package me.protocos.xteam.exception;

public class TeamPlayerLeaderDemoteException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7817328750606948917L;
	public TeamPlayerLeaderDemoteException()
	{
		super("Player cannot be demoted as leader");
	}
	public TeamPlayerLeaderDemoteException(String message)
	{
		super(message);
	}
}
