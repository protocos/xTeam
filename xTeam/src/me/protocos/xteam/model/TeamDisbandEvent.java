package me.protocos.xteam.model;

import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.api.model.ITeamEvent;

public class TeamDisbandEvent implements ITeamEvent
{
	private ITeam team;

	public TeamDisbandEvent(ITeam team)
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
