package me.protocos.xteam.core;

import org.bukkit.command.ConsoleCommandSender;

public interface IConsoleCommand extends ICommand
{
	public abstract void setSender(ConsoleCommandSender sender);
	public abstract ConsoleCommandSender getSender();
}
