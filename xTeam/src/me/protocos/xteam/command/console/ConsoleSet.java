package me.protocos.xteam.command.console;

import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.SetTeamAction;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ConsoleSet extends ConsoleCommand
{
	private String playerName, teamName;
	private SetTeamAction set;

	public ConsoleSet()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		set.actOn(playerName, teamName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		playerName = parseCommand.get(1);
		teamName = parseCommand.get(2);
		set = new SetTeamAction(originalSender);
		set.checkRequirementsOn(playerName, teamName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
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
		return "/team set [Player] [Team]";
	}

	@Override
	public String getDescription()
	{
		return "set team of player";
	}
}
