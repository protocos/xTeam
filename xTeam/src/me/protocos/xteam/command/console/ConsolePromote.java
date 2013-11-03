package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ConsolePromote extends ConsoleCommand
{
	private String teamName, playerName;

	public ConsolePromote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.getTeamManager().getTeam(teamName);
		team.promote(playerName);
		originalSender.sendMessage("You promoted " + playerName);
		ITeamPlayer other = PlayerManager.getPlayer(playerName);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColor.GREEN + "promoted");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		ITeamPlayer player = PlayerManager.getPlayer(playerName);
		Team team = xTeam.getTeamManager().getTeam(teamName);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkPlayerHasTeam(player);
		Requirements.checkPlayerOnTeam(player, team);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("promote") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return "/team promote [Team] [Player]";
	}
}
