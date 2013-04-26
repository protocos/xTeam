package me.protocos.xteam.util;

import me.protocos.xteam.core.Data;
import org.bukkit.command.CommandSender;

public class PermissionUtil
{
	public static boolean hasPermission(CommandSender sender, String permission)
	{
		if (Data.NO_PERMISSIONS && (permission.startsWith("xteam.player.") || permission.startsWith("xteam.admin.") || permission.startsWith("xteam.leader.")))
			return true;
		return sender.hasPermission(permission);
	}
}
