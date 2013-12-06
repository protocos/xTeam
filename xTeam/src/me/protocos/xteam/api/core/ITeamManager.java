package me.protocos.xteam.api.core;

import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.api.model.ITeamListener;

public interface ITeamManager
{
	public abstract void clear();

	public abstract void addTeam(ITeam team);

	public abstract ITeam getTeam(String teamName);

	public abstract boolean containsTeam(String teamName);

	public abstract ITeam removeTeam(String teamName);

	public abstract HashList<String, ITeam> getTeams();

	public abstract HashList<String, ITeam> getDefaultTeams();

	public abstract ITeam getTeamByPlayer(String name);

	public abstract void addTeamListener(ITeamListener listener);
}
