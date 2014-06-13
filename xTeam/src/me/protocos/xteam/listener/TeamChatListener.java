package me.protocos.xteam.listener;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.ILog;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamChatListener implements Listener
{
	private ILog log;
	private IPlayerFactory playerFactory;

	public TeamChatListener(TeamPlugin teamPlugin)
	{
		this.log = teamPlugin.getLog();
		this.playerFactory = teamPlugin.getPlayerFactory();
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		try
		{
			Player player = event.getPlayer();
			World playerWorld = player.getWorld();
			String msg = event.getMessage();
			String format = event.getFormat();
			ITeamPlayer teamPlayer = playerFactory.getPlayer(player);
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
				event.setFormat(MessageUtil.getColor(Configuration.COLOR_TAG) + teamTag + ChatColor.RESET + " " + format);
				if (Configuration.chatStatus.contains(playerName))
				{
					event.setCancelled(true);
					team.sendMessage("[" + MessageUtil.getColor(Configuration.COLOR_NAME) + playerName + ChatColor.RESET + "] " + msg);
					for (String p : Configuration.spies)
					{
						ITeamPlayer spy = playerFactory.getPlayer(p);
						if (!spy.isOnSameTeam(teamPlayer))
							spy.sendMessage(MessageUtil.getColor(Configuration.COLOR_TAG) + teamTag + ChatColor.DARK_GRAY + " <" + playerName + "> " + msg);
					}
					log.info("[" + playerName + "] " + event.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			log.exception(e);
		}
	}
}
