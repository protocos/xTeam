package me.protocos.xteam.command;

import java.io.File;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.BaseCommand;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDelegate implements CommandExecutor
{
	private ICommandManager manager;

	public CommandDelegate(ICommandManager manager)
	{
		this.manager = manager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandID, String[] args)
	{
		try
		{
			CommandContainer commandContainer = new CommandContainer(sender, commandID, args);
			BaseCommand command = manager.match(commandContainer);
			logCommand(commandContainer);
			if (command == null)
			{
				sender.sendMessage(ChatColorUtil.negativeMessage((new TeamInvalidCommandException()).getMessage()));
				xTeam.getInstance().getLog().info("Command execute failed for reason: " + (new TeamInvalidCommandException()).getMessage());
			}
			else if (command.execute(commandContainer) == true)
				xTeam.getInstance().writeTeamData(new File("plugins/xTeam/teams.txt"));
		}
		catch (Exception e)
		{
			sender.sendMessage(ChatColorUtil.negativeMessage("There was a server error executing command: /" + commandID + " " + StringUtil.concatenate(args)));
			xTeam.getInstance().getLog().exception(e);
			xTeam.getInstance().getLog().info("[ERROR] Exception in xTeam onCommand() class [check logs]");
		}
		return true;
		//		try
		//		{
		//			String originalCommand = StringUtil.concatenate(args);
		//			BaseCommand command = null;
		//			CommandParser parseCommand = null;
		//			if (sender instanceof ConsoleCommandSender)
		//			{
		//				parseCommand = new CommandParser(commandID + " " + originalCommand);
		//				command = manager.matchConsole(commandContainer.getArgumentCommandWithoutID());
		//				xTeam.getInstance().getLog().info("console issued command: " + parseCommand.toString());
		//			}
		//			else if (sender instanceof Player)
		//			{
		//				parseCommand = new CommandParser("/" + commandID + " " + originalCommand);
		//				command = manager.matchPlayerCommand(commandContainer.getArgumentCommandWithoutID());
		//				xTeam.getInstance().getLog().info(sender.getName() + " issued command: " + parseCommand.toString());
		//			}
		//			if (command == null)
		//			{
		//				sender.sendMessage(ChatColorUtil.negativeMessage((new TeamInvalidCommandException()).getMessage()));
		//				xTeam.getInstance().getLog().info("Command execute failed for reason: " + (new TeamInvalidCommandException()).getMessage());
		//			}
		//			else if (command.execute(sender, parseCommand) == true)
		//				xTeam.getInstance().writeTeamData(new File("plugins/xTeam/teams.txt"));
		//		}
		//		catch (Exception e)
		//		{
		//			sender.sendMessage(ChatColorUtil.negativeMessage("There was a server error executing command: /" + commandID + " " + StringUtil.concatenate(args)));
		//			xTeam.getInstance().getLog().exception(e);
		//			xTeam.getInstance().getLog().info("[ERROR] Exception in xTeam onCommand() class [check logs]");
		//		}
		//		return true;
	}

	private void logCommand(CommandContainer commandContainer)
	{
		xTeam.getInstance().getLog().info(commandContainer.getSenderName() + " issued command: " + commandContainer.getCommand());
	}
}
