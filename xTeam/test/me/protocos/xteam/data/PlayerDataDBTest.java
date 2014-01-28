package me.protocos.xteam.data;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.data.translator.LongDataTranslator;
import me.protocos.xteam.exception.DataManagerNotOpenException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeServer;
import me.protocos.xteam.fakeobjects.FakeTeamPlugin;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerDataDBTest
{
	private TeamPlugin plugin;
	private PlayerDataDB playerData;

	@Before
	public void setup()
	{
		BukkitUtil.setServer(new FakeServer());
		plugin = new FakeTeamPlugin();
		plugin.onEnable();
	}

	@Test
	public void ShouldBeOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataDB(plugin);
		//ACT
		playerData.open();
		//ASSERT
		Assert.assertTrue(playerData.isOpen());
	}

	@Test
	public void ShouldBeClosed()
	{
		//ASSEMBLE
		playerData = new PlayerDataDB(plugin);
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
		playerData = new PlayerDataDB(plugin);
		//ACT
		playerData.initializeData();
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeClearWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataDB(plugin);
		//ACT
		playerData.clearData();
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeSetWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataDB(plugin);
		//ACT
		playerData.setVariable("protocos", "lastAttacked", 10L, new LongDataTranslator());
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeGetWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataDB(plugin);
		//ACT
		playerData.getVariable("protocos", "lastAttacked", new LongDataTranslator());
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeReadWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataDB(plugin);
		//ACT
		playerData.read();
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeWriteWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataDB(plugin);
		//ACT
		playerData.write();
		//ASSERT
	}

	@Test
	public void ShouldBeWriteThenRead()
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(BukkitUtil.getWorld("world")).toLocation();
		playerData = new PlayerDataDB(plugin);
		playerData.open();
		//ACT
		playerData.setVariable("protocos", "returnLocation", originalLocation, new LocationDataTranslator());
		playerData.write();
		playerData.read();
		Location returnLocation = playerData.getVariable("protocos", "returnLocation", new LocationDataTranslator());
		//ASSERT
		Assert.assertEquals(originalLocation, returnLocation);
	}

	@After
	public void takedown()
	{
	}
}