package me.protocos.xteam.event;

import me.protocos.xteam.entity.ITeam;

public class TeamUpdateEvent implements ITeamEvent
{
	private ITeam team;

	public TeamUpdateEvent(ITeam team)
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
