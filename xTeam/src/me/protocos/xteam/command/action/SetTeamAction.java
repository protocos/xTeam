package me.protocos.xteam.command.action;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
import org.bukkit.command.CommandSender;

public class SetTeamAction
{
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;

	public SetTeamAction(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.playerFactory = teamPlugin.getPlayerFactory();
	}

	public void checkRequirementsOn(String playerName, String teamName) throws TeamException
	{
		ITeamPlayer player = playerFactory.getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkPlayerLeaderLeaving(player);
		Requirements.checkPlayerAlreadyOnTeam(player, teamName);
		Requirements.checkTeamPlayerMax(teamCoordinator, teamName);
	}

	public void actOn(CommandSender sender, String playerName, String teamName)
	{
		ITeamPlayer p = playerFactory.getPlayer(playerName);
		if (p.hasTeam())
		{
			removePlayer(sender, p);
		}
		if (!teamCoordinator.containsTeam(teamName))
		{
			createTeamWithLeader(sender, teamName, p);
		}
		else
		{
			addPlayerToTeam(sender, p, teamCoordinator.getTeam(teamName));
		}
	}

	public void removePlayer(CommandSender sender, ITeamPlayer player)
	{
		ITeam playerTeam = player.getTeam();
		String teamName = playerTeam.getName();
		String playerName = player.getName();
		String senderName = sender.getName();
		playerTeam.removePlayer(player.getName());
		Configuration.chatStatus.remove(playerName);
		player.removeReturnLocation();
		playerTeam.sendMessage(playerName + " has been " + MessageUtil.gold("removed") + " from " + teamName);
		if (playerName.equals(senderName))
		{
			//first person
			sender.sendMessage("You have been " + MessageUtil.gold("removed") + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				teamCoordinator.disbandTeam(teamName);
				sender.sendMessage(teamName + " has been " + MessageUtil.gold("disbanded"));
			}
		}
		else
		{
			//third person
			if (!playerTeam.containsPlayer(senderName))
				sender.sendMessage(playerName + " has been " + MessageUtil.gold("removed") + " from " + teamName);
			player.sendMessage("You have been " + MessageUtil.gold("removed") + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				teamCoordinator.disbandTeam(teamName);
				sender.sendMessage(teamName + " has been " + MessageUtil.gold("disbanded"));
				player.sendMessage(teamName + " has been " + MessageUtil.gold("disbanded"));
			}
		}
	}

	public void addPlayerToTeam(CommandSender sender, ITeamPlayer player, ITeam team)
	{
		String senderName = sender.getName();
		String playerName = player.getName();
		String teamName = team.getName();
		team.addPlayer(playerName);
		if (playerName.equals(senderName))
		{
			//first person
			sender.sendMessage("You have been " + MessageUtil.green("added") + " to " + teamName);
		}
		else
		{
			//third person
			sender.sendMessage(playerName + " has been " + MessageUtil.green("added") + " to " + teamName);
			player.sendMessage("You have been " + MessageUtil.green("added") + " to " + teamName);
		}
	}

	public void createTeamWithLeader(CommandSender sender, String teamName, ITeamPlayer player)
	{
		String senderName = sender.getName();
		String playerName = player.getName();
		Team newTeam = Team.createTeamWithLeader(teamPlugin, teamName, playerName);
		teamCoordinator.createTeam(newTeam);
		if (playerName.equals(senderName))
		{
			//first person
			sender.sendMessage(teamName + " has been " + MessageUtil.green("created"));
			sender.sendMessage("You have been " + MessageUtil.green("added") + " to " + teamName);
		}
		else
		{
			//third person
			sender.sendMessage(teamName + " has been " + MessageUtil.green("created"));
			sender.sendMessage(playerName + " has been " + MessageUtil.green("added") + " to " + teamName);
			player.sendMessage(teamName + " has been " + MessageUtil.green("created"));
			player.sendMessage("You have been " + MessageUtil.green("added") + " to " + teamName);
		}
	}
}
