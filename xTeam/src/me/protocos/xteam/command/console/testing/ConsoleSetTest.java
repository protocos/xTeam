package me.protocos.xteam.command.console.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.command.console.ConsoleSet;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.core.exception.TeamPlayerMaxException;
import me.protocos.xteam.core.exception.TeamPlayerNeverPlayedException;
import me.protocos.xteam.testing.FakeConsoleSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleSetTest
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
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSet(fakeConsoleSender, "set Lonely two");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Lonely has been added to two", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("two").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteLastPerson()
	{
		//ASSEMBLE
		xTeam.tm.getTeam("one").removePlayer("protocos");
		BaseConsoleCommand fakeCommand = new ConsoleSet(fakeConsoleSender, "set kmlanglois two");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("kmlanglois has been removed from ONE\n" +
				"ONE has been disbanded\n" +
				"kmlanglois has been added to two\n", fakeConsoleSender.getAllMessages());
		Assert.assertFalse(xTeam.tm.contains("one"));
		Assert.assertTrue(xTeam.tm.getTeam("two").containsPlayer("kmlanglois"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteCreateTeam()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSet(fakeConsoleSender, "set Lonely three");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("three has been created\n" +
				"Lonely has been added to three\n", fakeConsoleSender.getAllMessages());
		Assert.assertTrue(xTeam.tm.contains("three"));
		Assert.assertTrue(xTeam.tm.getTeam("three").containsPlayer("Lonely"));
		Assert.assertEquals(1, xTeam.tm.getTeam("three").size());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteHasTeam()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSet(fakeConsoleSender, "set protocos two");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("protocos has been removed from ONE\n" +
				"protocos has been added to two\n", fakeConsoleSender.getAllMessages());
		Assert.assertFalse(xTeam.tm.getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(xTeam.tm.getTeam("two").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteLeaderLeaving()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSet(fakeConsoleSender, "set kmlanglois two");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecutePlayerHasNotPlayed()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSet(fakeConsoleSender, "set newbie one");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetMaxPlayers()
	{
		//ASSEMBLE
		Data.MAX_PLAYERS = 2;
		BaseConsoleCommand fakeCommand = new ConsoleSet(fakeConsoleSender, "set Lonely one");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
