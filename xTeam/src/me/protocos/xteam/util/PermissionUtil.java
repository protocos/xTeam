package me.protocos.xteam.util;

import me.protocos.xteam.api.command.IPermissible;
import me.protocos.xteam.data.configuration.Configuration;
import org.bukkit.command.CommandSender;

public class PermissionUtil
{
	public static boolean hasPermission(CommandSender sender, IPermissible perm)
	{
		String node = perm.getPermissionNode();
		if (node == null)
			return false;
		if ("help".equals(node) || "info".equals(node))
			return true;
		if (Configuration.NO_PERMISSIONS && (node.startsWith("xteam.player.") || node.startsWith("xteam.admin.") || node.startsWith("xteam.leader.")))
			return true;
		return sender.hasPermission(node);
	}
}
