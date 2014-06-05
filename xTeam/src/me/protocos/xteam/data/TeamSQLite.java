package me.protocos.xteam.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lib.PatPeter.SQLibrary.Database;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;

public class TeamSQLite implements IDataManager
{
	private TeamPlugin teamPlugin;
	private ITeamManager teamManager;
	private Database db;
	private ILog log;

	public TeamSQLite(TeamPlugin teamPlugin, Database db, ITeamManager teamManager)
	{
		this.teamPlugin = teamPlugin;
		this.teamManager = teamManager;
		this.db = db;
		this.log = teamPlugin.getLog();
	}

	@Override
	public void read()
	{
		this.initializeData();
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
			resultSet.close();
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@Override
	public void write()
	{
		this.initializeData();
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
				statement.close();
			}
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@Override
	public void initializeData()
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

	@Override
	public void updateEntry(String key, PropertyList properties)
	{
		try
		{
			ITeam team = Team.generateTeamFromProperties(teamPlugin, properties.toString());
			//			PreparedStatement statement = db.prepare("SELECT name FROM team_data WHERE name = ?");
			//			statement.setString(1, team.getName());
			//			ResultSet resultSet = db.query(statement);
			//			System.out.println("............." + resultSet.getString(1));
			PreparedStatement statement = db.prepare("INSERT INTO team_data (name, tag, openJoining, defaultTeam, timeheadquartersLastSet, headquarters, leader, admins, players) VALUES(?,?,?,?,?,?,?,?,?);");
			statement.setString(1, team.getName());
			statement.setString(2, team.getTag());
			statement.setBoolean(3, team.isOpenJoining());
			statement.setBoolean(4, team.isDefaultTeam());
			statement.setLong(5, team.getTimeHeadquartersLastSet());
			statement.setString(6, team.getHeadquarters().toString());
			statement.setString(7, team.getLeader());
			statement.setString(8, CommonUtil.concatenate(team.getAdmins(), ","));
			statement.setString(9, CommonUtil.concatenate(team.getPlayers(), ","));
			db.insert(statement);
			statement.close();
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@Override
	public void removeEntry(String key)
	{
		try
		{
			PreparedStatement statement = db.prepare("DELETE FROM team_data WHERE name = ?");
			statement.setString(1, key);
			db.insert(statement);
			statement.close();
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@Override
	public <T> void setVariable(String teamName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		try
		{
			db.insert("UPDATE team_data SET " + variableName + " = '" + strategy.decompile(variable) + "' WHERE name = '" + teamName + "';");
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@Override
	public <T> T getVariable(String teamName, String variableName, IDataTranslator<T> strategy)
	{
		try
		{
			ResultSet resultSet = db.query("SELECT " + variableName + " FROM team_data WHERE name = '" + teamName + "';");
			T result = strategy.compile(resultSet.getString(1));
			resultSet.close();
			return result;
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
		return null;
	}
}
