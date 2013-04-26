package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.core.ICommandPattern;
import me.protocos.xteam.core.ICommandUsage;
import me.protocos.xteam.core.IPermissionNode;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand implements ICommandUsage, ICommandPattern, IPermissionNode
{
	protected final CommandSender originalSender;
	protected final List<String> parseCommand;
	protected static String baseCommand = "";

	public BaseCommand()
	{
		originalSender = null;
		parseCommand = null;
		baseCommand = null;
	}
	public BaseCommand(CommandSender sender, String command)
	{
		originalSender = sender;
		parseCommand = CommonUtil.toList(command.split(StringUtil.WHITE_SPACE));
		baseCommand = "/team";
	}

	public static void setBaseCommand(String baseCmd)
	{
		BaseCommand.baseCommand = baseCmd;
	}
	public static String getBaseCommand()
	{
		return baseCommand;
	}
	public boolean execute()
	{
		try
		{
			checkRequirements();
			act();
			return true;
		}
		catch (TeamException e)
		{
			originalSender.sendMessage(ChatColor.RED + e.getMessage());
		}
		return false;
	}
	protected abstract void act();
	protected abstract void checkRequirements() throws TeamException;

	public CommandSender getCommandSender()
	{
		return originalSender;
	}
	public List<String> getParseCommand()
	{
		return parseCommand;
	}
	public String toString()
	{
		return baseCommand + StringUtil.concatenate(parseCommand.toArray());
	}
}
