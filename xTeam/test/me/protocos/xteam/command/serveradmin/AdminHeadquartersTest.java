package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamNoHeadquartersException;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminHeadquartersTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location hq = xTeam.getTeamManager().getTeam("one").getHeadquarters().getLocation();
		ServerAdminCommand fakeCommand = new AdminHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team hq one"));
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
		ServerAdminCommand fakeCommand = new AdminHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team hq team"));
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
		ServerAdminCommand fakeCommand = new AdminHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team hq two"));
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
