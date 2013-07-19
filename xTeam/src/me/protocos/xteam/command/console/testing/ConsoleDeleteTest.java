package me.protocos.xteam.command.console.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.console.ConsoleDisband;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.testing.FakeConsoleSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleDeleteTest
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
	public void ShouldBeConsoleDeleteExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team disband one"));
		//ASSERT
		Assert.assertEquals("You disbanded one", fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getAllTeamNames().contains("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleDeleteExecuteThrowsNoTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team disband team"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("[ONE, two, red, blue]", xTeam.tm.getAllTeamNames().toString());
		Assert.assertTrue(xTeam.tm.contains("one"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
