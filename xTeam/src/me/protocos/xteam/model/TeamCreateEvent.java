package me.protocos.xteam.model;

import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.api.model.ITeamEvent;

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
