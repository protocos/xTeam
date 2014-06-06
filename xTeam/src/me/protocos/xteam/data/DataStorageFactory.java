package me.protocos.xteam.data;

import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.BukkitUtil;

public class DataStorageFactory
{
	private static Database db;
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;
	private ILog log;

	public DataStorageFactory(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.bukkitUtil = teamPlugin.getBukkitUtil();
		this.log = teamPlugin.getLog();
	}

	public static void closeDatabase()
	{
		if (db != null)
		{
			db.close();
		}
	}

	public IPersistenceLayer dataManagerFromString(String strategy)
	{
		IPersistenceLayer dataManager = null;
		if ("SQLite".equalsIgnoreCase(strategy))
		{
			if (bukkitUtil.getPlugin("SQLibrary") != null)
			{
				db = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", teamPlugin.getFolder(), "xTeam", ".db");
				db.open();
				dataManager = new SQLiteDataManager(teamPlugin, db, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
			}
			else
				this.log.error("Cannot use \"" + Configuration.STORAGE_TYPE + "\" for storage because plugin \"SQLibrary\" cannot be found!" +
						"\nSQLibrary can be found here: http://dev.bukkit.org/bukkit-plugins/sqlibrary/");
		}
		else if ("file".equalsIgnoreCase(Configuration.STORAGE_TYPE))
		{
			dataManager = new FlatFileDataManager(teamPlugin, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
		}
		else
		{
			this.log.error("\"" + Configuration.STORAGE_TYPE + "\" is not a valid storage type");
		}
		if (dataManager == null)
		{
			this.log.info("Resorting to \"file\" storage type");
			dataManager = new FlatFileDataManager(teamPlugin, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
		}
		return dataManager;
	}
}
