package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleReload extends BaseConsoleCommand
{
	public ConsoleReload()
	{
		super();
	}
	public ConsoleReload(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Data.load();
		originalSender.sendMessage("Config reloaded");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 1)
		{
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("reload") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " reload";
	}
}
