package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.exception.TeamPlayerMaxException;
import me.protocos.xteam.exception.TeamPlayerNeverPlayedException;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsoleSetTest
{
	private TeamPlugin teamPlugin;
	private FakeConsoleSender fakeConsoleSender;
	private ConsoleCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeConsoleSender = new FakeConsoleSender();
		fakeCommand = new ConsoleSet(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeConsoleSet()
	{
		Assert.assertTrue("set PLAYER TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("set PLAYER TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("s PLAYER TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("se PLAYER TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("s".matches(fakeCommand.getPattern()));
		Assert.assertFalse("se ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "set Lonely two");
		//ASSERT
		Assert.assertEquals("Lonely has been added to two", fakeConsoleSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("two").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteCreateTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "set Lonely three");
		//ASSERT
		Assert.assertEquals("three has been created\n" +
				"Lonely has been added to three", fakeConsoleSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.containsTeam("three"));
		Assert.assertTrue(teamCoordinator.getTeam("three").containsPlayer("Lonely"));
		Assert.assertEquals(1, teamCoordinator.getTeam("three").size());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteHasTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "set protocos two");
		//ASSERT
		Assert.assertEquals("protocos has been removed from ONE\n" +
				"protocos has been added to two", fakeConsoleSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(teamCoordinator.getTeam("two").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteLastPerson()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").removePlayer("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "set kmlanglois two");
		//ASSERT
		Assert.assertEquals("kmlanglois has been removed from ONE\n" +
				"ONE has been disbanded\n" +
				"kmlanglois has been added to two", fakeConsoleSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.containsTeam("one"));
		Assert.assertTrue(teamCoordinator.getTeam("two").containsPlayer("kmlanglois"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteLeaderLeaving()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "set kmlanglois two");
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerHasNotPlayed()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "set newbie one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetMaxPlayers()
	{
		//ASSEMBLE
		Configuration.MAX_PLAYERS = 2;
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "set Lonely one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
