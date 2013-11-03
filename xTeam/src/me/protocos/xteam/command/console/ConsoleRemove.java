package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.core.exception.TeamPlayerNeverPlayedException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ConsoleRemove extends ConsoleCommand
{
	private String teamName, playerName;
	private ITeamPlayer changeTeamPlayer;
	private Team changeTeam;

	public ConsoleRemove()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.removePlayer(playerName);
		originalSender.sendMessage("You" + ChatColor.RED + " removed " + ChatColor.RESET + playerName + " from " + teamName);
		if (changeTeamPlayer.isOnline())
			changeTeamPlayer.sendMessage("You've been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + changeTeam.getName());
		if (changeTeam.isEmpty())
		{
			originalSender.sendMessage(teamName + " has been disbanded");
			xTeam.getTeamManager().removeTeam(changeTeam.getName());
		}
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changeTeamPlayer = PlayerManager.getPlayer(playerName);
		changeTeam = changeTeamPlayer.getTeam();
		if (!changeTeamPlayer.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (changeTeam == null)
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (changeTeam.getLeader().equals(playerName) && changeTeam.getPlayers().size() > 1)
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
		return "/team remove [Team] [Player]";
	}
}
