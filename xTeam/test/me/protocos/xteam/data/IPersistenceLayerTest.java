package me.protocos.xteam.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import org.junit.*;

public class IPersistenceLayerTest
{
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private IPersistenceLayer persistenceLayer;
	private List<String> teamDataBefore;
	private List<String> teamDataAfter;
	private List<String> playerDataBefore;
	private List<String> playerDataAfter;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
		playerFactory.getPlayer("protocos").setLastKnownLocation(new FakeLocation());
		playerFactory.getPlayer("kmlanglois").setLastKnownLocation(new FakeLocation());
	}

	@Test
	public void ShouldBeFlatFileDataPersistence()
	{
		//ASSEMBLE
		persistenceLayer = new FlatFileDataManager(teamPlugin, teamCoordinator, playerFactory);
		//ACT
		writeAndRead();
		//ASSERT
		Assert.assertEquals(teamDataBefore, teamDataAfter);
		Assert.assertEquals(playerDataBefore, playerDataAfter);
	}

	@Test
	public void ShouldBeSQLiteDataPersistence() throws SQLException
	{
		//ASSEMBLE
		Database db = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", "./test/", "xTeam", ".db");
		openDatabase(db);
		persistenceLayer = new SQLDataManager(teamPlugin, db, teamCoordinator, playerFactory);
		//ACT
		writeAndRead();
		//ASSERT
		Assert.assertEquals(teamDataBefore, teamDataAfter);
		Assert.assertEquals(playerDataBefore, playerDataAfter);
		closeDatabase(db);
	}

	@Test
	public void ShouldBeSQLiteDataPersistenceConvertFromOldTable() throws SQLException
	{
		//ASSEMBLE
		Database db = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", "./test/", "xTeam", ".db");
		openDatabase(db);
		PreparedStatement statement = db.prepare("CREATE TABLE IF NOT EXISTS player_data(name VARCHAR(17) PRIMARY KEY, lastAttacked BIGINT, lastTeleported BIGINT, returnLocation TEXT);");
		statement.execute();
		statement.close();
		persistenceLayer = new SQLDataManager(teamPlugin, db, teamCoordinator, playerFactory);
		//ACT
		writeAndRead();
		//ASSERT
		Assert.assertEquals(teamDataBefore, teamDataAfter);
		Assert.assertEquals(playerDataBefore, playerDataAfter);
		closeDatabase(db);
	}

	@Ignore
	@Test
	public void ShouldBeMySQLDataPersistence() throws SQLException
	{
		//ASSEMBLE
		Database db = new MySQL(Logger.getLogger("Minecraft"), "[xTeam] ", "localhost", 8889, "xteam", "root", "root");
		openDatabase(db);
		persistenceLayer = new SQLDataManager(teamPlugin, db, teamCoordinator, playerFactory);
		//ACT
		writeAndRead();
		//ASSERT
		Assert.assertEquals(teamDataBefore, teamDataAfter);
		Assert.assertEquals(playerDataBefore, playerDataAfter);
		closeDatabase(db);
	}

	private void openDatabase(Database db) throws SQLException
	{
		db.open();
		PreparedStatement statement = db.prepare("DROP TABLE IF EXISTS team_data;");
		statement.execute();
		statement.close();
		statement = db.prepare("DROP TABLE IF EXISTS player_data;");
		statement.execute();
		statement.close();
	}

	private void closeDatabase(Database db)
	{
		if (db != null)
			db.close();
	}

	private void writeAndRead()
	{
		teamDataBefore = teamCoordinator.exportData();
		playerDataBefore = playerFactory.exportData();
		persistenceLayer.write();
		persistenceLayer.read();
		teamDataAfter = teamCoordinator.exportData();
		playerDataAfter = playerFactory.exportData();
	}

	@After
	public void takedown()
	{
	}
}