package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserHeadquartersTest
{
	Location before;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		before = new FakeLocation();
	}

	@Test
	public void ShouldBeTeamUserHeadquarters()
	{
		Assert.assertTrue("hq".matches(new TeamUserHeadquarters().getPattern()));
		Assert.assertTrue("hq ".matches(new TeamUserHeadquarters().getPattern()));
		Assert.assertFalse("h".matches(new TeamUserHeadquarters().getPattern()));
		Assert.assertFalse("hq dsaf".matches(new TeamUserHeadquarters().getPattern()));
		Assert.assertFalse("h dsaf".matches(new TeamUserHeadquarters().getPattern()));
		Assert.assertTrue(new TeamUserHeadquarters().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserHeadquarters().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserHQ()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		TeamUserCommand fakeCommand = new TeamUserHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals("You've been teleported to the team headquarters", fakePlayerSender.getLastMessage());
		Assert.assertEquals(xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters().getLocation(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
		//TODO assert everything! (including teleport)
	}

	@Test
	public void ShouldBeTeamUserHQNoHeadquarters()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", before);
		TeamUserCommand fakeCommand = new TeamUserHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNoHeadquartersException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQPlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		fakePlayerSender.setNoDamageTicks(1);
		TeamUserCommand fakeCommand = new TeamUserHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQPlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", before);
		TeamUserCommand fakeCommand = new TeamUserHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois").setLastAttacked(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		TeamUserCommand fakeCommand = new TeamUserHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last " + Configuration.LAST_ATTACKED_DELAY + " seconds\nYou must wait " + Configuration.LAST_ATTACKED_DELAY + " more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		TeleportScheduler.getInstance().setCurrentTask(teamPlayer, 0);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		TeamUserCommand fakeCommand = new TeamUserHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHQRecentTeleport()
	{
		//ASSEMBLE
		xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois").teleportTo(xTeam.getInstance().getTeamManager().getTeam("ONE"));
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		TeamUserCommand fakeCommand = new TeamUserHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport within " + Configuration.TELE_REFRESH_DELAY + " seconds of last teleport\nPlayer must wait " + Configuration.TELE_REFRESH_DELAY + " more seconds\n/team return is still available")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		ITeamPlayer kmlanglois = xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois");
		kmlanglois.setLastAttacked(0L);
		kmlanglois.setLastTeleported(0L);
		kmlanglois.setReturnLocation(null);
	}
}
