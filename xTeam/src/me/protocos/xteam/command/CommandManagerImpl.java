package me.protocos.xteam.command;

import me.protocos.xteam.util.HashList;

public class CommandManagerImpl implements ICommandManager
{
	private HashList<String, ICommandPattern> patterns;
	private HashList<String, ICommandUsage> usages;
	private HashList<String, IPermissionNode> nodes;

	//	private static HashList<String, BaseConsoleCommand> consoleCommands = new HashList<String, BaseConsoleCommand>();
	//	private static HashList<String, BaseServerAdminCommand> adminCommands = new HashList<String, BaseServerAdminCommand>();;
	//	private static HashList<String, BaseUserCommand> userCommands = new HashList<String, BaseUserCommand>();

	public CommandManagerImpl()
	{
		patterns = new HashList<String, ICommandPattern>();
		usages = new HashList<String, ICommandUsage>();
		nodes = new HashList<String, IPermissionNode>();
	}

	@Override
	public void registerCommand(String key, BaseCommand command)
	{
		patterns.put(key, command);
		usages.put(key, command);
		nodes.put(key, command);
	}

	@Override
	public String getPattern(String key)
	{
		return patterns.get(key).getPattern();
	}
	@Override
	public String getUsage(String key)
	{
		return usages.get(key).getUsage();
	}
	@Override
	public String getPermissionNode(String key)
	{
		return nodes.get(key).getPermissionNode();
	}
	//	public static void registerConsoleCommand(String key, BaseConsoleCommand command)
	//	{
	//		consoleCommands.put(key, command);
	//	}
	//	public static void registerAdminCommand(String key, BaseServerAdminCommand command)
	//	{
	//		adminCommands.put(key, command);
	//	}
	//	public static void registerUserCommand(String key, BaseUserCommand command)
	//	{
	//		userCommands.put(key, command);
	//	}
	//	public static String getAdminPattern(String key)
	//	{
	//		return StringUtil.OPTIONAL_WHITE_SPACE + adminCommands.get(key).getPattern();
	//	}
	//	public static String getAdminPermissionNode(String key)
	//	{
	//		return adminCommands.get(key).getPermissionNode();
	//	}
	//	public static String getAdminUsage(String key)
	//	{
	//		return adminCommands.get(key).getUsage();
	//	}
	//	public static String getConsolePattern(String key)
	//	{
	//		return StringUtil.OPTIONAL_WHITE_SPACE + consoleCommands.get(key).getPattern();
	//	}
	//	public static String getConsoleUsage(String key)
	//	{
	//		return consoleCommands.get(key).getUsage();
	//	}
	//	public static String getUserPattern(String key)
	//	{
	//		return StringUtil.OPTIONAL_WHITE_SPACE + userCommands.get(key).getPattern();
	//	}
	//	public static String getUserPermissionNode(String key)
	//	{
	//		return userCommands.get(key).getPermissionNode();
	//	}
	//	public static String getUserUsage(String key)
	//	{
	//		return userCommands.get(key).getUsage();
	//	}
	//	public static boolean matchesAdminCmd(String command)
	//	{
	//		for (int i = 0; i < adminCommands.size(); i++)
	//		{
	//			if (command.matches(adminCommands.get(i).getPattern()))
	//				return true;
	//		}
	//		return false;
	//	}
	//	public static boolean matchesConsoleCmd(String command)
	//	{
	//		for (int i = 0; i < consoleCommands.size(); i++)
	//		{
	//			if (command.matches(consoleCommands.get(i).getPattern()))
	//				return true;
	//		}
	//		return false;
	//	}
	//	public static boolean matchesUserCmd(String command)
	//	{
	//		for (int i = 0; i < userCommands.size(); i++)
	//		{
	//			if (command.matches(userCommands.get(i).getPattern()))
	//				return true;
	//		}
	//		return false;
	//	}
}
