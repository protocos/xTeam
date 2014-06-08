package me.protocos.xteam.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import org.junit.*;

public class IPersistenceLayerTest
{
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private IPersistenceLayer persistenceLayer;
	private List<String> teamDataBefore;
	private List<String> teamDataAfter;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
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
	}

	@Test
	public void ShouldBeSQLiteDataPersistence() throws SQLException
	{
		//ASSEMBLE
		Database db = new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", "./test/", "xTeam", ".db");
		initializeDatabase(db);
		persistenceLayer = new SQLDataManager(teamPlugin, db, teamCoordinator, playerFactory);
		//ACT
		writeAndRead();
		//ASSERT
		Assert.assertEquals(teamDataBefore, teamDataAfter);
		closeDatabase(db);
	}

	@Ignore
	@Test
	public void ShouldBeMySQLDataPersistence() throws SQLException
	{
		//ASSEMBLE
		Database db = new MySQL(Logger.getLogger("Minecraft"), "[xTeam] ", "localhost", 8889, "xteam", "root", "root");
		initializeDatabase(db);
		persistenceLayer = new SQLDataManager(teamPlugin, db, teamCoordinator, playerFactory);
		//ACT
		writeAndRead();
		//ASSERT
		Assert.assertEquals(teamDataBefore, teamDataAfter);
		closeDatabase(db);
	}

	private void initializeDatabase(Database db) throws SQLException
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
		persistenceLayer.write();
		persistenceLayer.read();
		teamDataAfter = teamCoordinator.exportData();
	}

	@After
	public void takedown()
	{
	}
}