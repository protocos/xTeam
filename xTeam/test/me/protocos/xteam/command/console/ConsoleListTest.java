package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsoleListTest
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
		fakeCommand = new ConsoleList(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeConsoleList()
	{
		Assert.assertTrue("list".matches(fakeCommand.getPattern()));
		Assert.assertTrue("list ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("l".matches(fakeCommand.getPattern()));
		Assert.assertTrue("l ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("li".matches(fakeCommand.getPattern()));
		Assert.assertTrue("li ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ls".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ls ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("ls1".matches(fakeCommand.getPattern()));
		Assert.assertFalse("ls 1".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserListExecuteNoTeams()
	{
		//ASSEMBLE
		teamCoordinator.clear();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "list");
		//ASSERT
		Assert.assertEquals("There are no teams", fakeConsoleSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserListExecuteOneTeam()
	{
		//ASSEMBLE
		teamCoordinator.disbandTeam("ONE");
		teamCoordinator.disbandTeam("TWO");
		teamCoordinator.disbandTeam("blue");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "list");
		//ASSERT
		Assert.assertEquals("Teams: red", fakeConsoleSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleListExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "list");
		//ASSERT
		Assert.assertEquals("Teams: ONE, two, red, blue", fakeConsoleSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
