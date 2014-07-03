package me.protocos.xteam.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;

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
	public List<ConsoleCommand> getConsoleCommands()
	{
		List<ConsoleCommand> availableCommands = CommonUtil.emptyList();
		for (ConsoleCommand command : CommonUtil.subListOfType(commands.toList(), ConsoleCommand.class))
		{
			availableCommands.add(command);
		}
		return availableCommands;
	}

	@Override
	public List<PlayerCommand> getAvailableCommandsFor(ITeamPlayer sender)
	{
		Set<PlayerCommand> availableCommands = CommonUtil.emptySet(new Comparator<PlayerCommand>()
		{
			@Override
			public int compare(PlayerCommand playerCommand1, PlayerCommand playerCommand2)
			{
				int compare = playerCommand1.getClassification().getRank() - playerCommand2.getClassification().getRank();
				//if they have the same classification, then say that it sorts after the previous one
				return compare == 0 ? 1 : compare;
			}
		});
		for (PlayerCommand command : CommonUtil.subListOfType(commands.toList(), PlayerCommand.class))
		{
			if (sender.hasPermission(command))
			{
				availableCommands.add(command);
			}
		}
		return new ArrayList<PlayerCommand>(availableCommands);
	}

	@Override
	public void register(ICommandContainer container)
	{
		container.registerCommands(this);
	}

	@Override
	public String toString()
	{
		String result = "";
		for (BaseCommand command : commands)
		{
			result += command.getUsage() + " - " + command.getDescription() + "\n";
		}
		return result;
	}
}
