package me.protocos.xteam.listener;

import java.util.List;
import java.util.Random;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
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
	private ILog log;
	private ITeamManager teamManager;
	private IPlayerFactory playerFactory;

	public TeamPlayerListener(XTeam xteam)
	{
		this.log = xteam.getLog();
		this.teamManager = xteam.getTeamManager();
		this.playerFactory = xteam.getPlayerManager();
	}

	@EventHandler
	public void onPlayerJoin(@SuppressWarnings("unused") PlayerTeleportEvent event)
	{
		//TODO maybe use this for updateing in the PlayerManager?
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		try
		{
			Player player = event.getPlayer();
			World playerWorld = player.getWorld();
			ITeamPlayer teamPlayer = playerFactory.getPlayer(player);
			if (teamPlayer.hasPlayedBefore() && Configuration.DISABLED_WORLDS.contains(playerWorld.getName()))
			{
				return;
			}
			if (Configuration.RANDOM_TEAM)
			{
				if (!teamPlayer.hasTeam() || !teamPlayer.hasPlayedBefore())
				{
					Random r = new Random();
					if (Configuration.DEFAULT_TEAM_NAMES.size() > 0)
					{
						List<ITeam> availableTeams = CommonUtil.emptyList();
						if (Configuration.BALANCE_TEAMS)
						{
							int smallest = teamManager.getDefaultTeams().get(0).size();
							for (ITeam t : teamManager.getDefaultTeams())
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
							for (ITeam t : teamManager.getDefaultTeams())
							{
								availableTeams.add(t);
							}
						}
						int index = r.nextInt(availableTeams.size());
						ITeam team = availableTeams.get(index);
						team.addPlayer(teamPlayer.getName());
						teamPlayer.sendMessage("You " + MessageUtil.green("joined") + " " + team.getName());
						for (ITeamPlayer teammate : teamPlayer.getOnlineTeammates())
						{
							teammate.sendMessage(teamPlayer.getName() + " " + MessageUtil.green("joined") + " your team");
						}
						log.info("Added " + teamPlayer.getName() + " to team " + team.getName());
					}
					else
					{
						log.info(MessageUtil.red("Player not assigned a team: No default teams have been set"));
					}
				}
			}
			if (Configuration.DEFAULT_HQ_ON_JOIN)
			{
				if (teamPlayer.hasTeam() && teamPlayer.getTeam().isDefaultTeam())
				{
					ITeam team = teamPlayer.getTeam();
					if (team.hasHeadquarters())
					{
						teamPlayer.teleportTo(team.getHeadquarters());
						teamPlayer.sendMessage(MessageUtil.red("You've been teleported to your Headquarters"));
					}
					else
					{
						teamPlayer.sendMessage(MessageUtil.red("Your team does not have an Headquarters"));
					}
				}
			}
		}
		catch (Exception e)
		{
			log.exception(e);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		try
		{
			Configuration.chatStatus.remove(player.getName());
		}
		catch (Exception e)
		{
			log.exception(e);
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		try
		{
			ITeamPlayer player = playerFactory.getPlayer(event.getPlayer());
			if (Configuration.DISABLED_WORLDS.contains(event.getPlayer().getWorld().getName()))
			{
				return;
			}
			if (Configuration.HQ_ON_DEATH)
			{
				if (player.hasTeam())
				{
					if (player.getTeam().hasHeadquarters())
					{
						event.setRespawnLocation(player.getTeam().getHeadquarters().getLocation());
					}
					else
					{
						player.sendMessage(MessageUtil.red("You have not set a headquarters yet."));
					}
				}
			}
		}
		catch (Exception e)
		{
			log.exception(e);
		}
	}
}
