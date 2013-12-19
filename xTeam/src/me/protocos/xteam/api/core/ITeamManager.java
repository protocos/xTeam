package me.protocos.xteam.api.core;

import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.entity.ITeam;
import me.protocos.xteam.api.event.ITeamEvent;
import me.protocos.xteam.api.model.ITeamListener;

public interface ITeamManager
{
	public abstract void clear();

	public abstract void createTeam(ITeam team);

	public abstract void renameTeam(ITeam team, String teamName);

	public abstract void disbandTeam(String teamName);

	public abstract ITeam getTeam(String teamName);

	public abstract ITeam getTeamByPlayer(String name);

	public abstract boolean containsTeam(String teamName);

	public abstract HashList<String, ITeam> getTeams();

	public abstract HashList<String, ITeam> getDefaultTeams();

	public abstract void addTeamListener(ITeamListener listener);

	public abstract void removeTeamListener(ITeamListener listener);

	public abstract void dispatchEvent(ITeamEvent event);
}
