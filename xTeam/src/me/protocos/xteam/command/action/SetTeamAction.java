package me.protocos.xteam.command.action;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class SetTeamAction
{
	private CommandSender originalSender;

	public SetTeamAction(CommandSender originalSender)
	{
		this.originalSender = originalSender;
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
		if (!xTeam.getInstance().getTeamManager().contains(teamName))
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
		Team playerTeam = player.getTeam();
		String teamName = playerTeam.getName();
		String playerName = player.getName();
		String senderName = originalSender.getName();
		playerTeam.removePlayer(player.getName());
		Configuration.chatStatus.remove(playerName);
		player.removeReturnLocation();
		playerTeam.sendMessage(playerName + " has been " + ChatColorUtil.negativeMessage("removed") + " from " + teamName);
		if (playerName.equals(senderName))
		{
			//first person
			originalSender.sendMessage("You have been " + ChatColorUtil.negativeMessage("removed") + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				xTeam.getInstance().getTeamManager().removeTeam(teamName);
				originalSender.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
			}
		}
		else
		{
			//third person
			originalSender.sendMessage(playerName + " has been " + ChatColorUtil.negativeMessage("removed") + " from " + teamName);
			player.sendMessage("You have been " + ChatColorUtil.negativeMessage("removed") + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				xTeam.getInstance().getTeamManager().removeTeam(teamName);
				originalSender.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
				player.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
			}
		}
	}

	public void addPlayerToTeam(ITeamPlayer player, Team changeTeam)
	{
		String senderName = originalSender.getName();
		String playerName = player.getName();
		String teamName = changeTeam.getName();
		changeTeam.addPlayer(playerName);
		if (playerName.equals(senderName))
		{
			//first person
			originalSender.sendMessage("You have been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
		}
		else
		{
			//third person
			originalSender.sendMessage(playerName + " has been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
			player.sendMessage("You have been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
		}
	}

	public void createTeamWithLeader(String teamName, ITeamPlayer player)
	{
		String senderName = originalSender.getName();
		String playerName = player.getName();
		Team newTeam = Team.createTeamWithLeader(teamName, playerName);
		xTeam.getInstance().getTeamManager().addTeam(newTeam);
		if (playerName.equals(senderName))
		{
			//first person
			originalSender.sendMessage(teamName + " has been " + ChatColorUtil.positiveMessage("created"));
			originalSender.sendMessage("You have been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
		}
		else
		{
			//third person
			originalSender.sendMessage(teamName + " has been " + ChatColorUtil.positiveMessage("created"));
			originalSender.sendMessage(playerName + " has been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
			player.sendMessage(teamName + " has been " + ChatColorUtil.positiveMessage("created"));
			player.sendMessage("You have been " + ChatColorUtil.positiveMessage("added") + " to " + teamName);
		}
	}
}
