package me.protocos.xteam.data;

import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.translator.IntegerDataTranslator;
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
		if (strategy.toLowerCase().startsWith("sqlite"))
		{
			if (bukkitUtil.getPlugin("SQLibrary") != null)
			{
				try
				{
					db = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", teamPlugin.getFolder(), "xTeam", ".db");
					db.open();
					dataManager = new SQLDataManager(teamPlugin, db, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
					this.log.info("Using SQLite as storage type");
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
					PropertyList props = parse(strategy);
					db = new MySQL(Logger.getLogger("Minecraft"), "[xTeam] ",
							props.get("host").getValue(),
							props.get("port").getValueUsing(new IntegerDataTranslator()),
							props.get("databasename").getValue(),
							props.get("username").getValue(),
							props.get("password").getValue());
					db.open();
					dataManager = new SQLDataManager(teamPlugin, db, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
					this.log.info("Using MySQL as storage type");
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
			dataManager = new FlatFileDataManager(teamPlugin, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
			this.log.info("Using File as storage type");
		}
		else
		{
			this.log.error("" + strategy + " is not a valid storage type");
		}
		if (dataManager == null)
		{
			dataManager = new FlatFileDataManager(teamPlugin, teamPlugin.getTeamCoordinator(), teamPlugin.getPlayerFactory());
			this.log.info("Resorting to File storage type");
		}
		return dataManager;
	}

	private PropertyList parse(String mysql)
	{
		PropertyList propertyList = new PropertyList();
		String[] pieces = mysql.split(":");
		if (pieces.length != 6)
			throw new IllegalArgumentException(mysql);
		propertyList.put("host", pieces[1]);
		propertyList.put("port", pieces[2]);
		propertyList.put("databasename", pieces[3]);
		propertyList.put("username", pieces[4]);
		propertyList.put("password", pieces[5]);
		return propertyList;
	}
}
