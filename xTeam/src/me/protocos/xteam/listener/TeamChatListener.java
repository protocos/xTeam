package me.protocos.xteam.listener;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
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
				ITeam team = teamPlayer.getTeam();
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
		//		printEvent(event);
	}

	public void printEvent(AsyncPlayerChatEvent event)
	{
		System.out.println("===========================Event Information===========================");
		System.out.println("Event Name:\t" + event.getEventName());
		System.out.println("Event Format:\t" + event.getFormat());
		System.out.println("Event Message:\t" + event.getMessage());
		System.out.println("Event Handlers:\t" + event.getHandlers());
		System.out.println("Event Player:\t" + event.getPlayer());
		System.out.println("Event Recipients:\t" + event.getRecipients());
		System.out.println("Event:\t" + event);
		System.out.println("=======================================================================");
	}
}
