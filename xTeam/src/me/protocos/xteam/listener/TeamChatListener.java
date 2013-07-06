package me.protocos.xteam.listener;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.util.ColorUtil;
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
			TeamPlayer teamPlayer = new TeamPlayer(player);
			String msg = event.getMessage();
			String format = event.getFormat();
			World teamPlayerWorld = teamPlayer.getWorld();
			if (event.isCancelled())
			{
				return;
			}
			if (teamPlayer.hasPlayedBefore() && Data.DISABLED_WORLDS.contains(teamPlayerWorld.getName()))
			{
				return;
			}
			if (teamPlayer.hasTeam() && Data.TEAM_TAG_ENABLED)
			{
				Team team = teamPlayer.getTeam();
				String playerName = teamPlayer.getName();
				String teamTag = "[" + teamPlayer.getTeam().getTag() + "]";
				event.setFormat(ColorUtil.getColor(Data.TAG_COLOR) + teamTag + ChatColor.RESET + " " + format);
				if (Data.chatStatus.contains(playerName))
				{
					event.setCancelled(true);
					team.sendMessage("[" + ColorUtil.getColor(Data.NAME_COLOR) + playerName + ChatColor.RESET + "] " + msg);
					for (String p : Data.spies)
					{
						TeamPlayer spy = new TeamPlayer(p);
						if (!spy.isOnSameTeam(teamPlayer))
							spy.sendMessage(ColorUtil.getColor(Data.TAG_COLOR) + teamTag + ChatColor.DARK_GRAY + " <" + playerName + "> " + msg);
					}
					xTeam.log.info("[" + playerName + "] " + event.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			xTeam.logger.exception(e);
			xTeam.log.info("[ERROR] Exception in xTeam onPlayerChat() class [check logs]");
		}
	}
}
