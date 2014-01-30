package me.protocos.xteam.core;

import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.entity.ITeam;

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
}
