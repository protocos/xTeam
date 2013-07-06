package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleDemote extends BaseConsoleCommand
{
	private String teamName, playerName;

	public ConsoleDemote(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.demote(playerName);
		originalSender.sendMessage("You demoted " + playerName);
		TeamPlayer other = new TeamPlayer(playerName);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColor.RED + "demoted");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 3)
		{
			teamName = parseCommand.get(1);
			playerName = parseCommand.get(2);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		TeamPlayer player = new TeamPlayer(playerName);
		Team team = player.getTeam();
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (!player.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (team == null)
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!player.isAdmin())
		{
			throw new TeamPlayerNotAdminException();
		}
		if (!desiredTeam.equals(team))
		{
			throw new TeamPlayerNotOnTeamException();
		}
		if (player.isLeader())
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
	public String getUsage()
	{
		return baseCommand + " demote [Team] [Player]";
	}
}
