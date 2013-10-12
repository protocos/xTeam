package me.protocos.xteam.core;

import me.protocos.xteam.api.collections.HashList;

public class TeamPlayerManager
{
	HashList<String, TeamPlayer> players;

	public TeamPlayerManager()
	{
		players = new HashList<String, TeamPlayer>();
	}
}
