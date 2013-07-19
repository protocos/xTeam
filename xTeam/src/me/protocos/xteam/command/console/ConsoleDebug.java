package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class ConsoleDebug extends ConsoleCommand
{
	private String subCommand;

	public ConsoleDebug()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
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
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		if (parseCommand.size() == 1)
		{
			subCommand = "";
		}
		else if (parseCommand.size() == 2)
		{
			subCommand = parseCommand.get(1);
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
		return "/team debug [Option]";
	}
}
