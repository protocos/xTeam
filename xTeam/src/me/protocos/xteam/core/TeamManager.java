package me.protocos.xteam.core;

import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.core.ITeam;
import me.protocos.xteam.api.core.ITeamManager;
import me.protocos.xteam.util.CommonUtil;

public class TeamManager implements ITeamManager
{
	private static HashList<String, ITeam> teams = CommonUtil.emptyHashList();

	public TeamManager()
	{
	}

	public void clearData()
	{
		teams.clear();
	}

	public void addTeam(ITeam team)
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

	public HashList<String, ITeam> getTeams()
	{
		HashList<String, ITeam> allTeams = CommonUtil.emptyHashList();
		for (ITeam team : teams)
		{
			allTeams.put(team.getName(), team);
		}
		return allTeams;
	}

	public HashList<String, ITeam> getDefaultTeams()
	{
		HashList<String, ITeam> defaultTeams = CommonUtil.emptyHashList();
		for (ITeam team : teams)
		{
			if (team.isDefaultTeam())
				defaultTeams.put(team.getName(), team);
		}
		return defaultTeams;
	}

	public HashList<String, ITeam> getRegularTeams()
	{
		HashList<String, ITeam> regularTeams = CommonUtil.emptyHashList();
		for (ITeam team : teams)
		{
			if (!team.isDefaultTeam())
				regularTeams.put(team.getName(), team);
		}
		return regularTeams;
	}

	public ITeam getTeam(String teamName)
	{
		ITeam team = teams.get(teamName.toLowerCase());
		if (team != null)
		{
			return teams.get(teamName.toLowerCase());
		}
		for (ITeam teamByTag : getTeams())
		{
			if (teamByTag.getTag().equalsIgnoreCase(teamName))
				return teamByTag;
		}
		return null;
	}

	public ITeam removeTeam(String teamName)
	{
		if (this.containsTeam(teamName))
		{
			return teams.remove(this.getTeam(teamName).getName().toLowerCase());
		}
		return null;
	}

	public ITeam getTeamByPlayer(String playerName)
	{
		for (ITeam team : this.getTeams())
			if (team.containsPlayer(playerName))
				return team;
		return null;
	}

	public String toString()
	{
		String output = "";
		for (ITeam team : getTeams())
			output += team.getName() + ": " + team.getPlayers().toString().replaceAll("[\\[\\]]", "") + "\n";
		return output.trim();
	}
}
