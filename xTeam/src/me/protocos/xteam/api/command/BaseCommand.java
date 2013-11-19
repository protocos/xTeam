package me.protocos.xteam.api.command;

import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand
{
	public BaseCommand()
	{
	}

	public abstract String getUsage();

	public abstract String getPattern();

	public abstract String getDescription();

	protected abstract void initData(CommandSender originalSender, CommandParser command) throws TeamException, InvalidClassException;

	protected abstract void checkRequirements(CommandSender originalSender, CommandParser command) throws TeamException;

	protected abstract void act(CommandSender originalSender, CommandParser command);

	public final boolean execute(CommandContainer commandContainer)
	{
		CommandSender originalSender = commandContainer.getSender();
		CommandParser command = new CommandParser(commandContainer.getCommand());
		try
		{
			initData(originalSender, command);
			checkRequirements(originalSender, command);
			act(originalSender, command);
			return true;
		}
		catch (TeamException e)
		{
			originalSender.sendMessage(ChatColorUtil.negativeMessage(e.getMessage()));
			xTeam.getInstance().getLog().info("Command execute failed for reason: " + e.getMessage());
		}
		catch (InvalidClassException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
