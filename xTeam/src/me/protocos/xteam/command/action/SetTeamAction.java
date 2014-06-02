package me.protocos.xteam.command.action;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import org.bukkit.command.CommandSender;

public class SetTeamAction
{
	private TeamPlugin teamPlugin;
	private ITeamManager teamManager;
	private IPlayerFactory playerFactory;
	private CommandSender sender;

	public SetTeamAction(TeamPlugin teamPlugin, CommandSender sender)
	{
		this.teamPlugin = teamPlugin;
		this.teamManager = teamPlugin.getTeamManager();
		this.playerFactory = teamPlugin.getPlayerManager();
		this.sender = sender;
	}

	public void checkRequirementsOn(String playerName, String teamName) throws TeamException
	{
		ITeamPlayer player = playerFactory.getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkPlayerLeaderLeaving(player);
		Requirements.checkPlayerAlreadyOnTeam(player, teamName);
		Requirements.checkTeamPlayerMax(teamManager, teamName);
	}

	public void actOn(String playerName, String teamName)
	{
		ITeamPlayer p = playerFactory.getPlayer(playerName);
		if (p.hasTeam())
		{
			removePlayer(p);
		}
		if (!teamManager.containsTeam(teamName))
		{
			createTeamWithLeader(teamName, p);
		}
		else
		{
			addPlayerToTeam(p, teamManager.getTeam(teamName));
		}
	}

	public void removePlayer(ITeamPlayer player)
	{
		ITeam playerTeam = player.getTeam();
		String teamName = playerTeam.getName();
		String playerName = player.getName();
		String senderName = sender.getName();
		playerTeam.removePlayer(player.getName());
		Configuration.chatStatus.remove(playerName);
		player.removeReturnLocation();
		playerTeam.sendMessage(playerName + " has been " + MessageUtil.negativeMessage("removed") + " from " + teamName);
		if (playerName.equals(senderName))
		{
			//first person
			sender.sendMessage("You have been " + MessageUtil.negativeMessage("removed") + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				teamManager.disbandTeam(teamName);
				sender.sendMessage(teamName + " has been " + MessageUtil.negativeMessage("disbanded"));
			}
		}
		else
		{
			//third person
			if (!playerTeam.containsPlayer(senderName))
				sender.sendMessage(playerName + " has been " + MessageUtil.negativeMessage("removed") + " from " + teamName);
			player.sendMessage("You have been " + MessageUtil.negativeMessage("removed") + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				teamManager.disbandTeam(teamName);
				sender.sendMessage(teamName + " has been " + MessageUtil.negativeMessage("disbanded"));
				player.sendMessage(teamName + " has been " + MessageUtil.negativeMessage("disbanded"));
			}
		}
	}

	public void addPlayerToTeam(ITeamPlayer player, ITeam team)
	{
		String senderName = sender.getName();
		String playerName = player.getName();
		String teamName = team.getName();
		team.addPlayer(playerName);
		if (playerName.equals(senderName))
		{
			//first person
			sender.sendMessage("You have been " + MessageUtil.positiveMessage("added") + " to " + teamName);
		}
		else
		{
			//third person
			sender.sendMessage(playerName + " has been " + MessageUtil.positiveMessage("added") + " to " + teamName);
			player.sendMessage("You have been " + MessageUtil.positiveMessage("added") + " to " + teamName);
		}
	}

	public void createTeamWithLeader(String teamName, ITeamPlayer player)
	{
		String senderName = sender.getName();
		String playerName = player.getName();
		Team newTeam = Team.createTeamWithLeader(teamPlugin, teamName, playerName);
		teamManager.createTeam(newTeam);
		if (playerName.equals(senderName))
		{
			//first person
			sender.sendMessage(teamName + " has been " + MessageUtil.positiveMessage("created"));
			sender.sendMessage("You have been " + MessageUtil.positiveMessage("added") + " to " + teamName);
		}
		else
		{
			//third person
			sender.sendMessage(teamName + " has been " + MessageUtil.positiveMessage("created"));
			sender.sendMessage(playerName + " has been " + MessageUtil.positiveMessage("added") + " to " + teamName);
			player.sendMessage(teamName + " has been " + MessageUtil.positiveMessage("created"));
			player.sendMessage("You have been " + MessageUtil.positiveMessage("added") + " to " + teamName);
		}
	}
}
