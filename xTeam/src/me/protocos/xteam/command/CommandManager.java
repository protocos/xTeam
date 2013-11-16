package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.api.ICommandContainer;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.command.IPermissible;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PermissionUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements ICommandManager
{
	private HashList<String, BaseCommand> commands;

	public CommandManager()
	{
		commands = new HashList<String, BaseCommand>();
	}

	@Override
	public void registerCommand(String key, BaseCommand command)
	{
		commands.put(key, command);
	}

	@Override
	public BaseCommand get(String key)
	{
		return commands.get(key);
	}

	@Override
	public String getPattern(String key)
	{
		return commands.get(key).getPattern();
	}

	@Override
	public String getPermissionNode(String key)
	{
		if (commands.get(key) instanceof IPermissible)
			return ((IPermissible) commands.get(key)).getPermissionNode();
		return null;
	}

	@Override
	public String getUsage(String key)
	{
		return commands.get(key).getUsage();
	}

	private String getDescription(String key)
	{
		return commands.get(key).getDescription();
	}

	@Override
	public ConsoleCommand matchConsole(String pattern)
	{
		for (int x = 0; x < commands.size(); x++)
			if (pattern.matches(StringUtil.IGNORE_CASE + commands.get(x).getPattern()) && commands.getKey(x).startsWith("console_"))
				return (ConsoleCommand) commands.get(x);
		return null;
	}

	@Override
	public PlayerCommand matchPlayer(String pattern)
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
		for (int x = 0; x < commands.size(); x++)
			if (pattern.matches(StringUtil.IGNORE_CASE + commands.get(x).getPattern()) && commands.getKey(x).startsWith("serveradmin_"))
				return (PlayerCommand) commands.get(x);
		return null;
	}

	private PlayerCommand matchTeamLeader(String pattern)
	{
		for (int x = 0; x < commands.size(); x++)
			if (pattern.matches(StringUtil.IGNORE_CASE + commands.get(x).getPattern()) && commands.getKey(x).startsWith("leader_"))
				return (PlayerCommand) commands.get(x);
		return null;
	}

	private PlayerCommand matchTeamAdmin(String pattern)
	{
		for (int x = 0; x < commands.size(); x++)
			if (pattern.matches(StringUtil.IGNORE_CASE + commands.get(x).getPattern()) && commands.getKey(x).startsWith("admin_"))
				return (PlayerCommand) commands.get(x);
		return null;
	}

	private PlayerCommand matchTeamUser(String pattern)
	{
		for (int x = 0; x < commands.size(); x++)
			if (pattern.matches(StringUtil.IGNORE_CASE + commands.get(x).getPattern()) && commands.getKey(x).startsWith("user_"))
				return (PlayerCommand) commands.get(x);
		return null;
	}

	@Override
	public List<String> getAvailableCommands(CommandSender sender)
	{
		List<String> availableCommands = CommonUtil.emptyList();
		for (String key : commands)
		{
			if (sender instanceof ConsoleCommandSender)
			{
				if (key.startsWith("console"))
					availableCommands.add(ChatColorUtil.formatForUser(getUsage(key) + " - " + getDescription(key)));
			}
		}
		return availableCommands;
	}

	@Override
	public List<String> getAvailableAdminCommandsFor(Player player)
	{
		List<String> availableCommands = CommonUtil.emptyList();
		for (String key : commands)
		{
			if (PermissionUtil.hasPermission(player, getPermissionNode(key)))
			{
				if (key.startsWith("serveradmin"))
					availableCommands.add(ChatColorUtil.formatForUser(getUsage(key) + " - " + getDescription(key)));
			}
		}
		return availableCommands;
	}

	@Override
	public List<String> getAvailableCommandsFor(TeamPlayer teamPlayer)
	{
		List<String> availableCommands = CommonUtil.emptyList();
		for (String key : commands)
		{
			if (teamPlayer.hasPermission(getPermissionNode(key)))
			{
				if (key.startsWith("user"))
					availableCommands.add(ChatColorUtil.formatForUser(getUsage(key) + " - " + getDescription(key)));
				if (key.startsWith("admin"))
					availableCommands.add(ChatColorUtil.formatForAdmin(getUsage(key) + " - " + getDescription(key)));
				if (key.startsWith("leader"))
					availableCommands.add(ChatColorUtil.formatForLeader(getUsage(key) + " - " + getDescription(key)));
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
