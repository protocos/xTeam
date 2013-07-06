package me.protocos.xteam.command;

import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleCommand extends Command implements IConsoleCommand
{
	ConsoleCommandSender sender;

	public ConsoleCommand(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
		setSender(sender);
	}

	public void setSender(ConsoleCommandSender sender)
	{
		this.sender = sender;
	}

	public ConsoleCommandSender getSender()
	{
		return sender;
	}

	@Override
	public String getPermissionNode()
	{
		return null;
	}
}
