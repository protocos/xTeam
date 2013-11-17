package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNeverPlayedException;
import me.protocos.xteam.core.exception.TeamPlayerNotOnTeamException;
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
	public void ShouldBeConsolePromote()
	{
		Assert.assertTrue("promote TEAM PLAYER".matches(new ConsolePromote().getPattern()));
		Assert.assertTrue("promote TEAM PLAYER ".matches(new ConsolePromote().getPattern()));
		Assert.assertTrue("pr TEAM PLAYER".matches(new ConsolePromote().getPattern()));
		Assert.assertTrue("prom TEAM PLAYER ".matches(new ConsolePromote().getPattern()));
		Assert.assertFalse("promote TEAM".matches(new ConsolePromote().getPattern()));
		Assert.assertFalse("promote TEAM ".matches(new ConsolePromote().getPattern()));
		Assert.assertFalse("promote".matches(new ConsolePromote().getPattern()));
		Assert.assertFalse("promote ".matches(new ConsolePromote().getPattern()));
		Assert.assertTrue(new ConsolePromote().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsolePromote().getPattern()));
	}

	@Test
	public void ShouldBeConsolePromoteExecute()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").demote("protocos");
		ConsoleCommand fakeCommand = new ConsolePromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team promote one protocos"));
		//ASSERT
		Assert.assertEquals("You promoted protocos", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsolePromoteExecuteIncorrectTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsolePromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team promote one mastermind"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("mastermind"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsolePromoteExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsolePromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team promote one Lonely"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsolePromoteExecutePlayerHasNotPlayed()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsolePromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team promote one newbie"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsolePromoteExecuteTeamNotExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsolePromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team promote three protocos"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
