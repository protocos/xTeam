package me.protocos.xteam.command.console;

import org.junit.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleSetLeaderTest
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
		fakeCommand = new ConsoleSetLeader(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeConsoleSetLeader()
	{
		Assert.assertTrue("setleader PLAYER TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("setleader PLAYER TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("s PLAYER TEAM".matches(fakeCommand.getPattern()));
		Assert.assertFalse("se PLAYER TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("s".matches(fakeCommand.getPattern()));
		Assert.assertFalse("se ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setleader one protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("protocos is now the team leader for ONE", fakeConsoleSender.getLastMessage());
		Assert.assertEquals("protocos", teamCoordinator.getTeam("one").getLeader());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setleader one newbie".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNoTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setleader one Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNotOnTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setleader one mastermind".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteTeamIsDefault()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setleader red strandedhelix".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamIsDefaultException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteTeamNotExist()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setleader three Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
