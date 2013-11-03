package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.core.exception.TeamPlayerMaxException;
import me.protocos.xteam.core.exception.TeamPlayerNeverPlayedException;
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
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team set Lonely two"));
		//ASSERT
		Assert.assertEquals("Lonely has been added to two", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(xTeam.getTeamManager().getTeam("two").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteCreateTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team set Lonely three"));
		//ASSERT
		Assert.assertEquals("three has been created\n" +
				"Lonely has been added to three\n", fakeConsoleSender.getAllMessages());
		Assert.assertTrue(xTeam.getTeamManager().contains("three"));
		Assert.assertTrue(xTeam.getTeamManager().getTeam("three").containsPlayer("Lonely"));
		Assert.assertEquals(1, xTeam.getTeamManager().getTeam("three").size());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteHasTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team set protocos two"));
		//ASSERT
		Assert.assertEquals("protocos has been removed from ONE\n" +
				"protocos has been added to two\n", fakeConsoleSender.getAllMessages());
		Assert.assertFalse(xTeam.getTeamManager().getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(xTeam.getTeamManager().getTeam("two").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteLastPerson()
	{
		//ASSEMBLE
		xTeam.getTeamManager().getTeam("one").removePlayer("protocos");
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team set kmlanglois two"));
		//ASSERT
		Assert.assertEquals("kmlanglois has been removed from ONE\n" +
				"ONE has been disbanded\n" +
				"kmlanglois has been added to two\n", fakeConsoleSender.getAllMessages());
		Assert.assertFalse(xTeam.getTeamManager().contains("one"));
		Assert.assertTrue(xTeam.getTeamManager().getTeam("two").containsPlayer("kmlanglois"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteLeaderLeaving()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team set kmlanglois two"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecutePlayerHasNotPlayed()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team set newbie one"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetMaxPlayers()
	{
		//ASSEMBLE
		Data.MAX_PLAYERS = 2;
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team set Lonely one"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}