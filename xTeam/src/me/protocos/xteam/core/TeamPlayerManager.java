package me.protocos.xteam.core;

import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.api.core.ITeamPlayerManager;

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
