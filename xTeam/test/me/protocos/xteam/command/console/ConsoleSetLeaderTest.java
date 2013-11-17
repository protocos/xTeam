package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleSetLeaderTest
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
	public void ShouldBeConsoleSetLeader()
	{
		Assert.assertTrue("setleader PLAYER TEAM".matches(new ConsoleSetLeader().getPattern()));
		Assert.assertTrue("setleader PLAYER TEAM ".matches(new ConsoleSetLeader().getPattern()));
		Assert.assertFalse("s PLAYER TEAM".matches(new ConsoleSetLeader().getPattern()));
		Assert.assertFalse("se PLAYER TEAM ".matches(new ConsoleSetLeader().getPattern()));
		Assert.assertFalse("s".matches(new ConsoleSetLeader().getPattern()));
		Assert.assertFalse("se ".matches(new ConsoleSetLeader().getPattern()));
		Assert.assertTrue(new ConsoleSetLeader().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleSetLeader().getPattern()));
	}

	@Test
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team setleader one protocos"));
		//ASSERT
		Assert.assertEquals("protocos is now the team leader for ONE", fakeConsoleSender.getLastMessage());
		Assert.assertEquals("protocos", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team setleader one newbie"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNoTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team setleader one Lonely"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecutePlayerNotOnTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team setleader one mastermind"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteTeamIsDefault()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team setleader red strandedhelix"));
		//ASSERT
		Assert.assertEquals((new TeamIsDefaultException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteTeamNotExist()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team setleader three Lonely"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
