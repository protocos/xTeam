package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.exception.TeamPlayerMaxException;
import me.protocos.xteam.exception.TeamPlayerNeverPlayedException;
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
	public void ShouldBeConsoleSet()
	{
		Assert.assertTrue("set PLAYER TEAM".matches(new ConsoleSet().getPattern()));
		Assert.assertTrue("set PLAYER TEAM ".matches(new ConsoleSet().getPattern()));
		Assert.assertTrue("s PLAYER TEAM".matches(new ConsoleSet().getPattern()));
		Assert.assertTrue("se PLAYER TEAM ".matches(new ConsoleSet().getPattern()));
		Assert.assertFalse("s".matches(new ConsoleSet().getPattern()));
		Assert.assertFalse("se ".matches(new ConsoleSet().getPattern()));
		Assert.assertTrue(new ConsoleSet().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleSet().getPattern()));
	}

	@Test
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "set Lonely two".split(" ")));
		//ASSERT
		Assert.assertEquals("Lonely has been added to two", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("two").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteCreateTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "set Lonely three".split(" ")));
		//ASSERT
		Assert.assertEquals("three has been created\n" +
				"Lonely has been added to three\n", fakeConsoleSender.getAllMessages());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().containsTeam("three"));
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("three").containsPlayer("Lonely"));
		Assert.assertEquals(1, xTeam.getInstance().getTeamManager().getTeam("three").size());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteHasTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "set protocos two".split(" ")));
		//ASSERT
		Assert.assertEquals("protocos has been removed from ONE\n" +
				"protocos has been added to two\n", fakeConsoleSender.getAllMessages());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("two").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteLastPerson()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").removePlayer("protocos");
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "set kmlanglois two".split(" ")));
		//ASSERT
		Assert.assertEquals("kmlanglois has been removed from ONE\n" +
				"ONE has been disbanded\n" +
				"kmlanglois has been added to two\n", fakeConsoleSender.getAllMessages());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().containsTeam("one"));
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("two").containsPlayer("kmlanglois"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteLeaderLeaving()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "set kmlanglois two".split(" ")));
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
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "set newbie one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetMaxPlayers()
	{
		//ASSEMBLE
		Configuration.MAX_PLAYERS = 2;
		ConsoleCommand fakeCommand = new ConsoleSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "set Lonely one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
