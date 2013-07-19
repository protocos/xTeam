package me.protocos.xteam.command;

import org.bukkit.command.CommandSender;

public interface ICommand
{
	public abstract boolean execute(CommandSender sender, CommandParser command);
}
