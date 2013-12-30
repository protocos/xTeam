package me.protocos.xteam.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class BukkitUtil
{
	private static Server BUKKIT = Bukkit.getServer();
	public static final double EYE_LEVEL_HEIGHT = 1.62;
	public static final long ONE_SECOND_IN_TICKS = 20L;
	public static final long ONE_MINUTE_IN_TICKS = 60 * 20L;

	public static void setServer(Server server)
	{
		BUKKIT = server;
	}

	public static List<Entity> getNearbyEntities(Location location, int radius)
	{
		List<Entity> nearbyEntities = new ArrayList<Entity>();
		List<Entity> entities = location.getWorld().getEntities();
		if (entities != null)
		{
			for (Entity e : entities)
			{
				if (e.getLocation().distance(location) <= radius)
					if (Math.abs(e.getLocation().getY() - location.getY()) <= 4)
						nearbyEntities.add(e);
			}
		}
		return nearbyEntities;
	}

	public static World getWorld(String string)
	{
		return BUKKIT.getWorld(string);
	}

	public static Server getServer()
	{
		return BUKKIT;
	}

	public static Player[] getOnlinePlayers()
	{
		return BUKKIT.getOnlinePlayers();
	}

	public static OfflinePlayer[] getOfflinePlayers()
	{
		return BUKKIT.getOfflinePlayers();
	}

	public static Player getPlayer(String name)
	{
		return BUKKIT.getPlayer(name);
	}

	public static OfflinePlayer getOfflinePlayer(String name)
	{
		return BUKKIT.getOfflinePlayer(name);
	}

	public static BukkitScheduler getScheduler()
	{
		return BUKKIT.getScheduler();
	}

	public static PluginManager getPluginManager()
	{
		return BUKKIT.getPluginManager();
	}

	public static Plugin getPlugin(String string)
	{
		return getPluginManager().getPlugin(string);
	}

	public static Plugin getxTeam()
	{
		return getPlugin("xTeam");
	}
}
