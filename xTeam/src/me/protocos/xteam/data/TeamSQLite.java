package me.protocos.xteam.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.exception.DataManagerNotOpenException;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;

public class TeamSQLite implements IDataManager
{
	private boolean open = false;
	private TeamPlugin teamPlugin;
	private ITeamManager teamManager;
	private Database db;
	private ILog log;

	public TeamSQLite(TeamPlugin teamPlugin, ITeamManager teamManager)
	{
		this.teamPlugin = teamPlugin;
		this.teamManager = teamManager;
		this.db = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", teamPlugin.getFolder(), "xTeam", ".db");
		this.log = teamPlugin.getLog();
	}

	@Override
	public void open()
	{
		if (!open)
			db.open();
		open = true;
		this.initializeData();
	}

	@Override
	public void read()
	{
		if (!open)
			throw new DataManagerNotOpenException();
		try
		{
			ResultSet resultSet = db.query("SELECT * FROM team_data;");
			while (resultSet.next())
			{
				PropertyList properties = new PropertyList();
				properties.put("name", resultSet.getObject("name"));
				properties.put("tag", resultSet.getObject("tag"));
				properties.put("openJoining", resultSet.getObject("openJoining"));
				properties.put("defaultTeam", resultSet.getObject("defaultTeam"));
				properties.put("timeHeadquartersLastSet", resultSet.getObject("timeHeadquartersLastSet"));
				properties.put("headquarters", resultSet.getObject("headquarters"));
				properties.put("leader", resultSet.getObject("leader"));
				properties.put("admins", resultSet.getObject("admins"));
				properties.put("players", resultSet.getObject("players"));
				ITeam team = Team.generateTeamFromProperties(teamPlugin, properties.toString());
				teamManager.updateTeam(team);
			}
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
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
		try
		{
			for (ITeam team : teamManager.getTeams())
			{
				PreparedStatement statement = db.prepare("UPDATE team_data SET tag = ?, openJoining = ?, defaultTeam = ?, timeHeadquartersLastSet = ?, headquarters = ?, leader = ?, admins = ?, players = ? WHERE name = ?;");
				statement.setString(1, team.getTag());
				statement.setBoolean(2, team.isOpenJoining());
				statement.setBoolean(3, team.isDefaultTeam());
				statement.setLong(4, team.getTimeHeadquartersLastSet());
				statement.setString(5, team.getHeadquarters().toString());
				statement.setString(6, team.getLeader());
				statement.setString(7, CommonUtil.concatenate(team.getAdmins(), ","));
				statement.setString(8, CommonUtil.concatenate(team.getPlayers(), ","));
				db.insert(statement);
			}
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@Override
	public void close()
	{
		if (open)
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
				db.insert("CREATE TABLE IF NOT EXISTS team_data(name TEXT PRIMARY KEY, tag TEXT, openJoining BOOLEAN, defaultTeam BOOLEAN, timeHeadquartersLastSet BIGINT, headquarters TEXT, leader TEXT, admins TEXT, players TEXT);");
			}
			catch (SQLException e)
			{
				log.exception(e);
			}
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public <T> void setVariable(String teamName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		if (open)
		{
			try
			{
				db.insert("UPDATE player_data SET " + variableName + " = '" + strategy.decompile(variable) + "' WHERE name = '" + teamName + "';");
			}
			catch (SQLException e)
			{
				log.exception(e);
			}
			this.read();
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public <T> T getVariable(String teamName, String variableName, IDataTranslator<T> strategy)
	{
		if (open)
		{
			try
			{
				ResultSet resultSet = db.query("SELECT " + variableName + " FROM team_data WHERE name = '" + teamName + "';");
				return strategy.compile(resultSet.getString(1));
			}
			catch (SQLException e)
			{
				log.exception(e);
			}
		}
		throw new DataManagerNotOpenException();
	}
}
