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

	public boolean contains(String teamName)
	{
		return teams.get(teamName.toLowerCase()) != null;
	}

	//	public List<String> getAllTeamNames()
	//	{
	//		List<Team> allTeams = getAllTeams();
	//		List<String> allTeamNames = CommonUtil.emptyList();
	//		for (Team team : allTeams)
	//			allTeamNames.add(team.getName());
	//		return allTeamNames;
	//	}

	public HashList<String, Team> getAllTeams()
	{
		HashList<String, Team> allTeams = CommonUtil.emptyHashList();
		for (Team team : teams)
		{
			allTeams.put(team.getName(), team);
		}
		return allTeams;
	}

	//	public List<String> getDefaultTeamNames()
	//	{
	//		List<Team> defaultTeams = getDefaultTeams();
	//		List<String> defaultTeamNames = CommonUtil.emptyList();
	//		for (Team team : defaultTeams)
	//			defaultTeamNames.add(team.getName());
	//		return defaultTeamNames;
	//	}

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

	//	public List<String> getRegularTeamNames()
	//	{
	//		List<Team> regularTeams = getRegularTeams();
	//		List<String> regularTeamNames = CommonUtil.emptyList();
	//		for (Team team : regularTeams)
	//			regularTeamNames.add(team.getName());
	//		return regularTeamNames;
	//	}

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
		return teams.get(teamName.toLowerCase());
	}

	public Team getTeamByTag(String tag)
	{
		for (Team team : getAllTeams())
		{
			if (team.getTag().equalsIgnoreCase(tag))
				return team;
		}
		return null;
	}

	public Team removeTeam(String team)
	{
		return teams.remove(team.toLowerCase());
	}

	public Team getTeamByPlayer(String name)
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
