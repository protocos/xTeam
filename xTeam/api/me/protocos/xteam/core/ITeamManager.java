package me.protocos.xteam.core;

import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.IDataManager;
import me.protocos.xteam.entity.ITeam;

public interface ITeamManager
{
	public abstract void open();

	public abstract void read();

	public abstract void write();

	public abstract void close();

	public abstract void clear();

	public abstract void setDataManager(IDataManager dataManager);

	public abstract void createTeam(ITeam team);

	public abstract void updateTeam(ITeam team);

	public abstract void renameTeam(ITeam team, String teamName);

	public abstract void disbandTeam(String teamName);

	public abstract ITeam getTeam(String teamName);

	public abstract ITeam getTeamByPlayer(String name);

	public abstract boolean containsTeam(String teamName);

	public abstract HashList<String, ITeam> getTeams();

	public abstract HashList<String, ITeam> getDefaultTeams();
}
