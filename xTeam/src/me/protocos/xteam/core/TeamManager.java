package me.protocos.xteam.core;

import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.core.ITeamManager;
import me.protocos.xteam.util.CommonUtil;

public class TeamManager implements ITeamManager
{
	private static HashList<String, Team> teams = CommonUtil.emptyHashList();

	public TeamManager()
	{
	}

	public void clearData()
	{
		teams.clear();
	}

	public void addTeam(Team team)
	{
		if (team != null)
		{
			teams.put(team.getName().toLowerCase(), team);
		}
	}

	public void clear()
	{
		teams.clear();
	}

	public boolean containsTeam(String teamName)
	{
		if (teamName != null)
		{
			return this.getTeam(teamName) != null;
		}
		return false;
	}

	public HashList<String, Team> getAllTeams()
	{
		HashList<String, Team> allTeams = CommonUtil.emptyHashList();
		for (Team team : teams)
		{
			allTeams.put(team.getName(), team);
		}
		return allTeams;
	}

	public HashList<String, Team> getDefaultTeams()
	{
		HashList<String, Team> defaultTeams = CommonUtil.emptyHashList();
		for (Team team : teams)
		{
			if (team.isDefaultTeam())
				defaultTeams.put(team.getName(), team);
		}
		return defaultTeams;
	}

	public HashList<String, Team> getRegularTeams()
	{
		HashList<String, Team> regularTeams = CommonUtil.emptyHashList();
		for (Team team : teams)
		{
			if (!team.isDefaultTeam())
				regularTeams.put(team.getName(), team);
		}
		return regularTeams;
	}

	public Team getTeam(String teamName)
	{
		Team team = teams.get(teamName.toLowerCase());
		if (team != null)
		{
			return teams.get(teamName.toLowerCase());
		}
		for (Team teamByTag : getAllTeams())
		{
			if (teamByTag.getTag().equalsIgnoreCase(teamName))
				return teamByTag;
		}
		return null;
	}

	public Team removeTeam(String teamName)
	{
		if (this.containsTeam(teamName))
		{
			return teams.remove(this.getTeam(teamName).getName().toLowerCase());
		}
		return null;
	}

	public Team getTeamByPlayer(String playerName)
	{
		for (Team team : this.getAllTeams())
			if (team.containsPlayer(playerName))
				return team;
		return null;
	}

	public String toString()
	{
		String output = "";
		for (Team team : getAllTeams())
			output += team.getName() + ": " + team.getPlayers().toString().replaceAll("[\\[\\]]", "") + "\n";
		return output.trim();
	}
}
