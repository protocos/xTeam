package me.protocos.xteam.core;

import me.protocos.xteam.listener.TeamChatListener;
import me.protocos.xteam.listener.TeamPlayerListener;
import me.protocos.xteam.listener.TeamPvPEntityListener;
import me.protocos.xteam.listener.TeamScoreListener;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
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
	private final FileConfiguration config;

	public TeamServiceManager(JavaPlugin plugin)
	{
		this.plugin = plugin;
		bukkit = Data.BUKKIT;
		pm = bukkit.getPluginManager();
		pm.registerEvents(new TeamPvPEntityListener(), plugin);
		pm.registerEvents(new TeamPlayerListener(), plugin);
		pm.registerEvents(new TeamScoreListener(), plugin);
		pm.registerEvents(new TeamChatListener(), plugin);
		tpm = new TeamPlayerManager();
		config = plugin.getConfig();
	}
	public OfflinePlayer getOfflinePlayer(String name)
	{
		for (OfflinePlayer offline : bukkit.getOfflinePlayers())
		{
			if (offline.getName().equals(name))
				return offline;
		}
		return null;
	}
	public Player getPlayer(String name)
	{
		for (Player online : bukkit.getOnlinePlayers())
		{
			if (online.getName().equals(name))
				return online;
		}
		return null;
	}
	public void loadConfig()
	{
		String setting;
		if (!config.contains(setting = "team_chat_enabled"))
			config.set(setting, true);
		if (!config.contains(setting = "headquarters_on_death_enabled"))
			config.set(setting, true);
		if (!config.contains(setting = "team_wolves_enabled"))
			config.set(setting, true);
		if (!config.contains(setting = "team_tag_enabled"))
			config.set(setting, true);
		if (!config.contains(setting = "display_teammate_coordinates_enabled"))
			config.set(setting, true);
		if (!config.contains(setting = "team_friendly_fire_enabled"))
			config.set(setting, false);
		if (!config.contains(setting = "alphanumeric_team_names_enabled"))
			config.set(setting, false);
		if (!config.contains(setting = "only_default_teams_enabled"))
			config.set(setting, false);
		if (!config.contains(setting = "default_teams.random_join_team"))
			config.set(setting, false);
		if (!config.contains(setting = "default_teams.balance_join_team"))
			config.set(setting, false);
		if (!config.contains(setting = "default_teams.headquarters_on_join"))
			config.set(setting, false);
		if (!config.contains(setting = "no_permissions_plugin"))
			config.set(setting, false);
		if (!config.contains(setting = "players_on_team"))
			config.set(setting, 10);
		if (!config.contains(setting = "teleport_radius"))
			config.set(setting, 500);
		if (!config.contains(setting = "teleport_danger_delay"))
			config.set(setting, 10);
		if (!config.contains(setting = "teleport_refresh_delay"))
			config.set(setting, 60);
		if (!config.contains(setting = "enemy_proximity"))
			config.set(setting, 16);
		if (!config.contains(setting = "set_headquarters_interval"))
			config.set(setting, 0);
		if (!config.contains(setting = "create_team_interval"))
			config.set(setting, 0);
		if (!config.contains(setting = "last_attacked_delay"))
			config.set(setting, 15);
		if (!config.contains(setting = "team_tag_length"))
			config.set(setting, 0);
		if (!config.contains(setting = "default_team_names"))
			config.set(setting, "");
		if (!config.contains(setting = "default_worlds"))
			config.set(setting, "");
		if (!config.contains(setting = "spout.hide_names"))
			config.set(setting, true);
		if (!config.contains(setting = "spout.name_reveal_time"))
			config.set(setting, 5);
	}
	public void registerEvents(Listener listener)
	{
		pm.registerEvents(listener, plugin);
	}
	public void reloadConfig()
	{
		plugin.reloadConfig();
		loadConfig();
	}
	public void saveConfig()
	{
		plugin.saveConfig();
	}
}
