package me.protocos.xteam.command.console.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.command.console.ConsoleInfo;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.testing.FakeConsoleSender;
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
		BaseConsoleCommand fakeCommand = new ConsoleInfo(fakeConsoleSender, "info protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
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
		BaseConsoleCommand fakeCommand = new ConsoleInfo(fakeConsoleSender, "info two");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
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
		BaseConsoleCommand fakeCommand = new ConsoleInfo(fakeConsoleSender, "info lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleInfoExecuteTeamNotExists()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleInfo(fakeConsoleSender, "info three");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
