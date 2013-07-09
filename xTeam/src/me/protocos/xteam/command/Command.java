package me.protocos.xteam.command;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class Command implements ICommandUsage, ICommandPattern, IPermissionNode
{
	protected final CommandSender originalSender;
	protected final CommandParser parseCommand;

	public Command()
	{
		originalSender = null;
		parseCommand = new CommandParser("/team");
	}

	public Command(CommandSender sender, CommandParser command)
	{
		originalSender = sender;
		parseCommand = command;
	}

	protected abstract void act();

	protected abstract void checkRequirements() throws TeamException;

	public boolean execute()
	{
		try
		{
			checkRequirements();
			act();
			return true;
		}
		catch (TeamException e)
		{
			originalSender.sendMessage(ChatColor.RED + e.getMessage());
			xTeam.logger.info("FAIL: " + e.getMessage());
		}
		return false;
	}

	public CommandSender getCommandSender()
	{
		return originalSender;
	}
	public CommandParser getParseCommand()
	{
		return parseCommand;
	}
}
