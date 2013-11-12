package me.protocos.xteam.listener;

import java.util.ArrayList;
import java.util.Random;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeamPlayerListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(@SuppressWarnings("unused") PlayerTeleportEvent event)
	{
		//TODO maybe use this for updateing in the PLayerManager?
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		try
		{
			Player player = event.getPlayer();
			World playerWorld = player.getWorld();
			ITeamPlayer teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(player);
			if (teamPlayer.hasPlayedBefore() && Data.DISABLED_WORLDS.contains(playerWorld.getName()))
			{
				return;
			}
			if (Data.RANDOM_TEAM)
			{
				if (!teamPlayer.hasTeam() || !teamPlayer.hasPlayedBefore())
				{
					Random r = new Random();
					if (Data.DEFAULT_TEAM_NAMES.size() > 0)
					{
						ArrayList<Team> availableTeams = new ArrayList<Team>();
						if (Data.BALANCE_TEAMS)
						{
							int smallest = xTeam.getInstance().getTeamManager().getDefaultTeams().get(0).size();
							for (Team t : xTeam.getInstance().getTeamManager().getDefaultTeams())
							{
								if (t.size() < smallest)
								{
									availableTeams.clear();
									smallest = t.size();
									availableTeams.add(t);
								}
								else if (t.size() == smallest)
								{
									availableTeams.add(t);
								}
							}
						}
						else
						{
							for (Team t : xTeam.getInstance().getTeamManager().getDefaultTeams())
							{
								availableTeams.add(t);
							}
						}
						int index = r.nextInt(availableTeams.size());
						Team team = availableTeams.get(index);
						team.addPlayer(teamPlayer.getName());
						teamPlayer.sendMessage("You " + ChatColorUtil.positiveMessage("joined") + " " + team.getName());
						for (ITeamPlayer teammate : teamPlayer.getOnlineTeammates())
						{
							teammate.sendMessage(teamPlayer.getName() + " " + ChatColorUtil.positiveMessage("joined") + " your team");
						}
						xTeam.getInstance().getLog().info("Added " + teamPlayer.getName() + " to team " + team.getName());
					}
					else
					{
						xTeam.getInstance().getLog().info(ChatColorUtil.negativeMessage("Player not assigned a team: No default teams have been set"));
					}
				}
			}
			if (Data.DEFAULT_HQ_ON_JOIN)
			{
				if (teamPlayer.hasTeam() && teamPlayer.getTeam().isDefaultTeam())
				{
					Team team = teamPlayer.getTeam();
					if (team.hasHeadquarters())
					{
						teamPlayer.teleportTo(team.getHeadquarters());
						teamPlayer.sendMessage(ChatColorUtil.negativeMessage("You've been teleported to your Headquarters"));
					}
					else
					{
						teamPlayer.sendMessage(ChatColorUtil.negativeMessage("Your team does not have an Headquarters"));
					}
				}
			}
		}
		catch (Exception e)
		{
			xTeam.getInstance().getLog().exception(e);
			xTeam.getInstance().getLog().info("[ERROR] Exception in xTeam onPlayerJoin() class [check logs]");
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		try
		{
			Data.chatStatus.remove(player.getName());
		}
		catch (Exception e)
		{
			xTeam.getInstance().getLog().exception(e);
			xTeam.getInstance().getLog().info("[ERROR] Exception in xTeam onPlayerQuit() class [check logs]");
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		try
		{
			ITeamPlayer player = xTeam.getInstance().getPlayerManager().getPlayer(event.getPlayer());
			if (Data.DISABLED_WORLDS.contains(event.getPlayer().getWorld().getName()))
			{
				return;
			}
			if (Data.HQ_ON_DEATH)
			{
				if (player.hasTeam())
				{
					if (player.getTeam().hasHeadquarters())
					{
						event.setRespawnLocation(player.getTeam().getHeadquarters().getLocation());
					}
					else
					{
						player.sendMessage(ChatColorUtil.negativeMessage("You have not set a headquarters yet."));
					}
				}
			}
		}
		catch (Exception e)
		{
			xTeam.getInstance().getLog().exception(e);
			xTeam.getInstance().getLog().info("[ERROR] Exception in xTeam onPlayerRespawn() class [check logs]");
		}
	}
}
