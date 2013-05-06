package me.protocos.xteam.command.console.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.command.console.ConsoleSetLeader;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.testing.FakeConsoleSender;
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
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSetLeader(fakeConsoleSender, "setleader one protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("protocos is now the team leader for ONE", fakeConsoleSender.getLastMessage());
		Assert.assertEquals("protocos", xTeam.tm.getTeam("one").getLeader());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSetLeader(fakeConsoleSender, "setleader one newbie");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("Kmlanglois", xTeam.tm.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecutePlayerNoTeam()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSetLeader(fakeConsoleSender, "setleader one lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("Kmlanglois", xTeam.tm.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecutePlayerNotOnTeam()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSetLeader(fakeConsoleSender, "setleader one mastermind");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("Kmlanglois", xTeam.tm.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteTeamIsDefault()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSetLeader(fakeConsoleSender, "setleader red strandedhelix");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamIsDefaultException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("Kmlanglois", xTeam.tm.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleSetExecuteTeamNotExist()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsoleSetLeader(fakeConsoleSender, "setleader three lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("Kmlanglois", xTeam.tm.getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
