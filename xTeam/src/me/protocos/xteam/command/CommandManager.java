package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
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

	private String modifyPattern(String commandPattern)
	{
		return new PatternBuilder().append(commandPattern.replaceAll("\\\\(s|S)(\\+|\\*)", "").replaceAll("\\[0\\-9\\-\\\\.\\]\\+|\\[\\]\\+", "")).whiteSpaceOptional().repeatUnlimited(new PatternBuilder().append("\\?+").whiteSpaceOptional()).toString();
	}

	@Override
	public String getHelp(CommandContainer commandContainer)
	{
		for (BaseCommand command : commands)
		{
			if (commandContainer.sentFromConsole() && command instanceof ConsoleCommand && PermissionUtil.hasPermission(commandContainer.getSender(), command) && commandContainer.getCommandWithoutID().matches(modifyPattern(command.getPattern())))
				return MessageUtil.highlightString(ChatColor.GRAY, "Console Parameters: {optional} [required] pick/one\n") +
						MessageUtil.highlightString(ChatColor.GRAY, "Usage: " + command.getUsage() + " - " + command.getDescription());
			else if (commandContainer.sentFromPlayer() && command instanceof ServerAdminCommand && PermissionUtil.hasPermission(commandContainer.getSender(), command) && commandContainer.getCommandWithoutID().matches(modifyPattern(command.getPattern())))
				return MessageUtil.highlightString(ChatColor.RESET, MessageUtil.formatForServerAdmin("Server Admin") + " Parameters: {optional} [required] pick/one\n") +
						"Usage: " + MessageUtil.formatForServerAdmin(command.getUsage() + " - " + command.getDescription());
			else if (commandContainer.sentFromPlayer() && command instanceof TeamLeaderCommand && PermissionUtil.hasPermission(commandContainer.getSender(), command) && commandContainer.getCommandWithoutID().matches(modifyPattern(command.getPattern())))
				return MessageUtil.highlightString(ChatColor.RESET, MessageUtil.formatForLeader("Team Leader") + " Parameters: {optional} [required] pick/one\n") +
						"Usage: " + MessageUtil.formatForLeader(command.getUsage() + " - " + command.getDescription());
			else if (commandContainer.sentFromPlayer() && command instanceof TeamAdminCommand && PermissionUtil.hasPermission(commandContainer.getSender(), command) && commandContainer.getCommandWithoutID().matches(modifyPattern(command.getPattern())))
				return MessageUtil.highlightString(ChatColor.RESET, MessageUtil.formatForAdmin("Team Admin") + " Parameters: {optional} [required] pick/one\n") +
						"Usage: " + MessageUtil.formatForAdmin(command.getUsage() + " - " + command.getDescription());
			else if (commandContainer.sentFromPlayer() && command instanceof TeamUserCommand && PermissionUtil.hasPermission(commandContainer.getSender(), command) && commandContainer.getCommandWithoutID().matches(modifyPattern(command.getPattern())))
				return MessageUtil.highlightString(ChatColor.RESET, MessageUtil.formatForUser("Team User") + " Parameters: {optional} [required] pick/one\n") +
						"Usage: " + MessageUtil.formatForUser(command.getUsage() + " - " + command.getDescription());
		}
		return null;
	}

	@Override
	public BaseCommand match(CommandContainer commandContainer)
	{
		for (BaseCommand command : commands)
			if (commandContainer.sentFromConsole() && command instanceof ConsoleCommand && new PatternBuilder(command.getPattern()).ignoreCase().matches(commandContainer.getCommandWithoutID()))
				return command;
			else if (commandContainer.sentFromPlayer() && command instanceof PlayerCommand && new PatternBuilder(command.getPattern()).ignoreCase().matches(commandContainer.getCommandWithoutID()))
				return command;
		return null;
	}

	@Override
	public List<String> getAvailableCommandsFor(CommandSender sender)
	{
		List<String> availableCommands = CommonUtil.emptyList();
		if (sender instanceof ConsoleCommandSender)
		{
			for (ConsoleCommand command : CommonUtil.subListOfType(commands.toList(), ConsoleCommand.class))
			{
				if (sender instanceof ConsoleCommandSender)
				{
					availableCommands.add(MessageUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
				}
			}
		}
		else if (sender instanceof ITeamPlayer)
		{
			ITeamPlayer teamPlayer = CommonUtil.assignFromType(sender, ITeamPlayer.class);
			for (TeamUserCommand command : CommonUtil.subListOfType(commands.toList(), TeamUserCommand.class))
			{
				if (teamPlayer.hasPermission(command))
				{
					availableCommands.add(MessageUtil.formatForUser(command.getUsage() + " - " + command.getDescription()));
				}
			}
			for (TeamAdminCommand command : CommonUtil.subListOfType(commands.toList(), TeamAdminCommand.class))
			{
				if (teamPlayer.hasPermission(command) && (teamPlayer.isLeader() || teamPlayer.isAdmin()))
				{
					availableCommands.add(MessageUtil.formatForAdmin(command.getUsage() + " - " + command.getDescription()));
				}
			}
			for (TeamLeaderCommand command : CommonUtil.subListOfType(commands.toList(), TeamLeaderCommand.class))
			{
				if (teamPlayer.hasPermission(command) && teamPlayer.isLeader())
				{
					availableCommands.add(MessageUtil.formatForLeader(command.getUsage() + " - " + command.getDescription()));
				}
			}
			for (ServerAdminCommand command : CommonUtil.subListOfType(commands.toList(), ServerAdminCommand.class))
			{
				if (teamPlayer.hasPermission(command))
				{
					availableCommands.add(MessageUtil.formatForServerAdmin(command.getUsage() + " - " + command.getDescription()));
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
