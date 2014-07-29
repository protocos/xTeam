package me.protocos.xteam.core;

import me.protocos.api.collection.OrderedHashMap;
import me.protocos.xteam.data.IDataContainer;
import me.protocos.xteam.entity.ITeam;

public interface ITeamCoordinator extends IDataContainer
{
	public abstract void clear();

	public abstract void putTeam(ITeam team);

	public abstract void createTeam(ITeam team);

	public abstract void renameTeam(ITeam team, String teamName);

	public abstract void disbandTeam(String teamName);

	public abstract ITeam getTeam(String teamName);

	public abstract ITeam getTeamByPlayer(String name);

	public abstract boolean containsTeam(String teamName);

	public abstract OrderedHashMap<String, ITeam> getTeams();

	public abstract OrderedHashMap<String, ITeam> getDefaultTeams();
}
