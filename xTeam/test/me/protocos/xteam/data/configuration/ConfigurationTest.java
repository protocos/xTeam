package me.protocos.xteam.data.configuration;

import java.io.FileWriter;
import java.io.IOException;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.SystemUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest
{
	private TeamPlugin teamPlugin;
	private Configuration configLoader;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		SystemUtil.ensureFolder("test/");
		SystemUtil.deleteFile("test/xTeam.cfg");
	}

	@Test
	public void ShouldBeDefaultConfigValues()
	{
		//ASSEMBLE
		loadConfig();
		//ACT
		//ASSERT
		Assert.assertFalse(Configuration.LOCATIONS_ENABLED);
		Assert.assertTrue(Configuration.CAN_CHAT);
		Assert.assertTrue(Configuration.HQ_ON_DEATH);
		Assert.assertTrue(Configuration.TEAM_WOLVES);
		Assert.assertFalse(Configuration.RANDOM_TEAM);
		Assert.assertFalse(Configuration.BALANCE_TEAMS);
		Assert.assertFalse(Configuration.DEFAULT_TEAM_ONLY);
		Assert.assertFalse(Configuration.DEFAULT_HQ_ON_JOIN);
		Assert.assertTrue(Configuration.TEAM_TAG_ENABLED);
		Assert.assertFalse(Configuration.TEAM_FRIENDLY_FIRE);
		Assert.assertFalse(Configuration.NO_PERMISSIONS);
		Assert.assertTrue(Configuration.ALPHA_NUM);
		Assert.assertTrue(Configuration.DISPLAY_COORDINATES);
		Assert.assertTrue(Configuration.DISPLAY_RELATIVE_COORDINATES);
		Assert.assertTrue(Configuration.SEND_ANONYMOUS_ERROR_REPORTS);
		Assert.assertEquals(10, Configuration.MAX_PLAYERS);
		Assert.assertEquals(0, Configuration.HQ_INTERVAL);
		Assert.assertEquals(16, Configuration.ENEMY_PROX);
		Assert.assertEquals(7, Configuration.TELE_DELAY);
		Assert.assertEquals(20, Configuration.CREATE_INTERVAL);
		Assert.assertEquals(15, Configuration.LAST_ATTACKED_DELAY);
		Assert.assertEquals(0, Configuration.TEAM_NAME_LENGTH);
		Assert.assertEquals(0, Configuration.MAX_NUM_LOCATIONS);
		Assert.assertEquals(0, Configuration.TELE_REFRESH_DELAY);
		Assert.assertEquals(2, Configuration.RALLY_DELAY);
		Assert.assertEquals(0, Configuration.SAVE_DATA_INTERVAL);
		Assert.assertEquals("green", Configuration.COLOR_TAG);
		Assert.assertEquals("dark_green", Configuration.COLOR_NAME);
		Assert.assertEquals("file", Configuration.STORAGE_TYPE);
		Assert.assertEquals("", CommonUtil.concatenate(Configuration.DEFAULT_TEAM_NAMES, ","));
		Assert.assertEquals("", CommonUtil.concatenate(Configuration.DISABLED_WORLDS, ","));
	}

	@Test
	public void ShouldBeCustomConfigValue() throws IOException
	{
		//ASSEMBLE
		customConfigValue("storagetype = sqlite");
		loadConfig();
		//ACT
		//ASSERT
		Assert.assertEquals("sqlite", Configuration.STORAGE_TYPE);
	}

	@Test
	public void ShouldBeRangeCheckRevertToDefaultValue() throws IOException
	{
		//ASSEMBLE
		customConfigValue("playersonteam = 1");
		loadConfig();
		//ACT
		//ASSERT
		Assert.assertEquals("playersonteam = 1 is not inside range (2 <= VALUE < 1000), defaulting to playersonteam = 10", teamPlugin.getLog().getLastMessage());
		Assert.assertEquals(10, Configuration.MAX_PLAYERS);
	}

	@Test
	public void ShouldBeListPatternCheckValid() throws IOException
	{
		//ASSEMBLE
		customConfigValue("disabledworlds = world,world_nether");
		loadConfig();
		//ACT
		//ASSERT
		Assert.assertNull(teamPlugin.getLog().getLastMessage());
		Assert.assertTrue(Configuration.DISABLED_WORLDS.contains("world"));
		Assert.assertTrue(Configuration.DISABLED_WORLDS.contains("world_nether"));
	}

	@Test
	public void ShouldBeListPatternCheckRevertToDefaultValue() throws IOException
	{
		//ASSEMBLE
		customConfigValue("disabledworlds = ???");
		loadConfig();
		//ACT
		//ASSERT
		Assert.assertEquals("disabledworlds = '???' is not a valid pattern, defaulting to disabledworlds = ''", teamPlugin.getLog().getLastMessage());
		Assert.assertEquals(CommonUtil.emptyList(), Configuration.DISABLED_WORLDS);
	}

	@Test
	public void ShouldBeStringPatternCheckRevertToDefaultValue() throws IOException
	{
		//ASSEMBLE
		customConfigValue("storagetype = mysql:blah:blah:blah:blah:blah");
		loadConfig();
		//ACT
		//ASSERT
		Assert.assertNull(teamPlugin.getLog().getLastMessage());
		Assert.assertEquals("mysql:blah:blah:blah:blah:blah", Configuration.STORAGE_TYPE);
	}

	@Test
	public void ShouldBeBooleanPatternCheckRevertToDefaultValue() throws IOException
	{
		//ASSEMBLE
		customConfigValue("friendlyfire = true?");
		loadConfig();
		//ACT
		//ASSERT
		Assert.assertNull(teamPlugin.getLog().getLastMessage());
		Assert.assertEquals(false, Configuration.TEAM_FRIENDLY_FIRE);
	}

	private void loadConfig()
	{
		this.configLoader = new Configuration(teamPlugin, SystemUtil.ensureFile("test/xTeam.cfg"));
		this.configLoader.load();
		this.configLoader.write();
	}

	private void customConfigValue(String write) throws IOException
	{
		FileWriter writer = new FileWriter(SystemUtil.ensureFile("test/xTeam.cfg"));
		writer.write(write);
		writer.close();
	}

	@After
	public void takedown()
	{
	}
}