package me.protocos.xteam.core;

import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.event.*;
import me.protocos.xteam.util.CommonUtil;

public class TeamManager implements ITeamManager
{
	private IEventDispatcher dispatcher;
	private HashList<String, ITeam> teams = CommonUtil.emptyHashList();

	public TeamManager(IEventDispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
	}

	private void addTeam(ITeam team)
	{
		if (team != null)
		{
			teams.put(team.getName().toLowerCase(), team);
		}
	}

	@Override
	public void clear()
	{
		teams.clear();
	}

	@Override
	public boolean containsTeam(String teamName)
	{
		if (teamName != null)
		{
			return this.getTeam(teamName) != null;
		}
		return false;
	}

	@Override
	public HashList<String, ITeam> getTeams()
	{
		HashList<String, ITeam> allTeams = CommonUtil.emptyHashList();
		for (ITeam team : teams)
		{
			allTeams.put(team.getName(), team);
		}
		return allTeams;
	}

	@Override
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

	@Override
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

	private ITeam removeTeam(String teamName)
	{
		if (this.containsTeam(teamName))
		{
			return teams.remove(this.getTeam(teamName).getName().toLowerCase());
		}
		return null;
	}

	@Override
	public ITeam getTeamByPlayer(String playerName)
	{
		for (ITeam team : this.getTeams())
			if (team.containsPlayer(playerName))
				return team;
		return null;
	}

	@Override
	public String toString()
	{
		String output = "";
		for (ITeam team : getTeams())
			output += team.getName() + ": " + team.getPlayers().toString().replaceAll("[\\[\\]]", "") + "\n";
		return output.trim();
	}

	@Override
	public void createTeam(ITeam team)
	{
		if (!teams.containsKey(team.getName()))
		{
			this.addTeam(team);
			this.dispatchEvent(new TeamCreateEvent(team));
		}
	}

	@Override
	public void renameTeam(ITeam team, String teamName)
	{
		if (this.containsTeam(team.getName()))
		{
			String oldName = team.getName();
			ITeam renameTeam = this.removeTeam(team.getName());
			renameTeam.setName(teamName);
			this.addTeam(renameTeam);
			this.dispatchEvent(new TeamRenameEvent(team, oldName));
		}
	}

	@Override
	public void disbandTeam(String teamName)
	{
		if (this.containsTeam(teamName))
		{
			ITeam removeTeam = this.removeTeam(teamName);
			this.dispatchEvent(new TeamDisbandEvent(removeTeam));
		}
	}

	private void dispatchEvent(ITeamEvent event)
	{
		dispatcher.dispatchEvent(event);
	}
}
