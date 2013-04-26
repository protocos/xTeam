package me.protocos.xteam.command.console.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.command.console.ConsolePromote;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNeverPlayedException;
import me.protocos.xteam.core.exception.TeamPlayerNotOnTeamException;
import me.protocos.xteam.testing.FakeConsoleSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsolePromoteTest
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
	public void ShouldBeConsolePromoteExecute()
	{
		//ASSEMBLE
		xTeam.tm.getTeam("one").demote("protocos");
		BaseConsoleCommand fakeCommand = new ConsolePromote(fakeConsoleSender, "promote one protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You promoted protocos", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsolePromoteExecuteIncorrectTeam()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsolePromote(fakeConsoleSender, "promote one mastermind");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("mastermind"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsolePromoteExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsolePromote(fakeConsoleSender, "promote one lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsolePromoteExecutePlayerHasNotPlayed()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsolePromote(fakeConsoleSender, "promote one newbie");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsolePromoteExecuteTeamNotExists()
	{
		//ASSEMBLE
		BaseConsoleCommand fakeCommand = new ConsolePromote(fakeConsoleSender, "promote three protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
