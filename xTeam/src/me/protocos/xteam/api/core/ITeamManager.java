package me.protocos.xteam.api.core;

import java.util.List;
import me.protocos.xteam.core.Team;

public interface ITeamManager
{
	public abstract boolean addTeam(Team team);
	public abstract void clear();
	public abstract boolean contains(String team);
	public abstract List<String> getAllTeamNames();
	public abstract List<Team> getAllTeams();
	public abstract Team getTeam(String team);
	public abstract Team removeTeam(String team);
}
