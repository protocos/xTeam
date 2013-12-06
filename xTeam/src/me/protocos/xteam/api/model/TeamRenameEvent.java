package me.protocos.xteam.api.model;

public class TeamRenameEvent implements ITeamEvent
{
	private ITeam team;

	public TeamRenameEvent(ITeam team)
	{
		this.team = team;
	}

	@Override
	public ITeam getTeam()
	{
		return team;
	}
}
