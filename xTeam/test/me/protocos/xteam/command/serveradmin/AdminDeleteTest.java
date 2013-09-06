package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.serveradmin.AdminDisband;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminDeleteTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeServerAdminDeleteExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team disband one"));
		//ASSERT
		Assert.assertEquals("You disbanded one", fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.contains("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminDeleteExecuteThrowsNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team disband team"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.contains("one"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
