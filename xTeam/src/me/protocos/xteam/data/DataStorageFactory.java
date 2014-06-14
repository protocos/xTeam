package me.protocos.xteam.data;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.BukkitUtil;

public class DataStorageFactory
{
	private static IPersistenceLayer persistenceLayer;
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;
	private ILog log;

	public DataStorageFactory(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.bukkitUtil = teamPlugin.getBukkitUtil();
		this.log = teamPlugin.getLog();
	}

	public static void open()
	{
		if (persistenceLayer != null)
			persistenceLayer.open();
	}

	public static void close()
	{
		if (persistenceLayer != null)
			persistenceLayer.close();
	}

	public IPersistenceLayer dataManagerFromString(String strategy)
	{
		if (strategy.toLowerCase().startsWith("sqlite"))
		{
			if (bukkitUtil.getPlugin("SQLibrary") != null)
			{
				try
				{
					persistenceLayer = new SQLDataManager(teamPlugin, SQLDataManager.databaseFrom(teamPlugin, strategy), teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
					this.log.debug("Using SQLite as storage type");
				}
				catch (Exception e)
				{
					this.log.error("There was an error loading the SQLite database");
					this.log.exception(e);
				}
			}
			else
				this.log.error("Cannot use SQLite for storage because plugin SQLibrary cannot be found!" +
						"\nSQLibrary can be found here: http://dev.bukkit.org/bukkit-plugins/sqlibrary/");
		}
		else if (strategy.toLowerCase().startsWith("mysql"))
		{
			if (bukkitUtil.getPlugin("SQLibrary") != null)
			{
				try
				{
					persistenceLayer = new SQLDataManager(teamPlugin, SQLDataManager.databaseFrom(teamPlugin, strategy), teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
					this.log.debug("Using MySQL as storage type");
				}
				catch (Exception e)
				{
					this.log.error("There was an error loading the MySQL database");
					this.log.exception(e);
				}
			}
			else
				this.log.error("Cannot use MySQL for storage because plugin SQLibrary cannot be found!" +
						"\nSQLibrary can be found here: http://dev.bukkit.org/bukkit-plugins/sqlibrary/");
		}
		else if (strategy.toLowerCase().startsWith("file"))
		{
			persistenceLayer = new FlatFileDataManager(teamPlugin, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
			this.log.debug("Using File as storage type");
		}
		else
		{
			this.log.error("'" + strategy + "' is not a valid storage type");
		}
		if (persistenceLayer == null)
		{
			persistenceLayer = new FlatFileDataManager(teamPlugin, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
			this.log.error("Resorting to File as storage type");
		}
		return persistenceLayer;
	}
}
