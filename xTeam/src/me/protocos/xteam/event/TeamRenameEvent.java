package me.protocos.xteam.event;

import me.protocos.xteam.entity.ITeam;

public class TeamRenameEvent implements ITeamEvent
{
	private final ITeam team;
	private final String newName;
	private final String oldName;

	public TeamRenameEvent(ITeam team, String oldName)
	{
		this.team = team;
		this.newName = team.getName();
		this.oldName = oldName;
	}

	public String getNewName()
	{
		return newName;
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
