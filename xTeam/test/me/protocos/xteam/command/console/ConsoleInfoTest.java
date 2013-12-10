package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleInfoTest
{
	FakeConsoleSender fakeConsoleSender;
	ConsoleInfo fakeCommand;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		fakeConsoleSender = new FakeConsoleSender();
		fakeCommand = new ConsoleInfo();
	}

	@Test
	public void ShouldBeConsoleInfo()
	{
		Assert.assertTrue("info TEAM/PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info TEAM/PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("info".matches(fakeCommand.getPattern()));
		Assert.assertFalse("info ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleInfoExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - ONE" +
				"\nTeam Tag - TeamAwesome" +
				"\nTeam Leader - kmlanglois" +
				"\nTeam Joining - Closed" +
				"\nTeam Headquarters - X:170 Y:65 Z:209" +
				"\nTeammates online:" +
				"\n    protocos Health: 100% Location: 0 64 0 in \"world\"" +
				"\n    kmlanglois Health: 100% Location: 0 64 0 in \"world\"",
				fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleInfoExecute2()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info two".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - two" +
				"\nTeam Leader - mastermind" +
				"\nTeam Joining - Closed" +
				"\nTeam Headquarters - None set" +
				"\nTeammates online:" +
				"\n    mastermind Health: 100% Location: 0 64 0 in \"world\"",
				fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleInfoExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleInfoExecuteTeamNotExists()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info truck".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamOrPlayerDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
