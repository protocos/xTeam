package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleSetLeader extends BaseConsoleCommand
{
	private String teamName, playerName;

	public ConsoleSetLeader()
	{
		super();
	}
	public ConsoleSetLeader(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		TeamPlayer player = new TeamPlayer(playerName);
		Team team = player.getTeam();
		team.setLeader(playerName);
		if (player.isOnline())
			player.sendMessage(ChatColor.GREEN + "You" + ChatColor.WHITE + " are now the team leader");
		if (!team.isDefaultTeam())
		{
			TeamPlayer previousLeader = new TeamPlayer(team.getLeader());
			if (previousLeader.isOnline())
				previousLeader.sendMessage(ChatColor.GREEN + playerName + ChatColor.WHITE + " is now the team leader");
		}
		originalSender.sendMessage(playerName + " is now the team leader for " + team.getName());
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
		if (!player.hasPlayedBefore())
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
		if (!desiredTeam.equals(team))
		{
			throw new TeamPlayerNotOnTeamException();
		}
		if (team.isDefaultTeam())
		{
			throw new TeamIsDefaultException();
		}
	}
	@Override
	public String getPattern()
	{
		return "set" + patternOneOrMore("leader") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " setleader [Team] [Player]";
	}
}