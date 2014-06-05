package me.protocos.xteam.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerDataFileTest
{
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;
	private PlayerFlatFile playerData;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Test
	public void ShouldBeWriteThenRead()
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(bukkitUtil.getWorld("world")).toLocation();
		playerData = new PlayerFlatFile(teamPlugin);
		//ACT
		playerData.setVariable("protocos", "returnLocation", originalLocation, new LocationDataTranslator(teamPlugin));
		playerData.write();
		playerData.read();
		Location returnLocation = playerData.getVariable("protocos", "returnLocation", new LocationDataTranslator(teamPlugin));
		//ASSERT
		Assert.assertEquals(originalLocation, returnLocation);
	}

	@Test
	public void ShouldBeReadCurruptedFileExample1() throws IOException
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(bukkitUtil.getWorld("world")).toLocation();
		BufferedWriter writer = new BufferedWriter(new FileWriter(SystemUtil.ensureFile("test/players.txt")));
		writer.write("name:protocos lastAttacked:0 lastTeleported:0:name returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.write("name:kmlanglois lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.write("name:mastermind lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.close();
		playerData = new PlayerFlatFile(teamPlugin);
		//ACT
		playerData.read();
		Location protocosLocation = playerData.getVariable("protocos", "returnLocation", new LocationDataTranslator(teamPlugin));
		Location kmlangloisLocation = playerData.getVariable("kmlanglois", "returnLocation", new LocationDataTranslator(teamPlugin));
		Location mastermindLocation = playerData.getVariable("mastermind", "returnLocation", new LocationDataTranslator(teamPlugin));
		playerData.write();
		//ASSERT
		Assert.assertNull(protocosLocation);
		Assert.assertEquals(originalLocation, kmlangloisLocation);
		Assert.assertEquals(originalLocation, mastermindLocation);
	}

	@Test
	public void ShouldBeReadCurruptedFileExample2() throws IOException
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(bukkitUtil.getWorld("world")).toLocation();
		BufferedWriter writer = new BufferedWriter(new FileWriter(SystemUtil.ensureFile("test/players.txt")));
		writer.write("name:protocos lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0ll\n");
		writer.write("name:kmlanglois lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.write("name:mastermind lastAttacked:0 lastTeleported:0 returnLocation:world,0.0,0.0,0.0,0.0,0.0\n");
		writer.close();
		playerData = new PlayerFlatFile(teamPlugin);
		//ACT
		playerData.read();
		Location protocosLocation = playerData.getVariable("protocos", "returnLocation", new LocationDataTranslator(teamPlugin));
		Location kmlangloisLocation = playerData.getVariable("kmlanglois", "returnLocation", new LocationDataTranslator(teamPlugin));
		Location mastermindLocation = playerData.getVariable("mastermind", "returnLocation", new LocationDataTranslator(teamPlugin));
		playerData.write();
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