package me.protocos.xteam.data;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.SystemUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest
{
	private Configuration configLoader;

	@Before
	public void setup()
	{
		SystemUtil.ensureFolder("test/");
		SystemUtil.deleteFile("test/xTeam.cfg");
		this.configLoader = new Configuration(FakeXTeam.asTeamPlugin(), SystemUtil.ensureFile("test/xTeam.cfg"));
		this.configLoader.load();
		this.configLoader.write();
	}

	@Test
	public void ShouldBeDefaultConfigValues()
	{
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

	@After
	public void takedown()
	{
	}
}