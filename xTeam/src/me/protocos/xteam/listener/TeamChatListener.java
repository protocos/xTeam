package me.protocos.xteam.listener;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Data;
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
			ITeamPlayer teamPlayer = xTeam.getPlayerManager().getPlayer(player);
			if (event.isCancelled())
			{
				return;
			}
			if (teamPlayer.hasPlayedBefore() && Data.DISABLED_WORLDS.contains(playerWorld.getName()))
			{
				return;
			}
			if (teamPlayer.hasTeam() && Data.TEAM_TAG_ENABLED)
			{
				Team team = teamPlayer.getTeam();
				String playerName = teamPlayer.getName();
				String teamTag = "[" + teamPlayer.getTeam().getTag() + "]";
				event.setFormat(ChatColorUtil.getColor(Data.TAG_COLOR) + teamTag + ChatColor.RESET + " " + format);
				if (Data.chatStatus.contains(playerName))
				{
					event.setCancelled(true);
					team.sendMessage("[" + ChatColorUtil.getColor(Data.NAME_COLOR) + playerName + ChatColor.RESET + "] " + msg);
					for (String p : Data.spies)
					{
						ITeamPlayer spy = xTeam.getPlayerManager().getPlayer(p);
						if (!spy.isOnSameTeam(teamPlayer))
							spy.sendMessage(ChatColorUtil.getColor(Data.TAG_COLOR) + teamTag + ChatColor.DARK_GRAY + " <" + playerName + "> " + msg);
					}
					xTeam.log.info("[" + playerName + "] " + event.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			xTeam.getLog().exception(e);
			xTeam.log.info("[ERROR] Exception in " + this.getClass().getName() + " class [check logs]");
		}
	}
}
