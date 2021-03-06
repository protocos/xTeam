package me.protocos.xteam.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import me.protocos.api.util.SystemUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class BukkitUtil
{
	public static final double EYE_LEVEL_HEIGHT = 1.62;
	public static final long ONE_SECOND_IN_TICKS = 20L;
	public static final long ONE_MINUTE_IN_TICKS = 60 * 20L;
	private Server server;

	public BukkitUtil(Server server)
	{
		this.server = server;
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

	public Server getServer()
	{
		return server;
	}

	public World getWorld(String string)
	{
		return server.getWorld(string);
	}

	public Player[] getOnlinePlayers()
	{
		return server.getOnlinePlayers();
	}

	public OfflinePlayer[] getOfflinePlayers()
	{
		return server.getOfflinePlayers();
	}

	public Player getPlayer(String name)
	{
		return server.getPlayer(name);
	}

	public OfflinePlayer getOfflinePlayer(String name)
	{
		return server.getOfflinePlayer(name);
	}

	public BukkitScheduler getScheduler()
	{
		return server.getScheduler();
	}

	public PluginManager getPluginManager()
	{
		return server.getPluginManager();
	}

	public Plugin getPlugin(String string)
	{
		return this.getPluginManager().getPlugin(string);
	}

	public Plugin getxTeam()
	{
		return this.getPlugin("xTeam");
	}

	public String getxTeamChecksum()
	{
		if (SystemUtil.pathIsPresent("./plugins/xTeam.jar"))
		{
			try
			{
				return SystemUtil.getMD5Checksum("./plugins/xTeam.jar");
			}
			catch (NoSuchAlgorithmException | IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean serverIsLive()
	{
		Server liveServer = Bukkit.getServer();
		if (liveServer == null)
			return false;
		return true;
	}
}
