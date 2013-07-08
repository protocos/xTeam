package me.protocos.xteam.command;

import me.protocos.xteam.util.HashList;

public class CommandManager implements ICommandManager
{
	private HashList<String, ICommandPattern> patterns;
	private HashList<String, ICommandUsage> usages;
	private HashList<String, IPermissionNode> nodes;

	public CommandManager()
	{
		patterns = new HashList<String, ICommandPattern>();
		usages = new HashList<String, ICommandUsage>();
		nodes = new HashList<String, IPermissionNode>();
	}

	@Override
	public String getPattern(String key)
	{
		return patterns.get(key).getPattern();
	}
	@Override
	public String getPermissionNode(String key)
	{
		return nodes.get(key).getPermissionNode();
	}
	@Override
	public String getUsage(String key)
	{
		return usages.get(key).getUsage();
	}
	@Override
	public void registerCommand(String key, Command command)
	{
		patterns.put(key, command);
		usages.put(key, command);
		nodes.put(key, command);
	}
}
