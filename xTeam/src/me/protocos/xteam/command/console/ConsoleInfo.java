package me.protocos.xteam.command.console;

import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.InfoAction;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ConsoleInfo extends ConsoleCommand
{
	private String other;
	private InfoAction info;

	public ConsoleInfo()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		info.actOn(originalSender, other);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		other = parseCommand.get(1);
		info = new InfoAction();
		info.checkRequirements(other);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("info")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "/team info [Player/Team]";
	}

	@Override
	public String getDescription()
	{
		return "get info on player/team";
	}
}
