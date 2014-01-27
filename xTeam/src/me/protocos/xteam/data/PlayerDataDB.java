package me.protocos.xteam.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.data.translator.IDataTranslator;

public class PlayerDataDB implements IDataManager
{
	private Database db;

	public PlayerDataDB(TeamPlugin plugin)
	{
		this.db = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", plugin.getFolder(), "xTeam", ".db");
		this.open();
	}

	@Override
	public void open()
	{
		db.open();
	}

	@Override
	public void read()
	{
	}

	@Override
	public boolean isOpen()
	{
		return db.isOpen();
	}

	@Override
	public void write()
	{
	}

	@Override
	public void close()
	{
		db.close();
	}

	@Override
	public void initializeData()
	{
		try
		{
			db.insert("CREATE TABLE IF NOT EXISTS player_data(name VARCHAR(17) PRIMARY KEY, lastAttacked BIGINT, lastTeleported BIGINT, returnLocation TEXT);");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
	}

	@Override
	public void clearData()
	{
		try
		{
			db.insert("UPDATE player_data SET lastAttacked=0,lastTeleported=0,returnLocation=null;");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
	}

	@Override
	public <T> void setVariable(String playerName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		try
		{
			db.insert("UPDATE player_data SET " + variableName + "='" + strategy.decompile(variable) + "' WHERE name='" + playerName + "';");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
	}

	@Override
	public <T> T getVariable(String playerName, String variableName, IDataTranslator<T> strategy)
	{
		ResultSet set = null;
		try
		{
			db.insert("INSERT INTO player_data(name, lastAttacked, lastTeleported) " +
					"SELECT '" + playerName + "',0,0 " +
					"WHERE NOT EXISTS (SELECT * FROM player_data WHERE name='" + playerName + "');");
			set = db.query("SELECT " + variableName + " FROM player_data WHERE name='" + playerName + "';");
			if (set == null || set.getString(1) == null)
				return null;
			return strategy.compile(set.getString(1));
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		return null;
	}
}
