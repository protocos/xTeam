package me.protocos.xteam.core;

import java.util.List;
import me.protocos.api.collection.OrderedHashMap;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.event.IEventDispatcher;
import me.protocos.xteam.event.TeamCreateEvent;
import me.protocos.xteam.event.TeamDisbandEvent;
import me.protocos.xteam.event.TeamRenameEvent;

public class TeamCoordinator implements ITeamCoordinator
{
	private IEventDispatcher dispatcher;
	private InviteHandler inviteHandler;
	private OrderedHashMap<String, ITeam> teams = CommonUtil.emptyOrderedHashMap();

	public TeamCoordinator(TeamPlugin teamPlugin)
	{
		this.dispatcher = teamPlugin.getEventDispatcher();
		this.inviteHandler = teamPlugin.getInviteHandler();
	}

	@Override
	public void clear()
	{
		teams.clear();
	}

	public void putTeam(ITeam team)
	{
		if (team != null)
		{
			teams.put(team.getName().toLowerCase(), team);
		}
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
	public OrderedHashMap<String, ITeam> getTeams()
	{
		OrderedHashMap<String, ITeam> allTeams = CommonUtil.emptyOrderedHashMap();
		for (ITeam team : teams)
		{
			allTeams.put(team.getName(), team);
		}
		return allTeams;
	}

	@Override
	public OrderedHashMap<String, ITeam> getDefaultTeams()
	{
		OrderedHashMap<String, ITeam> defaultTeams = CommonUtil.emptyOrderedHashMap();
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
			this.putTeam(team);
			dispatcher.dispatchEvent(new TeamCreateEvent(team));
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
			this.putTeam(renameTeam);
			dispatcher.dispatchEvent(new TeamRenameEvent(team, oldName));
		}
	}

	@Override
	public void disbandTeam(String teamName)
	{
		if (this.containsTeam(teamName))
		{
			inviteHandler.removeTeamInvites(this.getTeam(teamName));
			ITeam removeTeam = this.removeTeam(teamName);
			dispatcher.dispatchEvent(new TeamDisbandEvent(removeTeam));
		}
	}

	@Override
	public List<String> exportData()
	{
		List<String> exportTeams = CommonUtil.emptyList(teams.size());
		for (ITeam team : teams)
		{
			exportTeams.add(team.toString());
		}
		return exportTeams;
	}
}
