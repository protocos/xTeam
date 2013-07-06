package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleDebug extends BaseConsoleCommand
{
	private String subCommand;

	public ConsoleDebug(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		if (subCommand.equalsIgnoreCase("chat"))
			originalSender.sendMessage("UserChat statuses: " + Data.chatStatus.toString());
		else if (subCommand.equalsIgnoreCase("invites"))
			originalSender.sendMessage("Invites: " + InviteHandler.data());
		else if (subCommand.equalsIgnoreCase("spies"))
			originalSender.sendMessage("Spies: " + Data.spies.toString());
		else if (subCommand.equalsIgnoreCase("return"))
			originalSender.sendMessage("UserReturn locations: " + Data.returnLocations.toString());
		else if (subCommand.equalsIgnoreCase("tasks"))
			originalSender.sendMessage("Task IDs: " + Data.taskIDs.toString());
		else if (subCommand.equalsIgnoreCase("tele"))
			originalSender.sendMessage("Teleported: " + Data.hasTeleported.toString());
		else if (subCommand.equalsIgnoreCase("attacked"))
			originalSender.sendMessage("Last attacked: " + Data.lastAttacked.toString());
		else if (subCommand.equalsIgnoreCase("created"))
			originalSender.sendMessage("Last created: " + Data.lastCreated.toString());
		else
			originalSender.sendMessage("Options are: debug [chat, invites, spies, return, tasks, tele, attacked, created]");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 1)
		{
			subCommand = "";
		}
		else if (parseCommand.size() == 2)
		{
			subCommand = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
	}
	@Override
	public String getPattern()
	{
		return "d" + patternOneOrMore("ebug") + "(" + WHITE_SPACE + ANY_CHARS + ")?" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " debug [Option]";
	}
}
