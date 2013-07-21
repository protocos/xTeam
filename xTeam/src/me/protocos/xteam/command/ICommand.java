package me.protocos.xteam.command;

import org.bukkit.command.CommandSender;

public interface ICommand
{
	public abstract String getUsage();
	public abstract String getPattern();
	public abstract boolean execute(CommandSender sender, CommandParser command);
}
