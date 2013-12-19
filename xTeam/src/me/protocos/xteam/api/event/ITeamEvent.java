package me.protocos.xteam.api.event;

import me.protocos.xteam.api.entity.ITeam;

public interface ITeamEvent
{
	public abstract ITeam getTeam();

	public abstract String getTeamName();
}
