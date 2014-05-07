package me.protocos.xteam.data;

import me.protocos.xteam.TeamPlugin;
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

	public IDataManager fromString(String strategy)
	{
		IDataManager playerDataManager = null;
		if ("SQLite".equalsIgnoreCase(strategy))
		{
			if (bukkitUtil.getPlugin("SQLibrary") != null)
				playerDataManager = new PlayerDataDB(teamPlugin);
			else
				this.log.error("Cannot use \"" + Configuration.STORAGE_TYPE + "\" for storage because plugin \"SQLibrary\" cannot be found!" +
						"\nSQLibrary can be found here: http://dev.bukkit.org/bukkit-plugins/sqlibrary/");
		}
		else if ("file".equalsIgnoreCase(Configuration.STORAGE_TYPE))
		{
			playerDataManager = new PlayerDataFile(teamPlugin);
		}
		else
		{
			this.log.error("\"" + Configuration.STORAGE_TYPE + "\" is not a valid storage type");
		}
		if (playerDataManager == null)
		{
			this.log.info("Resorting to \"file\" storage type");
			playerDataManager = new PlayerDataFile(teamPlugin);
		}
		return playerDataManager;
	}
}
