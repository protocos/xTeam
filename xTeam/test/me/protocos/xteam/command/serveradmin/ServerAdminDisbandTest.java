package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamIsDefaultException;
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
	public void ShouldBeServerAdminDisbandByName()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "disband one".split(" ")));
		//ASSERT
		Assert.assertEquals("You disbanded ONE [TeamAwesome]", fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().containsTeam("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDisbandByTag()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "disband TeamAwesome".split(" ")));
		//ASSERT
		Assert.assertEquals("You disbanded ONE [TeamAwesome]", fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().containsTeam("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDisbandExecuteTeamDoesNotExixts()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "disband ooga".split(" ")));
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
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "disband RED".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamIsDefaultException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().containsTeam("RED"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
