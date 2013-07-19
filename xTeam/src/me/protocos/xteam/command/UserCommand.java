package me.protocos.xteam.command;

import java.io.InvalidClassException;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public abstract class UserCommand extends PlayerCommand
{
	public UserCommand()
	{
		super();
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
	}
}
