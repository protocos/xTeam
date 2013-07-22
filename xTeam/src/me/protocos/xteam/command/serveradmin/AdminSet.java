package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminSet extends ServerAdminCommand
{
	private String playerName, teamName;
	private Player sender;

	public AdminSet()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeamPlayer p = new TeamPlayer(playerName);
		if (p.hasTeam())
		{
			removePlayer(p);
		}
		if (!xTeam.tm.contains(teamName))
		{
			createTeamWithLeader(teamName, playerName);
		}
		else
		{
			addPlayerToTeam(p, xTeam.tm.getTeam(teamName));
		}
	}
	private void addPlayerToTeam(TeamPlayer p, Team changeTeam)
	{
		changeTeam.addPlayer(p.getName());
		sender.sendMessage(p.getName() + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + changeTeam.getName());
		if (!p.getName().equals(sender.getName()))
		{
			p.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + changeTeam.getName() + " by an admin");
			p.sendMessageToTeam(p.getName() + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + changeTeam.getName() + " by an admin", sender);
		}
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		sender = (Player) originalSender;
		playerName = parseCommand.get(1);
		teamName = parseCommand.get(2);
		TeamPlayer p = new TeamPlayer(playerName);
		Team playerTeam = p.getTeam();
		if (!p.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (playerTeam != null && playerTeam.getLeader().equals(playerName) && playerTeam.getPlayers().size() > 1)
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
	private void createTeamWithLeader(String newTeamName, String p)
	{
		xTeam.tm.createTeamWithLeader(newTeamName, p);
		Team t = xTeam.tm.getTeam(newTeamName);
		sender.sendMessage(newTeamName + " has been " + ChatColor.AQUA + "created");
		sender.sendMessage(p + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + newTeamName);
		if (!p.equals(sender.getName()))
		{
			t.sendMessage(newTeamName + " has been " + ChatColor.AQUA + "created" + ChatColor.RESET + " by an admin");
			t.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + newTeamName + " by an admin");
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("set") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.set";
	}
	@Override
	public String getUsage()
	{
		return "/team set [Player] [Team]";
	}
	private void removePlayer(TeamPlayer p)
	{
		Team playerTeam = p.getTeam();
		playerTeam.removePlayer(p.getName());
		playerTeam.sendMessage(p.getName() + " has been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + playerTeam.getName() + " by an admin", sender);
		Data.chatStatus.remove(p.getName());
		Data.returnLocations.remove(p.getOnlinePlayer());
		sender.sendMessage(p.getName() + " has been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + playerTeam.getName());
		if (!p.getName().equals(sender.getName()))
			p.sendMessage("You have been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + playerTeam.getName() + " by an admin");
		if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
		{
			sender.sendMessage(playerTeam.getName() + " has been " + ChatColor.RED + "disbanded");
			if (!p.getName().equals(sender.getName()))
				p.sendMessage(playerTeam.getName() + " has been " + ChatColor.RED + "disbanded" + ChatColor.RESET + " by an admin");
			xTeam.tm.removeTeam(playerTeam.getName());
		}
	}
}
