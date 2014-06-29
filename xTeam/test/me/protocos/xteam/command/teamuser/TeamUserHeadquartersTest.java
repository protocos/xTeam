package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserHeadquartersTest
{
	private Location before;
	private TeamUserCommand fakeCommand;
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private TeleportScheduler teleportScheduler;

	@Before
	public void setup()
	{
		before = new FakeLocation();
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserHeadquarters(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
		teleportScheduler = teamPlugin.getTeleportScheduler();
	}

	@Test
	public void ShouldBeTeamUserHeadquarters()
	{
		Assert.assertTrue("hq".matches(fakeCommand.getPattern()));
		Assert.assertTrue("hq ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("h".matches(fakeCommand.getPattern()));
		Assert.assertFalse("hq dsaf".matches(fakeCommand.getPattern()));
		Assert.assertFalse("h dsaf".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserHQ()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("kmlanglois").location(before).build();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals("You have been teleported to the team headquarters", fakePlayerSender.getLastMessages());
		Assert.assertEquals(teamCoordinator.getTeam("one").getHeadquarters().getLocation(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
		//TODO assert everything! (including teleport)
	}

	@Test
	public void ShouldBeTeamUserHQNoHeadquarters()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("mastermind").location(before).build();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNoHeadquartersException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQPlayerDying()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("kmlanglois").location(before).build();
		fakePlayerSender.setNoDamageTicks(1);
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQPlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("Lonely").location(before).build();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		playerFactory.getPlayer("kmlanglois").setLastAttacked(System.currentTimeMillis());
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("kmlanglois").location(before).build();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last " + Configuration.LAST_ATTACKED_DELAY + " seconds\nYou must wait " + Configuration.LAST_ATTACKED_DELAY + " more seconds")).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(playerFactory.getPlayer("kmlanglois"), TeamPlayer.class);
		teleportScheduler.setCurrentTask(teamPlayer, 0);
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("kmlanglois").location(before).build();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQRecentTeleport()
	{
		//ASSEMBLE
		Configuration.TELE_REFRESH_DELAY = 60;
		playerFactory.getPlayer("kmlanglois").teleportTo(teamCoordinator.getTeam("ONE"));
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("kmlanglois").location(before).build();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport within " + Configuration.TELE_REFRESH_DELAY + " seconds of last teleport\nPlayer must wait " + Configuration.TELE_REFRESH_DELAY + " more seconds")).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.TELE_REFRESH_DELAY = 0;
		ITeamPlayer kmlanglois = playerFactory.getPlayer("kmlanglois");
		kmlanglois.setLastAttacked(0L);
		kmlanglois.setLastTeleported(0L);
		kmlanglois.setReturnLocation(null);
	}
}
