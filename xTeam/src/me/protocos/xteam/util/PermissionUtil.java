package me.protocos.xteam.util;

import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.data.configuration.Configuration;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class PermissionUtil
{
	public static boolean hasPermission(CommandSender sender, BaseCommand command)
	{
		if (command instanceof IPermissible)
			return hasPermission(sender, CommonUtil.assignFromType(command, IPermissible.class));
		else if (sender instanceof ConsoleCommandSender && command instanceof ConsoleCommand)
			return true;
		return false;
	}

	public static boolean hasPermission(CommandSender sender, IPermissible perm)
	{
		String node = perm.getPermissionNode();
		if (node == null)
			return false;
		if (node.endsWith("help"))
			return true;
		if (Configuration.NO_PERMISSIONS && ((node.startsWith("xteam.") && node.contains("user.")) ||
				(node.startsWith("xteam.") && node.contains("admin.")) ||
				(node.startsWith("xteam.") && node.contains("leader.")) ||
				node.endsWith("info")))
			return true;
		return sender.hasPermission(node);
	}
}
