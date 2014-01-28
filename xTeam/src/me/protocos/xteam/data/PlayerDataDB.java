package me.protocos.xteam.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.exception.DataManagerNotOpenException;

public class PlayerDataDB implements IDataManager
{
	private boolean open = false;
	private Database db;

	public PlayerDataDB(TeamPlugin plugin)
	{
		this.db = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", plugin.getFolder(), "xTeam", ".db");
	}

	@Override
	public void open()
	{
		db.open();
		open = true;
		this.initializeData();
		this.clearData();
	}

	@Override
	public void read()
	{
		if (!open)
			throw new DataManagerNotOpenException();
	}

	@Override
	public boolean isOpen()
	{
		return open;
	}

	@Override
	public void write()
	{
		if (!open)
			throw new DataManagerNotOpenException();
	}

	@Override
	public void close()
	{
		db.close();
		open = false;
	}

	@Override
	public void initializeData()
	{
		if (open)
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
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public void clearData()
	{
		if (open)
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
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public <T> void setVariable(String playerName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		if (open)
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
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public <T> T getVariable(String playerName, String variableName, IDataTranslator<T> strategy)
	{
		if (open)
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
		throw new DataManagerNotOpenException();
	}
}
