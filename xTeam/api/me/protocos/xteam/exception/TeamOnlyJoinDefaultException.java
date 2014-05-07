package me.protocos.xteam.exception;

import me.protocos.xteam.core.ITeamManager;

public class TeamOnlyJoinDefaultException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8955205510371306237L;

	public TeamOnlyJoinDefaultException(ITeamManager teamManager)
	{
		super("Only default teams are available\nTeams: " + teamManager.getDefaultTeams().getOrder().toString().replaceAll("[\\[\\]]", ""));
	}

	public TeamOnlyJoinDefaultException(String message)
	{
		super(message);
	}
}
