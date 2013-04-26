package me.protocos.xteam.core.exception;

public class TeamPlayerNotAdminException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2977420859591957108L;
	public TeamPlayerNotAdminException()
	{
		super("Player not a team admin");
	}
	public TeamPlayerNotAdminException(String message)
	{
		super(message);
	}
}
