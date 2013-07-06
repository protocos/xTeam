package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminSet extends BaseServerAdminCommand
{
	private String playerName, teamName;

	public AdminSet(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
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
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (player == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
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
		Team team = p.getTeam();
		if (!p.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (team != null && team.getLeader().equals(playerName) && team.getPlayers().size() > 1)
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
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.set";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " set [Player] [Team]";
	}
	private void removePlayer(TeamPlayer p)
	{
		Team team = p.getTeam();
		team.removePlayer(p.getName());
		team.sendMessage(p.getName() + " has been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + team.getName() + " by an admin", player);
		Data.chatStatus.remove(p.getName());
		Data.returnLocations.remove(p.getOnlinePlayer());
		player.sendMessage(p.getName() + " has been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + team.getName());
		if (!p.getName().equals(player.getName()))
			p.sendMessage("You have been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + team.getName() + " by an admin");
		if (team.isEmpty() && !team.isDefaultTeam())
		{
			player.sendMessage(team.getName() + " has been " + ChatColor.RED + "disbanded");
			if (!p.getName().equals(player.getName()))
				p.sendMessage(team.getName() + " has been " + ChatColor.RED + "disbanded" + ChatColor.RESET + " by an admin");
			xTeam.tm.removeTeam(team.getName());
		}
	}
	private void createTeamWithLeader(String team, String p)
	{
		xTeam.tm.createTeamWithLeader(team, p);
		Team t = xTeam.tm.getTeam(team);
		player.sendMessage(team + " has been " + ChatColor.AQUA + "created");
		player.sendMessage(p + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + team);
		if (!p.equals(player.getName()))
		{
			t.sendMessage(team + " has been " + ChatColor.AQUA + "created" + ChatColor.RESET + " by an admin");
			t.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + team + " by an admin");
		}
	}
	private void addPlayerToTeam(TeamPlayer p, Team team)
	{
		team.addPlayer(p.getName());
		player.sendMessage(p.getName() + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + team.getName());
		if (!p.getName().equals(player.getName()))
		{
			p.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + team.getName() + " by an admin");
			p.sendMessageToTeam(p.getName() + " has been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + team.getName() + " by an admin", player);
		}
	}
}
