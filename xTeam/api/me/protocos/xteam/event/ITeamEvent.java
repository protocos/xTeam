package me.protocos.xteam.event;

import me.protocos.xteam.entity.ITeam;

public interface ITeamEvent
{
	public abstract ITeam getTeam();

	public abstract String getTeamName();
}
