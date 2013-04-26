package me.protocos.xteam.core;

import java.util.List;

public interface ITeamManager
{
	public abstract boolean addTeam(Team team);
	public abstract Team getTeam(String team);
	public abstract Team removeTeam(String team);
	public abstract void clear();
	public abstract boolean contains(String team);
	public abstract List<Team> getAllTeams();
	public abstract List<String> getAllTeamNames();
}
