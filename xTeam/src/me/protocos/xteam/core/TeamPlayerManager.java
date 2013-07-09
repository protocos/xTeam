package me.protocos.xteam.core;

import me.protocos.xteam.util.HashList;

public class TeamPlayerManager implements ITeamPlayerManager
{
	HashList<String, ITeamPlayer> players;

	public TeamPlayerManager()
	{
		players = new HashList<String, ITeamPlayer>();
	}

	public ITeamPlayer getTeamPlayer(String name)
	{
		return players.get(name);
	}

	public void logOff(String name)
	{
		players.remove(name);
	}

	public void logOn(ITeamPlayer player)
	{
		players.put(player.getName(), player);
	}

}
