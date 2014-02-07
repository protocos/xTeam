package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamNoHeadquartersException;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminHeadquartersTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminHeadquarters()
	{
		Assert.assertTrue("hq TEAM".matches(new ServerAdminHeadquarters().getPattern()));
		Assert.assertTrue("hq TEAM ".matches(new ServerAdminHeadquarters().getPattern()));
		Assert.assertFalse("h TEAM".matches(new ServerAdminHeadquarters().getPattern()));
		Assert.assertFalse("h TEAM ".matches(new ServerAdminHeadquarters().getPattern()));
		Assert.assertFalse("hq TEAM fdsaj;k".matches(new ServerAdminHeadquarters().getPattern()));
		Assert.assertTrue(new ServerAdminHeadquarters().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminHeadquarters().getPattern()));
	}

	@Test
	public void ShouldBeServerAdminHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location hq = XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters().getLocation();
		ServerAdminCommand fakeCommand = new ServerAdminHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "hq one".split(" ")));
		//ASSERT
		Assert.assertEquals("You have been teleported to the headquarters of team one", fakePlayerSender.getLastMessage());
		Assert.assertEquals(hq, fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminHQExecuteThrowsNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		ServerAdminCommand fakeCommand = new ServerAdminHeadquarters();
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
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		ServerAdminCommand fakeCommand = new ServerAdminHeadquarters();
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
