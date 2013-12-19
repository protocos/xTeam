package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.configuration.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminChatSpyTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminChatSpy()
	{
		Assert.assertTrue("chatspy".matches(new ServerAdminChatSpy().getPattern()));
		Assert.assertTrue("chatspy ".matches(new ServerAdminChatSpy().getPattern()));
		Assert.assertTrue("chspy".matches(new ServerAdminChatSpy().getPattern()));
		Assert.assertTrue("cspy ".matches(new ServerAdminChatSpy().getPattern()));
		Assert.assertFalse("c".matches(new ServerAdminChatSpy().getPattern()));
		Assert.assertFalse("cspy dasflk;j".matches(new ServerAdminChatSpy().getPattern()));
		Assert.assertTrue(new ServerAdminChatSpy().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminChatSpy().getPattern()));
	}

	@Test
	public void ShouldBeServerAdminChatSpyExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminChatSpy();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "chatspy".split(" ")));
		//ASSERT
		Assert.assertEquals("You are now spying on team chat", fakePlayerSender.getLastMessage());
		Assert.assertTrue(Configuration.spies.contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminChatSpyExecuteDisable()
	{
		//ASSEMBLE
		Configuration.spies.add("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminChatSpy();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "chatspy".split(" ")));
		//ASSERT
		Assert.assertEquals("You are no longer spying on team chat", fakePlayerSender.getLastMessage());
		Assert.assertFalse(Configuration.spies.contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.spies.clear();
	}
}
