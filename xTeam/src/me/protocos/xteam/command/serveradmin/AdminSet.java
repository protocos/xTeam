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

	public AdminSet()
	{
		super();
	}
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
		team.removePlayer(playerName);
		Data.chatStatus.remove(playerName);
		originalSender.sendMessage(playerName + " has been removed from " + team.getName());
		p.sendMessage("You have been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + team.getName());
		team.sendMessageToTeam(playerName + " has been removed from " + team.getName());
		if (team.isEmpty())
		{
			originalSender.sendMessage(team.getName() + " has been disbanded");
			p.sendMessage(team.getName() + " has been " + ChatColor.RED + "disbanded");
			xTeam.tm.removeTeam(team.getName());
		}
	}
	private void createTeamWithLeader(String team, String p)
	{
		xTeam.tm.createTeamWithLeader(team, p);
		originalSender.sendMessage(team + " has been created");
		originalSender.sendMessage(p + " has been added to " + team);
	}
	private void addPlayerToTeam(TeamPlayer p, Team team)
	{
		team.addPlayer(playerName);
		originalSender.sendMessage(playerName + " has been added to " + team.getName());
		p.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.RESET + " to " + team.getName());
		team.sendMessageToTeam(playerName + " has been added to " + team.getName());
	}
}
