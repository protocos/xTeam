package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminChatSpy extends ServerAdminCommand
{
	public AdminChatSpy()
	{
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		if (!Data.spies.contains(originalSender.getName()))
		{
			Data.spies.add(originalSender.getName());
			originalSender.sendMessage("You are " + ChatColor.RED + "now" + ChatColor.RESET + " spying on team chat");
		}
		else
		{
			Data.spies.remove(originalSender.getName());
			originalSender.sendMessage("You are " + ChatColor.GREEN + "no longer" + ChatColor.RESET + " spying on team chat");
		}
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("chat") + patternOneOrMore("spy") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.chatspy";
	}
	@Override
	public String getUsage()
	{
		return "/team chatspy";
	}
}
