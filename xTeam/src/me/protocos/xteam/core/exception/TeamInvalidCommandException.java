package me.protocos.xteam.core.exception;

import me.protocos.xteam.command.teamuser.UserHelp;

public class TeamInvalidCommandException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2065963142386150210L;

	public TeamInvalidCommandException()
	{
		super("Not a valid team command (use " + (new UserHelp()).getUsage() + ")");
	}
	public TeamInvalidCommandException(String message)
	{
		super(message);
	}
}
