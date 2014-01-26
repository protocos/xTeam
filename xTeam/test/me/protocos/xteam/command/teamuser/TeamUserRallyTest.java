package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.entity.ITeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.configuration.Configuration;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserRallyTest
{
	ITeam team;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		team = xTeam.getInstance().getTeamManager().getTeam("one");
	}

	@Test
	public void ShouldBeTeamUserRally()
	{
		Assert.assertTrue("rally".matches(new TeamUserRally().getPattern()));
		Assert.assertTrue("rally ".matches(new TeamUserRally().getPattern()));
		Assert.assertTrue("ral".matches(new TeamUserRally().getPattern()));
		Assert.assertFalse("r ".matches(new TeamUserRally().getPattern()));
		Assert.assertFalse("rally HOME ".matches(new TeamUserRally().getPattern()));
		Assert.assertTrue(new TeamUserRally().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserRally().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserRallyExecute()
	{
		//ASSEMBLE
		Location beforeLocation = new FakeLocation(BukkitUtil.getWorld("world")).toLocation();
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", beforeLocation);
		Location rallyLocation = team.getHeadquarters().getLocation();
		team.setRally(rallyLocation);
		TeamUserCommand fakeCommand = new TeamUserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rally".split(" ")));
		//ASSERT
		Assert.assertEquals("You've been teleported to the rally point", fakePlayerSender.getLastMessage());
		Assert.assertEquals(rallyLocation, fakePlayerSender.getLocation());
		Assert.assertEquals(beforeLocation, xTeam.getInstance().getPlayerManager().getPlayer("protocos").getReturnLocation());
		Assert.assertTrue(team.hasRally());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteNoRally()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rally".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotHaveRallyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		team.setRally(team.getHeadquarters().getLocation());
		fakePlayerSender.setNoDamageTicks(1);
		TeamUserCommand fakeCommand = new TeamUserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rally".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		team.setRally(team.getHeadquarters().getLocation());
		TeamUserCommand fakeCommand = new TeamUserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rally".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		xTeam.getInstance().getPlayerManager().getPlayer("protocos").setLastAttacked(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		xTeam.getInstance().getTeamManager().getTeam("one").setRally(new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rally".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		TeleportScheduler.getInstance().setCurrentTask(teamPlayer, 0);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		xTeam.getInstance().getTeamManager().getTeam("one").setRally(new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rally".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteAlreadyRallied()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		Location rallyLocation = team.getHeadquarters().getLocation();
		team.setRally(rallyLocation);
		TeleportScheduler.getInstance().setRallyUsedFor(teamPlayer);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rally".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerAlreadyUsedRallyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		TeleportScheduler.getInstance().clearTeamRally(team);
	}

	//	private boolean locationsEqual(Location location1, Location location2)
	//	{
	//		return (location1.getWorld().equals(location2.getWorld()) &&
	//				location1.getX() == location2.getX() &&
	//				location1.getY() == location2.getY() &&
	//				location1.getZ() == location2.getZ() &&
	//				location1.getPitch() == location2.getPitch() && location1.getYaw() == location2.getYaw());
	//	}
}
