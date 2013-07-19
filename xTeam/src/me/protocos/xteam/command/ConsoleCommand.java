package me.protocos.xteam.command;

import java.io.InvalidClassException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleCommand extends BaseCommand
{
	public ConsoleCommand()
	{
		super();
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		if (!(originalSender instanceof ConsoleCommandSender))
			throw new InvalidClassException("Sender not an instance of ConsoleCommandSender");
		if (!parseCommand.getCommandWithoutID().matches(StringUtil.IGNORE_CASE + getPattern()))
			throw new TeamInvalidCommandException();
	}
}
