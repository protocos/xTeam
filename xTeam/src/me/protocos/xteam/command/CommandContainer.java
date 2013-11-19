package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandContainer
{
	private final CommandSender sender;
	private final String commandID;
	private final List<String> arguments;

	public CommandContainer(CommandSender sender, String commandID, String[] args)
	{
		this.sender = sender;
		this.commandID = commandID;
		this.arguments = CommonUtil.toList(args);
	}

	public String getArgument(int index)
	{
		return arguments.get(index);
	}

	public CommandSender getSender()
	{
		return sender;
	}

	public String getSenderName()
	{
		return this.sentFromConsole() ? "console" : sender.getName();
	}

	public List<String> getArguments()
	{
		return arguments;
	}

	public String getCommandID()
	{
		return commandID;
	}

	public String getCommandWithoutID()
	{
		return StringUtil.concatenate(arguments.toArray());
	}

	public String getCommand()
	{
		return (this.sentFromPlayer() ? "/" : "") + this.getCommandID() + (arguments.isEmpty() ? "" : " " + this.getCommandWithoutID());
	}

	public int size()
	{
		return arguments.size();
	}

	public boolean sentFromConsole()
	{
		return sender instanceof ConsoleCommandSender;
	}

	public boolean sentFromPlayer()
	{
		return sender instanceof Player;
	}

	public String toString()
	{
		return this.getCommand();
	}
}
