package me.protocos.xteam.command.action;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class SetTeamAction
{
	private CommandSender sender;

	public SetTeamAction(CommandSender sender)
	{
		this.sender = sender;
	}

	public void checkRequirementsOn(String playerName, String teamName) throws TeamException
	{
		ITeamPlayer player = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkPlayerLeaderLeaving(player);
		Requirements.checkPlayerAlreadyOnTeam(player, teamName);
		Requirements.checkTeamPlayerMax(teamName);
	}

	public void actOn(String playerName, String teamName)
	{
		ITeamPlayer p = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		if (p.hasTeam())
		{
			removePlayer(p);
		}
		if (!xTeam.getInstance().getTeamManager().containsTeam(teamName))
		{
			createTeamWithLeader(teamName, p);
		}
		else
		{
			addPlayerToTeam(p, xTeam.getInstance().getTeamManager().getTeam(teamName));
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
		playerTeam.sendMessage(playerName + " has been " + ChatColorUtil.negativeMessage("removed") + " from " + teamName);
		if (playerName.equals(senderName))
		{
			//first person
			sender.sendMessage("You have been " + ChatColorUtil.negativeMessage("removed") + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				xTeam.getInstance().getTeamManager().disbandTeam(teamName);
				sender.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
			}
		}
		else
		{
			//third person
			if (!playerTeam.containsPlayer(senderName))
				sender.sendMessage(playerName + " has been " + ChatColorUtil.negativeMessage("removed") + " from " + teamName);
			player.sendMessage("You have been " + ChatColorUtil.negativeMessage("removed") + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				xTeam.getInstance().getTeamManager().disbandTeam(teamName);
				sender.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
				player.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
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
			sender.sendMessage("You have been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
		}
		else
		{
			//third person
			sender.sendMessage(playerName + " has been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
			player.sendMessage("You have been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
		}
	}

	public void createTeamWithLeader(String teamName, ITeamPlayer player)
	{
		String senderName = sender.getName();
		String playerName = player.getName();
		Team newTeam = Team.createTeamWithLeader(teamName, playerName);
		xTeam.getInstance().getTeamManager().createTeam(newTeam);
		if (playerName.equals(senderName))
		{
			//first person
			sender.sendMessage(teamName + " has been " + ChatColorUtil.positiveMessage("created"));
			sender.sendMessage("You have been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
		}
		else
		{
			//third person
			sender.sendMessage(teamName + " has been " + ChatColorUtil.positiveMessage("created"));
			sender.sendMessage(playerName + " has been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
			player.sendMessage(teamName + " has been " + ChatColorUtil.positiveMessage("created"));
			player.sendMessage("You have been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
		}
	}
}
