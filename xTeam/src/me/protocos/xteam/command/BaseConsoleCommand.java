package me.protocos.xteam.command;

import org.bukkit.command.ConsoleCommandSender;

public abstract class BaseConsoleCommand extends BaseCommand implements IConsoleCommand
{
	ConsoleCommandSender sender;

	public BaseConsoleCommand()
	{
		super();
	}

	public BaseConsoleCommand(ConsoleCommandSender sender, String command)
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
