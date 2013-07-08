package me.protocos.xteam.command;

import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleCommand extends Command
{
	ConsoleCommandSender sender;

	public ConsoleCommand()
	{
		super();
	}

	public ConsoleCommand(ConsoleCommandSender sender, CommandParser command)
	{
		super(sender, command);
		setSender(sender);
	}

	public String getPermissionNode()
	{
		return null;
	}

	public ConsoleCommandSender getSender()
	{
		return sender;
	}

	public void setSender(ConsoleCommandSender sender)
	{
		this.sender = sender;
	}
}
