package me.protocos.xteam.listener;

import java.util.List;
import java.util.Random;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
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
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;

	public TeamPlayerListener(XTeam xteam)
	{
		this.log = xteam.getLog();
		this.teamCoordinator = xteam.getTeamCoordinator();
		this.playerFactory = xteam.getPlayerFactory();
	}

	@EventHandler
	public void onPlayerJoin(PlayerTeleportEvent event)
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
							int smallest = teamCoordinator.getDefaultTeams().get(0).size();
							for (ITeam t : teamCoordinator.getDefaultTeams())
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
							for (ITeam t : teamCoordinator.getDefaultTeams())
							{
								availableTeams.add(t);
							}
						}
						int index = r.nextInt(availableTeams.size());
						ITeam team = availableTeams.get(index);
						team.addPlayer(teamPlayer.getName());
						new Message.Builder("You " + MessageUtil.green("joined") + " " + team.getName()).addRecipients(teamPlayer).send(log);
						for (ITeamPlayer teammate : teamPlayer.getOnlineTeammates())
						{
							new Message.Builder(teamPlayer.getName() + " " + MessageUtil.green("joined") + " your team").addRecipients(teammate).send(log);
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
						new Message.Builder(MessageUtil.red("You have been teleported to your Headquarters")).addRecipients(teamPlayer).send(log);
					}
					else
					{
						new Message.Builder(MessageUtil.red("Your team does not have an Headquarters")).addRecipients(teamPlayer).send(log);
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
			playerFactory.getPlayer(player).setLastKnownLocation(player.getLocation());
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
						new Message.Builder(MessageUtil.red("You have not set a headquarters yet.")).addRecipients(player).send(log);
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
