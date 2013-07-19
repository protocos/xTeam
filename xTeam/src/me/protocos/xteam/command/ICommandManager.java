package me.protocos.xteam.command;

public interface ICommandManager
{
	public abstract void registerCommand(String key, BaseCommand command);
	public abstract BaseCommand get(String key);
	public abstract String getPattern(String key);
	public abstract String getPermissionNode(String key);
	public abstract String getUsage(String key);
	public abstract ConsoleCommand matchConsole(String pattern);
	public abstract PlayerCommand matchPlayer(String pattern);
}
