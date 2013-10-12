package me.protocos.xteam.command.action;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SetTeamAction
{
	private CommandSender originalSender;

	public SetTeamAction(CommandSender originalSender)
	{
		this.originalSender = originalSender;
	}

	public void checkRequorementsOn(String playerName, String teamName) throws TeamException
	{
		TeamPlayer p = new TeamPlayer(playerName);
		Team playerTeam = p.getTeam();
		if (!p.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (p.hasTeam() && p.isLeader() && playerTeam.size() > 1)
		{
			throw new TeamPlayerLeaderLeavingException();
		}
		if (p.hasTeam() && p.getTeam().getName().equalsIgnoreCase(teamName))
		{
			throw new TeamPlayerAlreadyOnTeamException();
		}
		if (xTeam.tm.contains(teamName) && xTeam.tm.getTeam(teamName).size() >= Data.MAX_PLAYERS && Data.MAX_PLAYERS > 0)
		{
			throw new TeamPlayerMaxException();
		}
	}

	public void actOn(String playerName, String teamName)
	{
		TeamPlayer p = new TeamPlayer(playerName);
		if (p.hasTeam())
		{
			removePlayer(p);
		}
		if (!xTeam.tm.contains(teamName))
		{
			createTeamWithLeader(teamName, p);
		}
		else
		{
			addPlayerToTeam(p, xTeam.tm.getTeam(teamName));
		}
	}

	public void removePlayer(TeamPlayer player)
	{
		Team playerTeam = player.getTeam();
		String teamName = playerTeam.getName();
		String playerName = player.getName();
		String senderName = originalSender.getName();
		playerTeam.removePlayer(player.getName());
		Data.chatStatus.remove(playerName);
		Data.returnLocations.remove(player.getOnlinePlayer());
		playerTeam.sendMessage(playerName + " has been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + teamName);
		if (playerName.equals(senderName))
		{
			//first person
			originalSender.sendMessage("You have been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				xTeam.tm.removeTeam(teamName);
				originalSender.sendMessage(teamName + " has been " + ChatColor.RED + "disbanded");
			}
		}
		else
		{
			//third person
			originalSender.sendMessage(playerName + " has been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + teamName);
			player.sendMessage("You have been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + teamName);
			if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
			{
				xTeam.tm.removeTeam(teamName);
				originalSender.sendMessage(teamName + " has been " + ChatColor.RED + "disbanded");
				player.sendMessage(teamName + " has been " + ChatColor.RED + "disbanded");
			}
		}
	}

	public void addPlayerToTeam(TeamPlayer player, Team changeTeam)
	{
		String senderName = originalSender.getName();
		String playerName = player.getName();
		String teamName = changeTeam.getName();
		changeTeam.addPlayer(playerName);
		if (playerName.equals(senderName))
		{
			//first person
			originalSender.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + teamName);
		}
		else
		{
			//third person
			originalSender.sendMessage(playerName + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + teamName);
			player.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + teamName);
		}
		player.sendMessageToTeam(playerName + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + teamName);
	}

	public void createTeamWithLeader(String teamName, TeamPlayer player)
	{
		String senderName = originalSender.getName();
		String playerName = player.getName();
		xTeam.tm.createTeamWithLeader(teamName, playerName);
		if (playerName.equals(senderName))
		{
			//first person
			originalSender.sendMessage(teamName + " has been " + ChatColor.AQUA + "created");
			originalSender.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + teamName);
		}
		else
		{
			//third person
			originalSender.sendMessage(teamName + " has been " + ChatColor.AQUA + "created");
			originalSender.sendMessage(playerName + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + teamName);
			player.sendMessage(teamName + " has been " + ChatColor.AQUA + "created");
			player.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + teamName);
		}
	}
}
