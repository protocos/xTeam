package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
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
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "setleader one protocos");
		//ASSERT
		Assert.assertEquals("protocos is now the team leader for ONE", fakeConsoleSender.getLastMessages());
		Assert.assertEquals("protocos", teamCoordinator.getTeam("one").getLeader());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "setleader one newbie");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNoTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "setleader one Lonely");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNotOnTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "setleader one mastermind");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteTeamIsDefault()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "setleader red strandedhelix");
		//ASSERT
		Assert.assertEquals((new TeamIsDefaultException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteTeamNotExist()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "setleader three Lonely");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertEquals("kmlanglois", teamCoordinator.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
