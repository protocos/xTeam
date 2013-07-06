package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class Command implements ICommandUsage, ICommandPattern, IPermissionNode
{
	protected final CommandSender originalSender;
	protected final List<String> parseCommand;
	protected static String baseCommand = "";

	public Command(CommandSender sender, String command)
	{
		originalSender = sender;
		parseCommand = command != null ? CommonUtil.toList(command.split(StringUtil.WHITE_SPACE)) : null;
		baseCommand = "/team";
	}

	public static void setBaseCommand(String baseCmd)
	{
		Command.baseCommand = baseCmd;
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
			xTeam.logger.info("FAIL: " + e.getMessage());
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
