package me.protocos.xteam.event;

import me.protocos.xteam.entity.ITeam;

public class TeamCreateEvent implements ITeamEvent
{
	private ITeam team;

	public TeamCreateEvent(ITeam team)
	{
		this.team = team;
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
}
