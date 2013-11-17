package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.Locatable;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserTeleportTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamUserTeleport()
	{
		Assert.assertTrue("tele".matches(new TeamUserTeleport().getPattern()));
		Assert.assertTrue("tele ".matches(new TeamUserTeleport().getPattern()));
		Assert.assertTrue("tele PLAYER".matches(new TeamUserTeleport().getPattern()));
		Assert.assertTrue("tele PLAYER ".matches(new TeamUserTeleport().getPattern()));
		Assert.assertTrue("tp PLAYER".matches(new TeamUserTeleport().getPattern()));
		Assert.assertFalse("tp PLAYER wekn;ljdkkmsnaf".matches(new TeamUserTeleport().getPattern()));
		Assert.assertTrue(new TeamUserTeleport().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserTeleport().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserTeleExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", BukkitUtil.getPlayer("protocos").getLocation());
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele protocos"));
		//ASSERT
		Assert.assertEquals("You've been teleported to protocos", fakePlayerSender.getLastMessage());
		Assert.assertEquals(BukkitUtil.getPlayer("protocos").getLocation(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteNoName()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", BukkitUtil.getPlayer("protocos").getLocation());
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele"));
		//ASSERT
		Assert.assertEquals("You've been teleported to protocos", fakePlayerSender.getLastMessage());
		Assert.assertEquals(BukkitUtil.getPlayer("protocos").getLocation(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteNoTeammatesOnline()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player has no teammates online")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteNotTeammate()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele mastermind"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", BukkitUtil.getPlayer("protocos").getLocation());
		Location before = fakePlayerSender.getLocation();
		fakePlayerSender.setNoDamageTicks(1);
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois").setLastAttacked(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer testPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		TeleportScheduler.getInstance().getCurrentTasks().put(testPlayer, 0);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteRecentTeleport()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		TeleportScheduler.getInstance().teleport(teamPlayer, new Locatable("previous teleport", new FakeLocation()));
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport within 60 seconds of last teleport\nPlayer must wait 60 more seconds\n/team return is still available")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecuteSelfTele()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele kmlanglois"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport to themselves")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele neverplayed"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserTeleExecutePlayerOffline()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("strandedhelix", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserTeleport();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tele teammate"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerOfflineException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.LAST_ATTACKED_DELAY = 0;
		TeleportScheduler.getInstance().clearTasks();
	}
}
