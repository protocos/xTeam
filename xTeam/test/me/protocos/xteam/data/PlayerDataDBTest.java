package me.protocos.xteam.data;

import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.SQLite;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.translator.LocationDataTranslator;
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
		playerData = new PlayerSQLite(teamPlugin, new SQLite(Logger.getLogger("Minecraft"), "[xTeam] ", teamPlugin.getFolder(), "xTeam", ".db"));
	}

	@Test
	public void ShouldBeWriteThenRead()
	{
		//ASSEMBLE
		Location originalLocation = new FakeLocation(bukkitUtil.getWorld("world")).toLocation();
		playerData.read();
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