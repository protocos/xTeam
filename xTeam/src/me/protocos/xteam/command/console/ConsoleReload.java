package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Data;
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
		Data.load();
		originalSender.sendMessage("Config reloaded");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
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
}
