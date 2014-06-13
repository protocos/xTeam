package me.protocos.xteam.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.translator.IntegerDataTranslator;
import me.protocos.xteam.data.translator.LongDataTranslator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.event.*;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;

public class SQLDataManager implements IPersistenceLayer, IEventHandler
{
	private Database db;
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private ILog log;

	public SQLDataManager(TeamPlugin teamPlugin, Database db, ITeamCoordinator teamCoordinator, IPlayerFactory playerFactory)
	{
		this.teamPlugin = teamPlugin;
		this.db = db;
		this.open();
		this.teamCoordinator = teamCoordinator;
		this.playerFactory = playerFactory;
		this.log = teamPlugin.getLog();
		try
		{
			insert("CREATE TABLE IF NOT EXISTS team_data(name VARCHAR(255) PRIMARY KEY, tag TEXT, openJoining BOOLEAN, defaultTeam BOOLEAN, timeHeadquartersLastSet BIGINT, headquarters TEXT, leader TEXT, admins TEXT, players TEXT);");
			insert("CREATE TABLE IF NOT EXISTS player_data(name VARCHAR(17) PRIMARY KEY, lastAttacked BIGINT, lastTeleported BIGINT, returnLocation TEXT, lastKnownLocation TEXT);");
			try
			{
				insert("ALTER TABLE player_data ADD lastKnownLocation TEXT");
			}
			catch (SQLException e)
			{
				//swallow it if the column already exists
			}
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
		teamPlugin.getEventDispatcher().addTeamListener(this);
	}

	public void open()
	{
		if (db != null)
		{
			db.open();
		}
	}

	public void close()
	{
		if (db != null)
		{
			db.close();
		}
	}

	@Override
	public void read()
	{
		try
		{
			ResultSet resultSet = query("SELECT * FROM team_data;");
			while (resultSet.next())
			{
				PropertyList propertyList = new PropertyList();
				propertyList.put("name", resultSet.getObject("name").toString());
				propertyList.put("tag", resultSet.getObject("tag").toString());
				propertyList.put("openJoining", resultSet.getObject("openJoining").toString());
				propertyList.put("defaultTeam", resultSet.getObject("defaultTeam").toString());
				propertyList.put("timeHeadquartersLastSet", resultSet.getObject("timeHeadquartersLastSet").toString());
				propertyList.put("headquarters", resultSet.getObject("headquarters").toString());
				propertyList.put("leader", resultSet.getObject("leader").toString());
				propertyList.put("admins", resultSet.getObject("admins").toString());
				propertyList.put("players", resultSet.getObject("players").toString());
				ITeam team = Team.generateTeamFromProperties(teamPlugin, propertyList.toString());
				teamCoordinator.putTeam(team);
			}
			resultSet.close();
			resultSet = query("SELECT * FROM player_data;");
			while (resultSet.next())
			{
				PropertyList propertyList = new PropertyList();
				propertyList.put("name", resultSet.getObject("name").toString());
				propertyList.put("lastAttacked", resultSet.getObject("lastAttacked").toString());
				propertyList.put("lastTeleported", resultSet.getObject("lastTeleported").toString());
				propertyList.put("returnLocation", resultSet.getObject("returnLocation").toString());
				propertyList.put("lastKnownLocation", resultSet.getObject("lastKnownLocation").toString());
				playerFactory.updateValues(propertyList);
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
		try
		{
			for (ITeam team : teamCoordinator.getTeams())
			{
				PreparedStatement checkTeamInDatabase = prepare("SELECT * FROM team_data WHERE name = ?;");
				checkTeamInDatabase.setString(1, team.getName());
				ResultSet resultSet = query(checkTeamInDatabase);
				PreparedStatement statement;
				if (resultSet.next())
				{
					statement = prepare("UPDATE team_data SET tag = ?, openJoining = ?, defaultTeam = ?, timeHeadquartersLastSet = ?, headquarters = ?, leader = ?, admins = ?, players = ? WHERE name = ?;");
				}
				else
				{
					statement = prepare("INSERT INTO team_data(tag, openJoining, defaultTeam, timeHeadquartersLastSet, headquarters, leader, admins, players, name) VALUES(?,?,?,?,?,?,?,?,?);");
				}
				insertTeamDataIntoStatement(statement, team);
				insert(statement);
				checkTeamInDatabase.close();
				resultSet.close();
				statement.close();
			}
			for (String playerData : playerFactory.exportData())
			{
				PropertyList properties = PropertyList.fromString(playerData);
				PreparedStatement checkPlayerInDatabase = prepare("SELECT * FROM player_data WHERE name = ?;");
				checkPlayerInDatabase.setString(1, properties.get("name").getValue());
				ResultSet resultSet = query(checkPlayerInDatabase);
				PreparedStatement statement;
				if (resultSet.next())
				{
					statement = prepare("UPDATE player_data SET lastAttacked = ?, lastTeleported = ?, returnLocation = ?, lastKnownLocation = ? WHERE name = ?;");
				}
				else
				{
					statement = prepare("INSERT INTO player_data(lastAttacked, lastTeleported, returnLocation, lastKnownLocation, name) VALUES(?,?,?,?,?);");
				}
				statement.setLong(1, properties.getAsType("lastAttacked", new LongDataTranslator()));
				statement.setLong(2, properties.getAsType("lastTeleported", new LongDataTranslator()));
				statement.setString(3, properties.getAsString("returnLocation"));
				statement.setString(4, properties.getAsString("lastKnownLocation"));
				statement.setString(5, properties.getAsString("name"));
				insert(statement);
				checkPlayerInDatabase.close();
				resultSet.close();
				statement.close();
			}
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@TeamEvent
	public void onCreate(TeamCreateEvent event)
	{
		try
		{
			ITeam team = event.getTeam();
			PreparedStatement statement = prepare("INSERT INTO team_data(tag, openJoining, defaultTeam, timeHeadquartersLastSet, headquarters, leader, admins, players, name) VALUES(?,?,?,?,?,?,?,?,?);");
			//			PreparedStatement statement = db.getConnection().prepareStatement("INSERT INTO team_data(tag, openJoining, defaultTeam, timeHeadquartersLastSet, headquarters, leader, admins, players, name) VALUES(?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			insertTeamDataIntoStatement(statement, team);
			insert(statement);
			statement.close();
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@TeamEvent
	public void onRename(TeamRenameEvent event)
	{
		try
		{
			PreparedStatement statement = prepare("DELETE FROM team_data WHERE name = ?;");
			statement.setString(1, event.getOldName());
			insert(statement);
			ITeam team = event.getTeam();
			statement = prepare("INSERT INTO team_data(tag, openJoining, defaultTeam, timeHeadquartersLastSet, headquarters, leader, admins, players, name) VALUES(?,?,?,?,?,?,?,?,?);");
			insertTeamDataIntoStatement(statement, team);
			insert(statement);
			statement.close();
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	@TeamEvent
	public void onDisband(TeamDisbandEvent event)
	{
		try
		{
			PreparedStatement statement = prepare("DELETE FROM team_data WHERE name = ?;");
			statement.setString(1, event.getTeamName());
			insert(statement);
			statement.close();
		}
		catch (SQLException e)
		{
			log.exception(e);
		}
	}

	private PreparedStatement prepare(String prepare) throws SQLException
	{
		return db.getConnection().prepareStatement(prepare, Statement.RETURN_GENERATED_KEYS);
	}

	private ResultSet query(String statement) throws SQLException
	{
		return prepare(statement).executeQuery();
	}

	private ResultSet query(PreparedStatement statement) throws SQLException
	{
		return statement.executeQuery();
	}

	private boolean insert(String statement) throws SQLException
	{
		return prepare(statement).execute();
	}

	private boolean insert(PreparedStatement statement) throws SQLException
	{
		return statement.execute();
	}

	private void insertTeamDataIntoStatement(PreparedStatement statement, ITeam team) throws SQLException
	{
		statement.setString(1, team.getTag());
		statement.setBoolean(2, team.isOpenJoining());
		statement.setBoolean(3, team.isDefaultTeam());
		statement.setLong(4, team.getTimeHeadquartersLastSet());
		statement.setString(5, team.getHeadquarters().toString());
		statement.setString(6, team.getLeader());
		statement.setString(7, CommonUtil.concatenate(team.getAdmins(), ","));
		statement.setString(8, CommonUtil.concatenate(team.getPlayers(), ","));
		statement.setString(9, team.getName());
	}

	public static Database databaseFrom(TeamPlugin teamPlugin, String strategy)
	{
		Database database = null;
		if (strategy.toLowerCase().startsWith("sqlite"))
		{
			database = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", teamPlugin.getFolder(), "xTeam", ".db");
		}
		else if (strategy.toLowerCase().startsWith("mysql"))
		{
			PropertyList props = parse(strategy);
			database = new MySQL(Logger.getLogger("Minecraft"), "[xTeam] ",
					props.get("host").getValue(),
					props.get("port").getValueUsing(new IntegerDataTranslator()),
					props.get("databasename").getValue(),
					props.get("username").getValue(),
					props.get("password").getValue());
		}
		return database;
	}

	private static PropertyList parse(String mysql)
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
