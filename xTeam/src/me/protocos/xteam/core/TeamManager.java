package me.protocos.xteam.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.protocos.xteam.util.HashList;

public class TeamManager implements ITeamManager
{
	private HashList<String, Team> teams;

	public TeamManager()
	{
		teams = new HashList<String, Team>();
	}
	public boolean addTeam(Team team)
	{
		if (team != null)// && !teamExists(team.getName()))
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
	public boolean createTeam(String teamName)
	{
		Team team = new Team.Builder(teamName).build();
		return addTeam(team);
	}
	public boolean createTeamWithLeader(String teamName, String player)
	{
		List<String> players = new ArrayList<String>(Arrays.asList(player));
		List<String> admins = new ArrayList<String>(Arrays.asList(player));
		Team team = new Team.Builder(teamName).players(players).admins(admins).leader(player).build();
		return addTeam(team);
	}
	public ArrayList<String> getAllTeamNames()
	{
		List<Team> allTeams = getAllTeams();
		ArrayList<String> allTeamNames = new ArrayList<String>();
		for (Team team : allTeams)
			allTeamNames.add(team.getName());
		return allTeamNames;
	}
	public List<Team> getAllTeams()
	{
		List<Team> allTeams = new ArrayList<Team>();
		for (int x = 0; x < teams.size(); x++)
		{
			allTeams.add(teams.get(x));
		}
		return allTeams;
	}
	public ArrayList<String> getDefaultTeamNames()
	{
		ArrayList<Team> defaultTeams = getDefaultTeams();
		ArrayList<String> defaultTeamNames = new ArrayList<String>();
		for (Team team : defaultTeams)
			defaultTeamNames.add(team.getName());
		return defaultTeamNames;
	}
	public ArrayList<Team> getDefaultTeams()
	{
		ArrayList<Team> defaultTeams = new ArrayList<Team>();
		for (int x = 0; x < teams.size(); x++)
		{
			if (teams.get(x).isDefaultTeam())
				defaultTeams.add(teams.get(x));
		}
		return defaultTeams;
	}
	public ArrayList<String> getRegularTeamNames()
	{
		ArrayList<Team> regularTeams = getRegularTeams();
		ArrayList<String> regularTeamNames = new ArrayList<String>();
		for (Team team : regularTeams)
			regularTeamNames.add(team.getName());
		return regularTeamNames;
	}
	public ArrayList<Team> getRegularTeams()
	{
		ArrayList<Team> regularTeams = new ArrayList<Team>();
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
}
