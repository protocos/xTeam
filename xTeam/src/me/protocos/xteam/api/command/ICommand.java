package me.protocos.xteam.api.command;

import me.protocos.xteam.command.CommandParser;
import org.bukkit.command.CommandSender;

public interface ICommand
{
	public abstract String getUsage();

	public abstract String getPattern();

	public abstract String getDescription();

	public abstract boolean execute(CommandSender sender, CommandParser command);
}
