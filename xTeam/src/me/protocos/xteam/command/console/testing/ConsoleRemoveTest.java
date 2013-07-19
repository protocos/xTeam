package me.protocos.xteam.command.console.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.console.ConsoleRemove;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.core.exception.TeamPlayerNeverPlayedException;
import me.protocos.xteam.testing.FakeConsoleSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleRemoveTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeConsoleRemoveExecute()
	{
		//ASSEMBLE
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team remove one protocos"));
		//ASSERT
		Assert.assertEquals("You removed protocos from one", fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleHasNoTeam()
	{
		//ASSEMBLE
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team remove one Lonely"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleHeverPlayed()
	{
		//ASSEMBLE
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team remove one newbie"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").containsPlayer("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleLeader()
	{
		//ASSEMBLE
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team remove one kmlanglois"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleRemoveExecuteLastConsole()
	{
		//ASSEMBLE
		xTeam.tm.getTeam("one").removePlayer("protocos");
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team remove one kmlanglois"));
		//ASSERT
		Assert.assertEquals("You removed kmlanglois from one", fakeConsoleSender.getMessage(0));
		Assert.assertEquals("one has been disbanded", fakeConsoleSender.getMessage(1));
		Assert.assertFalse(xTeam.tm.contains("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.ALPHA_NUM = false;
	}
}
