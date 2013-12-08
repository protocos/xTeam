package me.protocos.xteam.core;

import java.lang.reflect.Method;
import java.util.List;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.core.ITeamManager;
import me.protocos.xteam.api.model.*;
import me.protocos.xteam.model.TeamCreateEvent;
import me.protocos.xteam.model.TeamDisbandEvent;
import me.protocos.xteam.model.TeamRenameEvent;
import me.protocos.xteam.util.CommonUtil;

public class TeamManager implements ITeamManager
{
	private static List<ITeamListener> listeners = CommonUtil.emptyList();
	private static HashList<String, ITeam> teams = CommonUtil.emptyHashList();

	public TeamManager()
	{
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
	public void addTeamListener(ITeamListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeTeamListener(ITeamListener listener)
	{
		listeners.remove(listener);
	}

	@Override
	public void dispatchEvent(ITeamEvent event)
	{
		for (ITeamListener listener : listeners)
		{
			dispatchEventTo(event, listener);
		}
	}

	private void dispatchEventTo(ITeamEvent event, ITeamListener listener)
	{
		List<Method> methods = getAllMethodsWithEventAnnotation(listener);
		for (Method method : methods)
		{
			try
			{
				method.setAccessible(true);
				Class<?>[] parameters = method.getParameterTypes();

				if (parameters.length == 1 && parameters[0].equals(event.getClass()))
				{
					method.invoke(listener, event);
				}
			}
			catch (Exception e)
			{
				System.err.println("Could not invoke event handler!");
				e.printStackTrace(System.err);
			}
		}
	}

	private List<Method> getAllMethodsWithEventAnnotation(ITeamListener listener)
	{
		Method[] methods = listener.getClass().getDeclaredMethods();
		List<Method> finalMethods = CommonUtil.emptyList();
		for (Method method : methods)
		{
			if (method.getAnnotation(TeamEvent.class) != null)
			{
				finalMethods.add(method);
			}
		}
		return finalMethods;
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
}
