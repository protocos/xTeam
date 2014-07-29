package me.protocos.xteam.command.teamuser;

import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.Locatable;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommandUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserTeleportTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private BukkitUtil bukkitUtil;
	private IPlayerFactory playerFactory;
	private TeleportScheduler teleportScheduler;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		bukkitUtil = teamPlugin.getBukkitUtil();
		playerFactory = teamPlugin.getPlayerFactory();
		teleportScheduler = teamPlugin.getTeleportScheduler();
		fakeCommand = new TeamUserTeleport(teamPlugin);
	}

	@Test
	public void ShouldBeTeamUserTeleport()
	{
		Assert.assertTrue("tp".matches(fakeCommand.getPattern()));
		Assert.assertTrue("teleport ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tp PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tp PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tp PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tp PLAYER wekn;ljdkkmsnaf".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserTeleExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("kmlanglois").location(bukkitUtil.getPlayer("protocos").getLocation()).build();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele protocos");
		//ASSERT
		Assert.assertEquals("You have been teleported to protocos", fakePlayerSender.getLastMessages());
		Assert.assertEquals(bukkitUtil.getPlayer("protocos").getLocation(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteNoName()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("kmlanglois").location(bukkitUtil.getPlayer("protocos").getLocation()).build();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele");
		//ASSERT
		Assert.assertEquals("You have been teleported to protocos", fakePlayerSender.getLastMessages());
		Assert.assertEquals(bukkitUtil.getPlayer("protocos").getLocation(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteNoTeammatesOnline()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("mastermind");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player has no teammates online")).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteNotTeammate()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele mastermind");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("kmlanglois").location(bukkitUtil.getPlayer("protocos").getLocation()).build();
		Location before = fakePlayerSender.getLocation();
		fakePlayerSender.setNoDamageTicks(1);
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele protocos");
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele protocos");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		playerFactory.getPlayer("kmlanglois").setLastAttacked(System.currentTimeMillis());
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele protocos");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer testPlayer = CommonUtil.assignFromType(playerFactory.getPlayer("kmlanglois"), TeamPlayer.class);
		teleportScheduler.getCurrentTasks().put(testPlayer, 0);
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele protocos");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteRecentTeleport()
	{
		//ASSEMBLE
		Configuration.TELE_REFRESH_DELAY = 60;
		teleportScheduler.teleport(CommonUtil.assignFromType(playerFactory.getPlayer("kmlanglois"), TeamPlayer.class), new Locatable(teamPlugin, "locatable", new FakeLocation()));
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		fakePlayerSender.clearMessages();
		Location beforeLocation = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele protocos");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport within " + Configuration.TELE_REFRESH_DELAY + " seconds of last teleport\nPlayer must wait " + Configuration.TELE_REFRESH_DELAY + " more seconds")).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(beforeLocation, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteSelfTele()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele kmlanglois");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport to themselves")).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele neverplayed");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecutePlayerOffline()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("strandedhelix");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tele teammate");
		//ASSERT
		Assert.assertEquals((new TeamPlayerOfflineException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.TELE_REFRESH_DELAY = 0;
		Configuration.LAST_ATTACKED_DELAY = 0;
	}
}
