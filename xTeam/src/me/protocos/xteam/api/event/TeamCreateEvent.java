package me.protocos.xteam.api.event;

import me.protocos.xteam.api.entity.ITeam;

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
