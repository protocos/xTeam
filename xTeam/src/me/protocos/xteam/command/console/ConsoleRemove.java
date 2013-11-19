package me.protocos.xteam.command.console;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
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
			xTeam.getInstance().getTeamManager().removeTeam(changeTeam.getName());
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changePlayer = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(changePlayer);
		Requirements.checkPlayerHasTeam(changePlayer);
		Requirements.checkPlayerLeaderLeaving(changePlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("move")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "/team remove [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "remove player from team";
	}
}
