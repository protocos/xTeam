package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.command.*;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandManager implements ICommandManager
{
	private HashList<String, BaseCommand> commands;

	public CommandManager()
	{
		commands = new HashList<String, BaseCommand>();
	}

	@Override
	public void registerCommand(BaseCommand command)
	{
		commands.put(command.getClass().getName(), command);
	}

	@Override
	public BaseCommand match(CommandContainer commandContainer)
	{
		for (BaseCommand command : commands)
			if (new PatternBuilder(command.getPattern()).ignoreCase().matches(commandContainer.getCommandWithoutID()) && commandContainer.sentFromConsole() && command instanceof ConsoleCommand)
				return command;
			else if (new PatternBuilder(command.getPattern()).ignoreCase().matches(commandContainer.getCommandWithoutID()) && commandContainer.sentFromPlayer() && command instanceof PlayerCommand)
				return command;
		return null;
	}

	//	@Override
	//	public List<String> getAvailableConsoleCommands(CommandSender sender)
	//	{
	//		List<String> availableCommands = CommonUtil.emptyList();
	//		for (ConsoleCommand command : CommonUtil.subListOfType(commands.asList(), ConsoleCommand.class))
	//		{
	//			if (sender instanceof ConsoleCommandSender)
	//			{
	//				availableCommands.add(ChatColorUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
	//			}
	//		}
	//		return availableCommands;
	//	}
	//
	//	@Override
	//	public List<String> getAvailableAdminCommandsFor(TeamPlayer teamPlayer)
	//	{
	//		List<String> availableCommands = CommonUtil.emptyList();
	//		for (ServerAdminCommand command : CommonUtil.subListOfType(commands.asList(), ServerAdminCommand.class))
	//		{
	//			if (teamPlayer.hasPermission(command))
	//			{
	//				availableCommands.add(ChatColorUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
	//			}
	//		}
	//		return availableCommands;
	//	}
	//
	//	@Override
	//	public List<String> getAvailableCommandsFor(TeamPlayer teamPlayer)
	//	{
	//		List<String> availableCommands = CommonUtil.emptyList();
	//		for (TeamUserCommand command : CommonUtil.subListOfType(commands.asList(), TeamUserCommand.class))
	//		{
	//			if (teamPlayer.hasPermission(command))
	//			{
	//				availableCommands.add(ChatColorUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
	//			}
	//		}
	//		for (TeamAdminCommand command : CommonUtil.subListOfType(commands.asList(), TeamAdminCommand.class))
	//		{
	//			if (teamPlayer.hasPermission(command))
	//			{
	//				availableCommands.add(ChatColorUtil.formatForAdmin(command.getUsage() + " - " + command.getDescription()));
	//			}
	//		}
	//		for (TeamLeaderCommand command : CommonUtil.subListOfType(commands.asList(), TeamLeaderCommand.class))
	//		{
	//			if (teamPlayer.hasPermission(command))
	//			{
	//				availableCommands.add(ChatColorUtil.formatForLeader(command.getUsage() + " - " + command.getDescription()));
	//			}
	//		}
	//		return availableCommands;
	//	}
	@Override
	public List<String> getAvailableCommandsFor(CommandSender sender)
	{
		List<String> availableCommands = CommonUtil.emptyList();
		if (sender instanceof ConsoleCommandSender)
		{
			for (ConsoleCommand command : CommonUtil.subListOfType(commands.asList(), ConsoleCommand.class))
			{
				if (sender instanceof ConsoleCommandSender)
				{
					availableCommands.add(ChatColorUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
				}
			}
		}
		else if (sender instanceof ITeamPlayer)
		{
			TeamPlayer teamPlayer = CommonUtil.assignFromType(sender, TeamPlayer.class);
			for (TeamUserCommand command : CommonUtil.subListOfType(commands.asList(), TeamUserCommand.class))
			{
				if (teamPlayer.hasPermission(command))
				{
					availableCommands.add(ChatColorUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
				}
			}
			for (TeamAdminCommand command : CommonUtil.subListOfType(commands.asList(), TeamAdminCommand.class))
			{
				if (teamPlayer.hasPermission(command))
				{
					availableCommands.add(ChatColorUtil.formatForAdmin(command.getUsage() + " - " + command.getDescription()));
				}
			}
			for (TeamLeaderCommand command : CommonUtil.subListOfType(commands.asList(), TeamLeaderCommand.class))
			{
				if (teamPlayer.hasPermission(command))
				{
					availableCommands.add(ChatColorUtil.formatForLeader(command.getUsage() + " - " + command.getDescription()));
				}
			}
			for (ServerAdminCommand command : CommonUtil.subListOfType(commands.asList(), ServerAdminCommand.class))
			{
				if (teamPlayer.hasPermission(command))
				{
					availableCommands.add(ChatColorUtil.formatForServerAdmin(command.getUsage() + " - " + command.getDescription()));
				}
			}
		}
		return availableCommands;
	}

	@Override
	public void register(ICommandContainer container)
	{
		container.registerCommands(this);
	}
}
