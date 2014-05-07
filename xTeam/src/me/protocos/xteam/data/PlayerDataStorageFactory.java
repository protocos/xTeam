package me.protocos.xteam.data;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerManager;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.BukkitUtil;

public class PlayerDataStorageFactory
{
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;
	private ILog log;

	public PlayerDataStorageFactory(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.bukkitUtil = teamPlugin.getBukkitUtil();
		this.log = teamPlugin.getLog();
	}

	public IPlayerManager fromString(String strategy)
	{
		IPlayerManager playerManager = null;
		if ("SQLite".equalsIgnoreCase(strategy))
		{
			if (bukkitUtil.getPlugin("SQLibrary") != null)
				playerManager = new PlayerManager(teamPlugin, new PlayerDataDB(teamPlugin));
			else
				this.log.error("Cannot use \"" + Configuration.STORAGE_TYPE + "\" for storage because plugin \"SQLibrary\" cannot be found!" +
						"\nSQLibrary can be found here: http://dev.bukkit.org/bukkit-plugins/sqlibrary/");
		}
		else if ("file".equalsIgnoreCase(Configuration.STORAGE_TYPE))
		{
			playerManager = new PlayerManager(teamPlugin, new PlayerDataFile(teamPlugin));
		}
		else
		{
			this.log.error("\"" + Configuration.STORAGE_TYPE + "\" is not a valid storage type");
		}
		if (playerManager == null)
		{
			this.log.info("Resorting to \"file\" storage type");
			playerManager = new PlayerManager(teamPlugin, new PlayerDataFile(teamPlugin));
		}
		return playerManager;
	}
}
