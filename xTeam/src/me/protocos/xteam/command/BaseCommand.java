package me.protocos.xteam.command;

import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ICommand;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand implements ICommand
{
	public BaseCommand()
	{
	}

	protected abstract void act(CommandSender sender, CommandParser command);

	protected abstract void checkRequirements(CommandSender sender, CommandParser command) throws TeamException, InvalidClassException;

	@Override
	public boolean execute(CommandSender sender, CommandParser command)
	{
		try
		{
			checkRequirements(sender, command);
			act(sender, command);
			return true;
		}
		catch (TeamException e)
		{
			sender.sendMessage(ChatColor.RED + e.getMessage());
			xTeam.getLog().info("Command execute failed for reason: " + e.getMessage());
		}
		catch (InvalidClassException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
