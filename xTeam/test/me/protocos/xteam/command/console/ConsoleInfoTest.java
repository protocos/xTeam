package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
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
				"Team Tag - TeamAwesome" +
				"Team Leader - kmlanglois" +
				"Team Joining - Closed" +
				"Team Headquarters - X:170 Y:65 Z:209" +
				"Teammates online:" +
				"    kmlanglois Health: 100% Location: 0 64 0 in \"world\"" +
				"    protocos Health: 100% Location: 0 64 0 in \"world\"",
				fakeConsoleSender.getMessage(0) +
						fakeConsoleSender.getMessage(1) +
						fakeConsoleSender.getMessage(2) +
						fakeConsoleSender.getMessage(3) +
						fakeConsoleSender.getMessage(4) +
						fakeConsoleSender.getMessage(5) +
						fakeConsoleSender.getMessage(6) +
						fakeConsoleSender.getMessage(7));
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
				"Team Leader - mastermind" +
				"Team Joining - Closed" +
				"Team Headquarters - none set" +
				"Teammates online:" +
				"    mastermind Health: 100% Location: 0 64 0 in \"world\"",
				fakeConsoleSender.getMessage(0) +
						fakeConsoleSender.getMessage(1) +
						fakeConsoleSender.getMessage(2) +
						fakeConsoleSender.getMessage(3) +
						fakeConsoleSender.getMessage(4) +
						fakeConsoleSender.getMessage(5));
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
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team info three"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
