package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminTpAllTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminTpAll()
	{
		Assert.assertTrue("tpall TEAM".matches(new ServerAdminTpAll().getPattern()));
		Assert.assertTrue("tpall TEAM ".matches(new ServerAdminTpAll().getPattern()));
		Assert.assertTrue("tpa TEAM".matches(new ServerAdminTpAll().getPattern()));
		Assert.assertTrue("tpa TEAM ".matches(new ServerAdminTpAll().getPattern()));
		Assert.assertFalse("t TEAM".matches(new ServerAdminTpAll().getPattern()));
		Assert.assertFalse("tpall".matches(new ServerAdminTpAll().getPattern()));
		Assert.assertFalse("tpall ".matches(new ServerAdminTpAll().getPattern()));
		Assert.assertTrue(new ServerAdminTpAll().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminTpAll().getPattern()));
	}

	@Test
	public void ShouldBeServerAdminTpAllExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminTpAll();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tpall one".split(" ")));
		//ASSERT
		Assert.assertEquals("Players teleported", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminTpAllExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminTpAll();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tpall three".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
