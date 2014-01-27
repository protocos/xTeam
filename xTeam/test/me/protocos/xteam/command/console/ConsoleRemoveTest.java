package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.exception.TeamPlayerNeverPlayedException;
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
	public void ShouldBeConsoleRemove()
	{
		Assert.assertTrue("remove TEAM PLAYER".matches(new ConsoleRemove().getPattern()));
		Assert.assertTrue("remove TEAM PLAYER ".matches(new ConsoleRemove().getPattern()));
		Assert.assertTrue("rem TEAM PLAYER".matches(new ConsoleRemove().getPattern()));
		Assert.assertTrue("remv TEAM PLAYER ".matches(new ConsoleRemove().getPattern()));
		Assert.assertTrue("rm TEAM PLAYER ".matches(new ConsoleRemove().getPattern()));
		Assert.assertFalse("r TEAM PLAYER".matches(new ConsoleRemove().getPattern()));
		Assert.assertTrue(new ConsoleRemove().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleRemove().getPattern()));
	}

	@Test
	public void ShouldBeConsoleRemoveExecute()
	{
		//ASSEMBLE
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "remove one protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("You removed protocos from one", fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleHasNoTeam()
	{
		//ASSEMBLE
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "remove one Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleHeverPlayed()
	{
		//ASSEMBLE
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "remove one newbie".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteConsoleLeader()
	{
		//ASSEMBLE
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "remove one kmlanglois".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRemoveExecuteLastConsole()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").removePlayer("protocos");
		FakeConsoleSender fakeConsoleSender = new FakeConsoleSender();
		ConsoleCommand fakeCommand = new ConsoleRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "remove one kmlanglois".split(" ")));
		//ASSERT
		Assert.assertEquals("You removed kmlanglois from one", fakeConsoleSender.getMessage(0));
		Assert.assertEquals("one has been disbanded", fakeConsoleSender.getMessage(1));
		Assert.assertFalse(xTeam.getInstance().getTeamManager().containsTeam("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
	}
}
