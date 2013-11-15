package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminReloadTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminReloadExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminReload();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team reload"));
		//ASSERT
		Assert.assertEquals("Configuration reloaded", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
