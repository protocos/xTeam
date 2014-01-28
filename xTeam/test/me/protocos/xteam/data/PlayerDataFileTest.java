package me.protocos.xteam.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.data.translator.LongDataTranslator;
import me.protocos.xteam.exception.DataManagerNotOpenException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeServer;
import me.protocos.xteam.fakeobjects.FakeTeamPlugin;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerDataFileTest
{
	private TeamPlugin plugin;
	private PlayerDataFile playerData;

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
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.open();
		//ASSERT
		Assert.assertTrue(playerData.isOpen());
	}

	@Test
	public void ShouldBeClosed()
	{
		//ASSEMBLE
		playerData = new PlayerDataFile(plugin);
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
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.initializeData();
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeClearWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.clearData();
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeSetWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.setVariable("protocos", "lastAttacked", 10L, new LongDataTranslator());
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeGetWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.getVariable("protocos", "lastAttacked", new LongDataTranslator());
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeReadWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.read();
		//ASSERT
	}

	@Test(expected = DataManagerNotOpenException.class)
	public void ShouldBeWriteWithoutOpen()
	{
		//ASSEMBLE
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.write();
		//ASSERT
	}

	@Test
	public void ShouldBeWriteThenRead()
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(BukkitUtil.getWorld("world")).toLocation();
		playerData = new PlayerDataFile(plugin);
		playerData.open();
		//ACT
		playerData.setVariable("protocos", "returnLocation", originalLocation, new LocationDataTranslator());
		playerData.write();
		playerData.read();
		Location returnLocation = playerData.getVariable("protocos", "returnLocation", new LocationDataTranslator());
		//ASSERT
		Assert.assertEquals(originalLocation, returnLocation);
	}

	@Test
	public void ShouldBeReadCurruptedFileExample1() throws IOException
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(BukkitUtil.getWorld("world")).toLocation();
		BufferedWriter writer = new BufferedWriter(new FileWriter(SystemUtil.ensureFile("test/players.txt")));
		writer.write("name:protocos lastAttacked:0 lastTeleported:0:name returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.write("name:kmlanglois lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.write("name:mastermind lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.close();
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.open();
		playerData.read();
		Location protocosLocation = playerData.getVariable("protocos", "returnLocation", new LocationDataTranslator());
		Location kmlangloisLocation = playerData.getVariable("kmlanglois", "returnLocation", new LocationDataTranslator());
		Location mastermindLocation = playerData.getVariable("mastermind", "returnLocation", new LocationDataTranslator());
		playerData.write();
		playerData.close();
		//ASSERT
		Assert.assertNull(protocosLocation);
		Assert.assertEquals(originalLocation, kmlangloisLocation);
		Assert.assertEquals(originalLocation, mastermindLocation);
	}

	@Test
	public void ShouldBeReadCurruptedFileExample2() throws IOException
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(BukkitUtil.getWorld("world")).toLocation();
		BufferedWriter writer = new BufferedWriter(new FileWriter(SystemUtil.ensureFile("test/players.txt")));
		writer.write("name:protocos lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0ll\n");
		writer.write("name:kmlanglois lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.write("name:mastermind lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.close();
		playerData = new PlayerDataFile(plugin);
		//ACT
		playerData.open();
		playerData.read();
		Location protocosLocation = playerData.getVariable("protocos", "returnLocation", new LocationDataTranslator());
		Location kmlangloisLocation = playerData.getVariable("kmlanglois", "returnLocation", new LocationDataTranslator());
		Location mastermindLocation = playerData.getVariable("mastermind", "returnLocation", new LocationDataTranslator());
		playerData.write();
		playerData.close();
		//ASSERT
		Assert.assertNull(protocosLocation);
		Assert.assertEquals(originalLocation, kmlangloisLocation);
		Assert.assertEquals(originalLocation, mastermindLocation);
	}

	@After
	public void takedown()
	{
	}
}