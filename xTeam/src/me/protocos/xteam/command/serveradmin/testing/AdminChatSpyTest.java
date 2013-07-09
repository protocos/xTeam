package me.protocos.xteam.command.serveradmin.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.serveradmin.AdminChatSpy;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminChatSpyTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeServerAdminChatSpyExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminChatSpy(fakePlayerSender, new CommandParser("/team chatspy"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You are now spying on team chat", fakePlayerSender.getLastMessage());
		Assert.assertTrue(Data.spies.contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminChatSpyExecuteDisable()
	{
		//ASSEMBLE
		Data.spies.add("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminChatSpy(fakePlayerSender, new CommandParser("/team chatspy"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You are no longer spying on team chat", fakePlayerSender.getLastMessage());
		Assert.assertFalse(Data.spies.contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.spies.clear();
	}
}
