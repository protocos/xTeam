package me.protocos.xteam.core;

import me.protocos.xteam.listener.TeamChatListener;
import me.protocos.xteam.listener.TeamPlayerListener;
import me.protocos.xteam.listener.TeamPvPEntityListener;
import me.protocos.xteam.listener.TeamScoreListener;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TeamServiceManager
{
	private final Server bukkit;
	private final JavaPlugin plugin;
	private final PluginManager pm;
	private final TeamPlayerManager tpm;

	//	private final FileConfiguration config;

	public TeamServiceManager(JavaPlugin plugin)
	{
		this.plugin = plugin;
		bukkit = Bukkit.getServer();
		pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new TeamPvPEntityListener(), plugin);
		pm.registerEvents(new TeamPlayerListener(), plugin);
		pm.registerEvents(new TeamScoreListener(), plugin);
		pm.registerEvents(new TeamChatListener(), plugin);
		tpm = new TeamPlayerManager();
		//		config = plugin.getConfig();
	}
	public void registerEvents(Listener listener)
	{
		pm.registerEvents(listener, plugin);
	}
	public Player getPlayer(String name)
	{
		return bukkit.getPlayer(name);
	}
	public OfflinePlayer getOfflinePlayer(String name)
	{
		return bukkit.getOfflinePlayer(name);
	}
	public ITeamPlayer getTeamPlayer(String name)
	{
		return tpm.getTeamPlayer(name);
	}
}
