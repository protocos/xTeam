package me.protocos.xteam.exception;

import me.protocos.xteam.command.teamuser.TeamUserHelp;

public class TeamInvalidCommandException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2065963142386150210L;

	public TeamInvalidCommandException()
	{
		super("Not a valid team command (use '" + (new TeamUserHelp()).getUsage() + "')");
	}

	public TeamInvalidCommandException(String message)
	{
		super(message);
	}
}
