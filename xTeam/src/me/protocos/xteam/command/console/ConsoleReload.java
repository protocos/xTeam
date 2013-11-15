package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class ConsoleReload extends ConsoleCommand
{
	public ConsoleReload()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		xTeam.getInstance().getConfigLoader().load();
		originalSender.sendMessage("Configuration reloaded");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("reload") + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getUsage()
	{
		return "/team reload";
	}

	@Override
	public String getDescription()
	{
		return "reload configuration file";
	}
}
