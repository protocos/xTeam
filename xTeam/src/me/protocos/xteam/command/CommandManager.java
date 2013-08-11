package me.protocos.xteam.command;

import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.command.IPermissionNode;
import me.protocos.xteam.util.StringUtil;

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
		if (commands.get(key) instanceof IPermissionNode)
			return ((IPermissionNode) commands.get(key)).getPermissionNode();
		return null;
	}
	@Override
	public String getUsage(String key)
	{
		return commands.get(key).getUsage();
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
}
