package me.protocos.xteam.command;


public interface ICommandManager
{
	public abstract void registerCommand(String key, Command command);
	public abstract String getPattern(String key);
	public abstract String getUsage(String key);
	public abstract String getPermissionNode(String key);
}
