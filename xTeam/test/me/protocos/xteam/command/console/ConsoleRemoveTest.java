package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsoleRemoveTest
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
		fakeCommand = new ConsoleRemove(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeConsoleRemove()
	{
		Assert.assertTrue("remove TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("remove TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rem TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("remv TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rm TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("r TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleRemoveExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "remove protocos one");
		//ASSERT
		Assert.assertEquals("protocos has been removed from ONE", fakeConsoleSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteLastConsole()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").removePlayer("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "remove kmlanglois one");
		//ASSERT
		Assert.assertEquals("kmlanglois has been removed from ONE\n" +
				"ONE has been disbanded", fakeConsoleSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.containsTeam("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteTeamDoesNotExist()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "remove Lonely DNE");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteConsolePlayerNeverPlayed()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "remove newbie one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleHasNoTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "remove Lonely one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleNotOnTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "remove mastermind one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleLeader()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "remove kmlanglois one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
	}
}
