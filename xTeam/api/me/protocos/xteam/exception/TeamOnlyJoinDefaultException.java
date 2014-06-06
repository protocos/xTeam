package me.protocos.xteam.exception;

import me.protocos.xteam.core.ITeamCoordinator;

public class TeamOnlyJoinDefaultException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8955205510371306237L;

	public TeamOnlyJoinDefaultException(ITeamCoordinator teamCoordinator)
	{
		super("Only default teams are available\nTeams: " + teamCoordinator.getDefaultTeams().getOrder().toString().replaceAll("[\\[\\]]", ""));
	}

	public TeamOnlyJoinDefaultException(String message)
	{
		super(message);
	}
}
