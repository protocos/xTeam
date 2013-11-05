package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleInfoTest
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
	public void ShouldBeConsoleInfoExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team info protocos"));
		//ASSERT
		Assert.assertEquals("Team Name - ONE" +
				"\nTeam Tag - TeamAwesome" +
				"\nTeam Leader - kmlanglois" +
				"\nTeam Joining - Closed" +
				"\nTeam Headquarters - X:170 Y:65 Z:209" +
				"\nTeammates online:" +
				"\n    kmlanglois Health: 100% Location: 0 64 0 in \"world\"" +
				"\n    protocos Health: 100% Location: 0 64 0 in \"world\"",
				fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleInfoExecute2()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team info two"));
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
		ConsoleCommand fakeCommand = new ConsoleInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team info Lonely"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleInfoExecuteTeamNotExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team info truck"));
		//ASSERT
		Assert.assertEquals((new TeamOrPlayerDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
