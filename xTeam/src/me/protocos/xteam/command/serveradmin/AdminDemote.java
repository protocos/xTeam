package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminDemote extends ServerAdminCommand
{
	private String teamName, playerName;

	public AdminDemote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.demote(playerName);
		if (!team.containsPlayer(originalSender.getName()))
			originalSender.sendMessage("You " + ChatColor.RED + "demoted" + ChatColor.RESET + " " + playerName);
		TeamPlayer other = new TeamPlayer(playerName);
		other.sendMessage("You have been " + ChatColor.RED + "demoted" + ChatColor.RESET + " by an admin");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		TeamPlayer playerDemote = new TeamPlayer(playerName);
		Team team = playerDemote.getTeam();
		if (!playerDemote.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (team == null)
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!playerDemote.isAdmin())
		{
			throw new TeamPlayerNotAdminException();
		}
		if (!desiredTeam.equals(team))
		{
			throw new TeamPlayerNotOnTeamException();
		}
		if (playerDemote.isLeader())
		{
			throw new TeamPlayerLeaderDemoteException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("demote") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.demote";
	}
	@Override
	public String getUsage()
	{
		return "/team demote [Team] [Player]";
	}
}
