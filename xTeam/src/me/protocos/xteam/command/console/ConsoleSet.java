package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleSet extends BaseConsoleCommand
{
	private String playerName, teamName;

	public ConsoleSet()
	{
		super();
	}
	public ConsoleSet(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		TeamPlayer player = new TeamPlayer(playerName);
		if (player.hasTeam())
		{
			removePlayer(player);
		}
		if (!xTeam.tm.contains(teamName))
		{
			createTeamWithLeader(teamName, playerName);
		}
		else
		{
			addPlayerToTeam(player, xTeam.tm.getTeam(teamName));
		}
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 3)
		{
			playerName = parseCommand.get(1);
			teamName = parseCommand.get(2);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		TeamPlayer p = new TeamPlayer(playerName);
		if (!p.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (p.hasTeam() && p.isLeader() && p.getTeam().size() > 1)
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
	@Override
	public String getPattern()
	{
		return patternOneOrMore("set") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " set [Player] [Team]";
	}
	private void removePlayer(TeamPlayer player)
	{
		Team team = player.getTeam();
		team.removePlayer(player.getName());
		team.sendMessage(player.getName() + " has been removed from " + team.getName());
		Data.chatStatus.remove(player.getName());
		originalSender.sendMessage(player.getName() + " has been removed from " + team.getName());
		player.sendMessage("You have been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + team.getName());
		if (team.isEmpty() && !team.isDefaultTeam())
		{
			originalSender.sendMessage(team.getName() + " has been disbanded");
			player.sendMessage(team.getName() + " has been " + ChatColor.RED + "disbanded");
			xTeam.tm.removeTeam(team.getName());
		}
	}
	private void createTeamWithLeader(String team, String player)
	{
		xTeam.tm.createTeamWithLeader(team, player);
		originalSender.sendMessage(team + " has been created");
		originalSender.sendMessage(player + " has been added to " + team);
	}
	private void addPlayerToTeam(TeamPlayer player, Team team)
	{
		team.addPlayer(player.getName());
		originalSender.sendMessage(player.getName() + " has been added to " + team.getName());
		player.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + team.getName());
		player.sendMessageToTeam(player.getName() + " has been added to " + team.getName());
	}
}
