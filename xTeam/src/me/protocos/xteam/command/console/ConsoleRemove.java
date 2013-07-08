package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleRemove extends ConsoleCommand
{
	private String teamName, playerName;

	public ConsoleRemove()
	{
	}
	public ConsoleRemove(ConsoleCommandSender sender, CommandParser command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		TeamPlayer p = new TeamPlayer(playerName);
		Team team = p.getTeam();
		team.removePlayer(playerName);
		originalSender.sendMessage("You" + ChatColor.RED + " removed " + ChatColor.RESET + playerName + " from " + teamName);
		if (p.isOnline())
			p.sendMessage("You've been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + team.getName());
		if (team.isEmpty())
		{
			originalSender.sendMessage(teamName + " has been disbanded");
			xTeam.tm.removeTeam(team.getName());
		}
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
		TeamPlayer p = new TeamPlayer(playerName);
		Team team = p.getTeam();
		if (!p.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (team == null)
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (team.getLeader().equals(playerName) && team.getPlayers().size() > 1)
		{
			throw new TeamPlayerLeaderLeavingException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("re") + patternOneOrMore("move") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return parseCommand.getBaseCommand() + " remove [Team] [Player]";
	}
}
