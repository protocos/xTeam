package me.protocos.xteam.core;

import java.util.List;
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

	public boolean addTeam(Team team)
	{
		if (team != null)
		{
			teams.put(team.getName().toLowerCase(), team);
			return true;
		}
		return false;
	}

	public void clear()
	{
		teams.clear();
	}

	public boolean contains(String teamName)
	{
		return teams.get(teamName.toLowerCase()) != null;
	}

	public List<String> getAllTeamNames()
	{
		List<Team> allTeams = getAllTeams();
		List<String> allTeamNames = CommonUtil.emptyList();
		for (Team team : allTeams)
			allTeamNames.add(team.getName());
		return allTeamNames;
	}

	public List<Team> getAllTeams()
	{
		List<Team> allTeams = CommonUtil.emptyList();
		for (int x = 0; x < teams.size(); x++)
		{
			allTeams.add(teams.get(x));
		}
		return allTeams;
	}

	public List<String> getDefaultTeamNames()
	{
		List<Team> defaultTeams = getDefaultTeams();
		List<String> defaultTeamNames = CommonUtil.emptyList();
		for (Team team : defaultTeams)
			defaultTeamNames.add(team.getName());
		return defaultTeamNames;
	}

	public List<Team> getDefaultTeams()
	{
		List<Team> defaultTeams = CommonUtil.emptyList();
		for (int x = 0; x < teams.size(); x++)
		{
			if (teams.get(x).isDefaultTeam())
				defaultTeams.add(teams.get(x));
		}
		return defaultTeams;
	}

	public List<String> getRegularTeamNames()
	{
		List<Team> regularTeams = getRegularTeams();
		List<String> regularTeamNames = CommonUtil.emptyList();
		for (Team team : regularTeams)
			regularTeamNames.add(team.getName());
		return regularTeamNames;
	}

	public List<Team> getRegularTeams()
	{
		List<Team> regularTeams = CommonUtil.emptyList();
		for (int x = 0; x < teams.size(); x++)
		{
			if (!teams.get(x).isDefaultTeam())
				regularTeams.add(teams.get(x));
		}
		return regularTeams;
	}

	public Team getTeam(String teamName)
	{
		return teams.get(teamName.toLowerCase());
	}

	public Team removeTeam(String team)
	{
		return teams.remove(team.toLowerCase());
	}

	public Team getTeamFromPlayer(String name)
	{
		for (Team team : this.getAllTeams())
			if (team.containsPlayer(name))
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
