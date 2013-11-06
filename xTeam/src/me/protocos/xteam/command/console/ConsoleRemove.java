package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class ConsoleRemove extends ConsoleCommand
{
	private String teamName, playerName;
	private ITeamPlayer changePlayer;

	public ConsoleRemove()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team changeTeam = changePlayer.getTeam();
		changeTeam.removePlayer(playerName);
		originalSender.sendMessage("You" + ChatColorUtil.negativeMessage(" removed ") + playerName + " from " + teamName);
		if (changePlayer.isOnline())
			changePlayer.sendMessage("You've been " + ChatColorUtil.negativeMessage("removed") + " from " + changeTeam.getName());
		if (changeTeam.isEmpty())
		{
			originalSender.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
			xTeam.getTeamManager().removeTeam(changeTeam.getName());
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changePlayer = xTeam.getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(changePlayer);
		Requirements.checkPlayerHasTeam(changePlayer);
		Requirements.checkPlayerLeaderLeaving(changePlayer);
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
