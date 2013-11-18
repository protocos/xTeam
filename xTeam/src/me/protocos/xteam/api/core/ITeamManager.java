package me.protocos.xteam.api.core;

import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.core.Team;

public interface ITeamManager
{
	public abstract void clear();

	public abstract void addTeam(Team team);

	public abstract Team getTeam(String string);

	public abstract Team getTeamByTag(String tag);

	public abstract boolean contains(String teamName);

	public abstract Team removeTeam(String teamName);

	public abstract HashList<String, Team> getDefaultTeams();

	public abstract HashList<String, Team> getAllTeams();

	public abstract Team getTeamByPlayer(String name);
}
