package me.protocos.xteam.data;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.data.translator.LongDataTranslator;
import me.protocos.xteam.exception.DataManagerNotOpenException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerDataDBTest
{
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;
	private PlayerSQLite playerData;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Test
	public void ShouldBeOpen()
	{
		//ASSEMBLE
		playerData = new PlayerSQLite(teamPlugin);
		//ACT
		playerData.open();
		//ASSERT
		Assert.assertTrue(playerData.isOpen());
	}

	@Test
	public void ShouldBeClosed()
	{
		//ASSEMBLE
		playerData = new PlayerSQLite(teamPlugin);
		playerData.open();
		//ACT
		playerData.close();
		//ASSERT
		Assert.assertFalse(playerData.isOpen());
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeInitializeWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerSQLite(teamPlugin);
		//ACT
		playerData.initializeData();
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeSetWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerSQLite(teamPlugin);
		//ACT
		playerData.setVariable("protocos", "lastAttacked", 10L, new LongDataTranslator());
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeGetWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerSQLite(teamPlugin);
		//ACT
		playerData.getVariable("protocos", "lastAttacked", new LongDataTranslator());
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeReadWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerSQLite(teamPlugin);
		//ACT
		playerData.read();
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeWriteWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerSQLite(teamPlugin);
		//ACT
		playerData.write();
		//ASSERT
	}

	@Test
	public void ShouldBeWriteThenRead()
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(bukkitUtil.getWorld("world")).toLocation();
		playerData = new PlayerSQLite(teamPlugin);
		playerData.open();
		//ACT
		playerData.setVariable("protocos", "returnLocation", originalLocation, new LocationDataTranslator(teamPlugin));
		playerData.write();
		playerData.read();
		Location returnLocation = playerData.getVariable("protocos", "returnLocation", new LocationDataTranslator(teamPlugin));
		//ASSERT
		Assert.assertEquals(originalLocation, returnLocation);
	}

	@After
	public void takedown()
	{
	}
}