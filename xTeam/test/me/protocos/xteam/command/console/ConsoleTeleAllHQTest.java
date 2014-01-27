package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleTeleAllHQTest
{
	FakeConsoleSender fakeConsoleSender;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		fakeConsoleSender = new FakeConsoleSender();
	}

	@Test
	public void ShouldBeConsoleTeleAllHQ()
	{
		Assert.assertTrue("teleallhq".matches(new ConsoleTeleAllHQ().getPattern()));
		Assert.assertTrue("teleallhq ".matches(new ConsoleTeleAllHQ().getPattern()));
		Assert.assertTrue("teleah".matches(new ConsoleTeleAllHQ().getPattern()));
		Assert.assertFalse("t".matches(new ConsoleTeleAllHQ().getPattern()));
		Assert.assertFalse("tele ".matches(new ConsoleTeleAllHQ().getPattern()));
		Assert.assertTrue(new ConsoleTeleAllHQ().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleTeleAllHQ().getPattern()));
	}

	@Test
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleTeleAllHQ();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "teleallhq".split(" ")));
		//ASSERT
		Assert.assertEquals("Players teleported", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
