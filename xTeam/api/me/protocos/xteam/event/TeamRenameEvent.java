package me.protocos.xteam.event;

import me.protocos.xteam.entity.ITeam;

public class TeamRenameEvent implements ITeamEvent
{
	private final ITeam team;
	private final String oldName;

	public TeamRenameEvent(ITeam team, String oldName)
	{
		this.team = team;
		this.oldName = oldName;
	}

	public String getOldName()
	{
		return oldName;
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
