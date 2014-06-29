package me.protocos.xteam.util;

import org.bukkit.command.CommandSender;
import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.CommandContainer;

public class CommandUtil
{
	public static boolean execute(CommandSender sender, BaseCommand command, String string)
	{
		return command.execute(new CommandContainer(sender, "team", string.split(" ")));
	}
}
