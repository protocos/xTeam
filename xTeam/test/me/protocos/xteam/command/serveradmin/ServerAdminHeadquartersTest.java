package me.protocos.xteam.command.serveradmin;

import org.junit.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamNoHeadquartersException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminHeadquartersTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminHeadquarters(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeServerAdminHeadquarters()
	{
		Assert.assertTrue("hq TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("hq TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("h TEAM".matches(fakeCommand.getPattern()));
		Assert.assertFalse("h TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("hq TEAM fdsaj;k".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		Location hq = teamCoordinator.getTeam("one").getHeadquarters().getLocation();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq one".split(" ")));
		//ASSERT
		Assert.assertEquals("You have been teleported to the headquarters of team ONE", fakePlayerSender.getLastMessage());
		Assert.assertEquals(hq, fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminHQExecuteThrowsNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq team".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminHQExecuteThrowsNoTeamHeadquarters()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq two".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNoHeadquartersException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
