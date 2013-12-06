package me.protocos.xteam.api.model;

public class TeamDisbandEvent implements ITeamEvent
{
	private ITeam team;

	public TeamDisbandEvent(ITeam team)
	{
		this.team = team;
	}

	@Override
	public ITeam getTeam()
	{
		return team;
	}
}
