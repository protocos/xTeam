package me.protocos.xteam.core.exception;

import me.protocos.xteam.xTeam;
public class TeamOnlyJoinDefaultException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8955205510371306237L;

	public TeamOnlyJoinDefaultException()
	{
		super("Only default teams are available\nTeams: " + xTeam.getInstance().getTeamManager().getDefaultTeamNames().toString().replaceAll("[\\[\\]]", ""));
	}
	public TeamOnlyJoinDefaultException(String message)
	{
		super(message);
	}
}
