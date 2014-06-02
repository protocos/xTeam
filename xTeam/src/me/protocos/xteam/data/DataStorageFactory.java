package me.protocos.xteam.data;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.BukkitUtil;

public class DataStorageFactory
{
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;
	private ILog log;

	public DataStorageFactory(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.bukkitUtil = teamPlugin.getBukkitUtil();
		this.log = teamPlugin.getLog();
	}

	public IDataManager playerManagerFromString(String strategy)
	{
		IDataManager playerDataManager = null;
		if ("SQLite".equalsIgnoreCase(strategy))
		{
			if (bukkitUtil.getPlugin("SQLibrary") != null)
				playerDataManager = new PlayerSQLite(teamPlugin);
			else
				this.log.error("Cannot use \"" + Configuration.STORAGE_TYPE + "\" for storage because plugin \"SQLibrary\" cannot be found!" +
						"\nSQLibrary can be found here: http://dev.bukkit.org/bukkit-plugins/sqlibrary/");
		}
		else if ("file".equalsIgnoreCase(Configuration.STORAGE_TYPE))
		{
			playerDataManager = new PlayerFlatFile(teamPlugin);
		}
		else
		{
			this.log.error("\"" + Configuration.STORAGE_TYPE + "\" is not a valid storage type");
		}
		if (playerDataManager == null)
		{
			this.log.info("Resorting to \"file\" storage type");
			playerDataManager = new PlayerFlatFile(teamPlugin);
		}
		return playerDataManager;
	}

	public IDataManager teamManagerFromString(String strategy)
	{
		IDataManager teamDataManager = null;
		if ("SQLite".equalsIgnoreCase(strategy))
		{
			if (bukkitUtil.getPlugin("SQLibrary") != null)
				teamDataManager = new TeamSQLite(teamPlugin, teamPlugin.getTeamManager());
			else
				this.log.error("Cannot use \"" + Configuration.STORAGE_TYPE + "\" for storage because plugin \"SQLibrary\" cannot be found!" +
						"\nSQLibrary can be found here: http://dev.bukkit.org/bukkit-plugins/sqlibrary/");
		}
		else if ("file".equalsIgnoreCase(Configuration.STORAGE_TYPE))
		{
			teamDataManager = new TeamFlatFile(teamPlugin, teamPlugin.getTeamManager());
		}
		else
		{
			this.log.error("\"" + Configuration.STORAGE_TYPE + "\" is not a valid storage type");
		}
		if (teamDataManager == null)
		{
			this.log.info("Resorting to \"file\" storage type");
			teamDataManager = new TeamFlatFile(teamPlugin, teamPlugin.getTeamManager());
		}
		return teamDataManager;
	}
}
