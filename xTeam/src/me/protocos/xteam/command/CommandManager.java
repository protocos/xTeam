package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.command.*;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.StringUtil;
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
	public ConsoleCommand matchConsole(String pattern)
	{
		for (BaseCommand command : commands)
			if (pattern.matches(StringUtil.IGNORE_CASE + command.getPattern()) && command instanceof ConsoleCommand)
				return (ConsoleCommand) command;
		return null;
	}

	@Override
	public PlayerCommand matchPlayerCommand(String pattern)
	{
		PlayerCommand match = matchServerAdmin(pattern);
		if (match == null)
			match = matchTeamLeader(pattern);
		if (match == null)
			match = matchTeamAdmin(pattern);
		if (match == null)
			match = matchTeamUser(pattern);
		return match;
	}

	private PlayerCommand matchServerAdmin(String pattern)
	{
		for (BaseCommand command : commands)
			if (pattern.matches(StringUtil.IGNORE_CASE + command.getPattern()) && command instanceof ServerAdminCommand)
				return (PlayerCommand) command;
		return null;
	}

	private TeamLeaderCommand matchTeamLeader(String pattern)
	{
		for (BaseCommand command : commands)
			if (pattern.matches(StringUtil.IGNORE_CASE + command.getPattern()) && command instanceof TeamLeaderCommand)
				return (TeamLeaderCommand) command;
		return null;
	}

	private TeamAdminCommand matchTeamAdmin(String pattern)
	{
		for (BaseCommand command : commands)
			if (pattern.matches(StringUtil.IGNORE_CASE + command.getPattern()) && command instanceof TeamAdminCommand)
				return (TeamAdminCommand) command;
		return null;
	}

	private TeamUserCommand matchTeamUser(String pattern)
	{
		for (BaseCommand command : commands)
			if (pattern.matches(StringUtil.IGNORE_CASE + command.getPattern()) && command instanceof TeamUserCommand)
				return (TeamUserCommand) command;
		return null;
	}

	@Override
	public List<String> getAvailableConsoleCommands(CommandSender sender)
	{
		List<String> availableCommands = CommonUtil.emptyList();
		for (ConsoleCommand command : CommonUtil.subListOfType(commands.asList(), ConsoleCommand.class))
		{
			if (sender instanceof ConsoleCommandSender)
			{
				availableCommands.add(ChatColorUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
			}
		}
		return availableCommands;
	}

	@Override
	public List<String> getAvailableAdminCommandsFor(TeamPlayer teamPlayer)
	{
		List<String> availableCommands = CommonUtil.emptyList();
		for (ServerAdminCommand command : CommonUtil.subListOfType(commands.asList(), ServerAdminCommand.class))
		{
			if (teamPlayer.hasPermission(command))
			{
				availableCommands.add(ChatColorUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
			}
		}
		return availableCommands;
	}

	@Override
	public List<String> getAvailableCommandsFor(TeamPlayer teamPlayer)
	{
		List<String> availableCommands = CommonUtil.emptyList();
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
		return availableCommands;
	}

	@Override
	public void register(ICommandContainer container)
	{
		container.registerConsoleCommands(this);
		container.registerServerAdminCommands(this);
		container.registerUserCommands(this);
		container.registerAdminCommands(this);
		container.registerLeaderCommands(this);
	}
}
