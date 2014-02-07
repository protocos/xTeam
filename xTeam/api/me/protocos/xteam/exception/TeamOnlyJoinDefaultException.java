package me.protocos.xteam.exception;

import me.protocos.xteam.XTeam;
public class TeamOnlyJoinDefaultException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8955205510371306237L;

	public TeamOnlyJoinDefaultException()
	{
		super("Only default teams are available\nTeams: " + XTeam.getInstance().getTeamManager().getDefaultTeams().getOrder().toString().replaceAll("[\\[\\]]", ""));
	}
	public TeamOnlyJoinDefaultException(String message)
	{
		super(message);
	}
}
