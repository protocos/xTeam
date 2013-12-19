package me.protocos.xteam.api.event;

import me.protocos.xteam.api.entity.ITeam;

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
