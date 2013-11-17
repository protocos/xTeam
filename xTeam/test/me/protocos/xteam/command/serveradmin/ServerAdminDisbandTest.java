package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamIsDefaultException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminDisbandTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminDisband()
	{
		Assert.assertTrue("disband TEAM".matches(new ServerAdminDisband().getPattern()));
		Assert.assertTrue("disband TEAM ".matches(new ServerAdminDisband().getPattern()));
		Assert.assertFalse("disband".matches(new ServerAdminDisband().getPattern()));
		Assert.assertFalse("d TEAM".matches(new ServerAdminDisband().getPattern()));
		Assert.assertFalse("disband TEAM dfsaiphjkl".matches(new ServerAdminDisband().getPattern()));
		Assert.assertTrue(new ServerAdminDisband().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminDisband().getPattern()));
	}

	@Test
	public void ShouldBeServerAdminDisbandExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team disband one"));
		//ASSERT
		Assert.assertEquals("You disbanded one", fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().contains("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDisbandExecuteTeamDoesNotExixts()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team disband ooga"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDisbandExecuteTeamIsDefault()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team disband RED"));
		//ASSERT
		Assert.assertEquals((new TeamIsDefaultException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().contains("RED"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
