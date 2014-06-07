package me.protocos.xteam.event;

import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;

public class TeamLeaveEvent implements ITeamEvent
{
	private ITeam team;
	private ITeamPlayer teamPlayer;

	public TeamLeaveEvent(ITeam team, ITeamPlayer teamPlayer)
	{
		this.team = team;
		this.teamPlayer = teamPlayer;
	}

	@Override
	public String getTeamName()
	{
		return team.getName();
	}

	@Override
	public ITeam getTeam()
	{
		return team;
	}

	public ITeamPlayer getPlayer()
	{
		return teamPlayer;
	}
}
