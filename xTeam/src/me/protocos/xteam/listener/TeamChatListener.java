package me.protocos.xteam.listener;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.ITeamPlayer;
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
		Player player = event.getPlayer();
		String msg = event.getMessage();
		String format = event.getFormat();
		TeamPlayer teamPlayer = new TeamPlayer(player);
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
			String teamTag = "[" + teamPlayer.getTeam().getTag() + "]";
			String teamPlayerName = teamPlayer.getName();
			event.setFormat(ColorUtil.getColor(Data.TAG_COLOR) + teamTag + ChatColor.RESET + " " + format);
			if (Data.chatStatus.contains(teamPlayerName))
			{
				event.setCancelled(true);
				List<String> onlineTeammates = teamPlayer.getOnlineTeammates();
				teamPlayer.sendMessage("[" + ColorUtil.getColor(Data.NAME_COLOR) + teamPlayerName + ChatColor.RESET + "] " + msg);
				for (String teammate : onlineTeammates)
				{
					ITeamPlayer p = new TeamPlayer(teammate);
					p.sendMessage("[" + ColorUtil.getColor(Data.NAME_COLOR) + teamPlayerName + ChatColor.RESET + "] " + msg);
				}
				for (String p : Data.spies)
				{
					TeamPlayer spy = new TeamPlayer(p);
					if (!spy.isOnSameTeam(teamPlayer))
						spy.sendMessage(ChatColor.RED + "[" + teamTag + "] " + ChatColor.RESET + "<" + teamPlayerName + "> " + msg);
				}
				xTeam.log.info("[" + teamPlayerName + "] " + event.getMessage());
			}
		}
	}
}
