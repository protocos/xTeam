package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.console.ConsoleList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleListTest
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
	public void ShouldBeConsoleListExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team list"));
		//ASSERT
		Assert.assertEquals("Teams: ONE, two, red, blue", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
