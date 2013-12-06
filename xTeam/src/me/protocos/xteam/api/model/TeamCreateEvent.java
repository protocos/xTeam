package me.protocos.xteam.api.model;

public class TeamCreateEvent implements ITeamEvent
{
	private ITeam team;

	public TeamCreateEvent(ITeam team)
	{
		this.team = team;
	}

	@Override
	public ITeam getTeam()
	{
		return team;
	}
}
