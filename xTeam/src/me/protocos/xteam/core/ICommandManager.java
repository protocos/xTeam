package me.protocos.xteam.core;

import me.protocos.xteam.command.BaseCommand;

public interface ICommandManager
{
	public abstract void registerCommand(String key, BaseCommand command);
	public abstract String getPattern(String key);
	public abstract String getUsage(String key);
	public abstract String getPermissionNode(String key);
}
