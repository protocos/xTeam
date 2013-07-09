package me.protocos.xteam.command;

import org.bukkit.command.ConsoleCommandSender;

public interface IConsoleCommand extends ICommand
{
	public abstract ConsoleCommandSender getSender();
	public abstract void setSender(ConsoleCommandSender sender);
}
