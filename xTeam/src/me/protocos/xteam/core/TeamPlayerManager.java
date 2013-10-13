package me.protocos.xteam.core;

import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.core.ITeamPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeamPlayerManager
{
	HashList<String, ITeamPlayer> players;

	public TeamPlayerManager()
	{
		players = new HashList<String, ITeamPlayer>();
	}

	public boolean addPlayer(String name)
	{
		Player player = Bukkit.getPlayer(name);
		if (player == null)
			return false;
		ITeamPlayer newTeamPlayer = new TeamPlayer(player);
		players.put(name, newTeamPlayer);
		return true;
	}

	public ITeamPlayer getPlayer(Player player)
	{
		String name = player.getName();
		return this.getPlayer(name);
	}

	public ITeamPlayer getPlayer(String name)
	{
		if (this.exists(name))
		{
			return players.get(name);
		}
		return null;
	}

	public boolean removePlayer(String name)
	{
		if (this.exists(name))
		{
			players.remove(name);
			return true;
		}
		return false;
	}
	public boolean exists(String name)
	{
		return players.containsKey(name);
	}
}
