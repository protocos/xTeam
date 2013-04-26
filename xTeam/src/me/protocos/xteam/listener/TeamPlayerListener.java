package me.protocos.xteam.listener;

import java.util.ArrayList;
import java.util.Random;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.*;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class TeamPlayerListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		ITeamPlayer player = new TeamPlayer(event.getPlayer());
		if (player.hasPlayedBefore() && Data.DISABLED_WORLDS.contains(player.getWorld().getName()))
		{
			return;
		}
		else if (Data.RANDOM_TEAM)
		{
			if (!player.hasTeam() || !player.hasPlayedBefore())
			{
				Random r = new Random();
				if (Data.DEFAULT_TEAM_NAMES.size() > 0)
				{
					Team team;
					ArrayList<Team> availableTeams = new ArrayList<Team>();
					for (Team t : xTeam.tm.getDefaultTeams())
					{
						// IF (trying to balance teams) && (availableTeams is not empty) && (the current team has fewer players that any team in availableTeams)
						if (Data.BALANCE_TEAMS)
						{
							if (!availableTeams.isEmpty())
							{
								if (t.size() < availableTeams.get(0).size())
								{
									availableTeams.clear();
									availableTeams.add(t);
								}
								else if (t.size() == availableTeams.get(0).size())
								{
									availableTeams.add(t);
								}
								else if (t.size() > availableTeams.get(0).size())
								{

								}
							}
							else
							{
								availableTeams.add(t);
							}
						}
					}
					int index = r.nextInt(availableTeams.size());
					team = availableTeams.get(index);
					team.addPlayer(player.getName());
					player.sendMessage("You joined " + ChatColor.AQUA + team.getName());
					for (String p : player.getOnlineTeammates())
					{
						ITeamPlayer teammate = new TeamPlayer(p);
						if (teammate.isOnline())
							teammate.sendMessage(player.getName() + ChatColor.AQUA + " joined your team");
					}
					xTeam.log.info("Added " + player.getName() + " to team " + team.getName());
				}
				else
				{
					xTeam.log.info(ChatColor.RED + "No default teams have been set");
				}
			}
		}
		if (Data.DEFAULT_HQ_ON_JOIN)
		{
			if (player.hasTeam() && player.getTeam().isDefaultTeam())
			{
				Team team = player.getTeam();
				if (team.hasHQ())
				{
					player.teleport(team.getHeadquarters());
					player.sendMessage(ChatColor.RED + "You've been teleported to your Headquarters");
				}
				else
				{
					player.sendMessage(ChatColor.RED + "Your team does not have an Headquarters");
				}
			}
		}
		int seconds = 5;
		Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
		{
			@Override
			public void run()
			{
				Functions.updatePlayers();
			}
		}, seconds * 20L);
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Data.chatStatus.remove(event.getPlayer().getName());
	}
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		ITeamPlayer player = new TeamPlayer(event.getPlayer());
		if (Data.DISABLED_WORLDS.contains(event.getPlayer().getWorld().getName()))
		{
			return;
		}
		if (Data.HQ_ON_DEATH)
		{
			if (player.hasTeam())
			{
				if (player.getTeam().hasHQ())
				{
					event.setRespawnLocation(player.getTeam().getHeadquarters());
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You have not set an Headquarters yet.");
				}
			}
		}
		int seconds = 3;
		if (Data.SPOUT_ENABLED)
		{
			Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
			{
				@Override
				public void run()
				{
					Functions.updatePlayers();
				}
			}, seconds * 20L);
		}
	}
}
