package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminTeleAllHQTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminTeleAllHQ()
	{
		Assert.assertTrue("teleallhq".matches(new ServerAdminTeleAllHQ().getPattern()));
		Assert.assertTrue("teleallhq ".matches(new ServerAdminTeleAllHQ().getPattern()));
		Assert.assertTrue("tahq".matches(new ServerAdminTeleAllHQ().getPattern()));
		Assert.assertFalse("tele ".matches(new ServerAdminTeleAllHQ().getPattern()));
		Assert.assertFalse("teleallhq fdsakjn".matches(new ServerAdminTeleAllHQ().getPattern()));
		Assert.assertFalse("teleallhq awkejnr ".matches(new ServerAdminTeleAllHQ().getPattern()));
		Assert.assertTrue(new ServerAdminTeleAllHQ().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminTeleAllHQ().getPattern()));
	}

	@Test
	public void ShouldBeServerAdminTeleAllHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminTeleAllHQ();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "teleallhq".split(" ")));
		//ASSERT
		Assert.assertEquals("Players teleported", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
