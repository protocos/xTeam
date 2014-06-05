package me.protocos.xteam.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import lib.PatPeter.SQLibrary.Database;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.model.ILog;

public class PlayerSQLite implements IDataManager
{
	private Database db;
	private ILog log;

	public PlayerSQLite(TeamPlugin teamPlugin, Database db)
	{
		this.db = db;
		this.log = teamPlugin.getLog();
	}

	@Override
	public void read()
	{
	}

	@Override
	public void write()
	{
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
			log.exception(e);
		}
	}

	@Override
	public void updateEntry(String name, PropertyList properties)
	{
		String keys = "", values = "";
		boolean first = true;
		for (Property property : properties)
		{
			String key = property.getKey();
			String value = property.getValue();
			if (!first)
			{
				keys += ",";
				values += ",";
			}
			keys += key;
			values += "'" + value + "'";
			first = false;
		}
		try
		{
			db.insert("INSERT INTO player_data(" + keys + ") VALUES (" + values + ");");
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@Override
	public void removeEntry(String name)
	{
		try
		{
			db.insert("DELETE FROM player_data WHERE name='" + name + "';");
		}
		catch (SQLException e)
		{
			log.exception(e);
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
			log.exception(e);
		}
	}

	@Override
	public <T> T getVariable(String playerName, String variableName, IDataTranslator<T> strategy)
	{
		try
		{
			db.insert("INSERT INTO player_data(name, lastAttacked, lastTeleported) " +
					"SELECT '" + playerName + "',0,0 " +
					"WHERE NOT EXISTS (SELECT * FROM player_data WHERE name='" + playerName + "');");
			ResultSet set = db.query("SELECT " + variableName + " FROM player_data WHERE name='" + playerName + "';");
			if (set == null || set.getString(1) == null)
			{

				return null;
			}
			T result = strategy.compile(set.getString(1));
			set.close();
			return result;
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
		return null;
	}
}
