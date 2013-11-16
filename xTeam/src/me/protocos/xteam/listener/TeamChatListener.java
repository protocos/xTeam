package me.protocos.xteam.listener;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamChatListener implements Listener
{
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		try
		{
			Player player = event.getPlayer();
			World playerWorld = player.getWorld();
			String msg = event.getMessage();
			String format = event.getFormat();
			ITeamPlayer teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(player);
			if (event.isCancelled())
			{
				return;
			}
			if (Configuration.DISABLED_WORLDS.contains(playerWorld.getName()))
			{
				return;
			}
			if (teamPlayer.hasTeam() && Configuration.TEAM_TAG_ENABLED)
			{
				Team team = teamPlayer.getTeam();
				String playerName = teamPlayer.getName();
				String teamTag = "[" + teamPlayer.getTeam().getTag() + "]";
				event.setFormat(ChatColorUtil.getColor(Configuration.COLOR_TAG) + teamTag + ChatColor.RESET + " " + format);
				if (Configuration.chatStatus.contains(playerName))
				{
					event.setCancelled(true);
					team.sendMessage("[" + ChatColorUtil.getColor(Configuration.COLOR_NAME) + playerName + ChatColor.RESET + "] " + msg);
					for (String p : Configuration.spies)
					{
						ITeamPlayer spy = xTeam.getInstance().getPlayerManager().getPlayer(p);
						if (!spy.isOnSameTeam(teamPlayer))
							spy.sendMessage(ChatColorUtil.getColor(Configuration.COLOR_TAG) + teamTag + ChatColor.DARK_GRAY + " <" + playerName + "> " + msg);
					}
					xTeam.getInstance().getLog().info("[" + playerName + "] " + event.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
	}
}
