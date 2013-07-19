package me.protocos.xteam.util;

import me.protocos.xteam.core.Data;
import org.bukkit.command.CommandSender;

public class PermissionUtil
{
	public static boolean hasPermission(CommandSender sender, String node)
	{
		if (Data.NO_PERMISSIONS && (node.startsWith("xteam.player.") || node.startsWith("xteam.admin.") || node.startsWith("xteam.leader.")))
			return true;
		return sender.hasPermission(node);
	}
}
