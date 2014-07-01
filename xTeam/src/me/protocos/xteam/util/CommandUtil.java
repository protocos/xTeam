package me.protocos.xteam.util;

import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.CommandContainer;
import org.bukkit.command.CommandSender;

public class CommandUtil
{
	public static boolean execute(CommandSender sender, BaseCommand command, String string)
	{
		return command.execute(new CommandContainer(sender, "team", string.split(" ")));
	}
}
