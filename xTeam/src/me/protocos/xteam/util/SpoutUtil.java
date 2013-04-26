package me.protocos.xteam.util;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;

public class SpoutUtil
{
	public static void hidePlayerNames(Player[] onlinePlayers)
	{
		for (Player player1 : onlinePlayers)
		{
			for (Player player2 : onlinePlayers)
			{
				SpoutManager.getPlayer(player1).hideTitleFrom(SpoutManager.getPlayer(player2));
			}
		}
	}
}
