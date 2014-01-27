package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleListTest
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
	public void ShouldBeConsoleList()
	{
		Assert.assertTrue("list".matches(new ConsoleList().getPattern()));
		Assert.assertTrue("list ".matches(new ConsoleList().getPattern()));
		Assert.assertTrue("l".matches(new ConsoleList().getPattern()));
		Assert.assertTrue("l ".matches(new ConsoleList().getPattern()));
		Assert.assertTrue("li".matches(new ConsoleList().getPattern()));
		Assert.assertTrue("li ".matches(new ConsoleList().getPattern()));
		Assert.assertTrue("ls".matches(new ConsoleList().getPattern()));
		Assert.assertTrue("ls ".matches(new ConsoleList().getPattern()));
		Assert.assertFalse("ls1".matches(new ConsoleList().getPattern()));
		Assert.assertFalse("ls 1".matches(new ConsoleList().getPattern()));
		Assert.assertTrue(new ConsoleList().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleList().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserListExecuteNoTeams()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().clear();
		ConsoleCommand fakeCommand = new ConsoleList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "list".split(" ")));
		//ASSERT
		Assert.assertEquals("There are no teams", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserListExecuteOneTeam()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().disbandTeam("ONE");
		xTeam.getInstance().getTeamManager().disbandTeam("TWO");
		xTeam.getInstance().getTeamManager().disbandTeam("blue");
		ConsoleCommand fakeCommand = new ConsoleList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "list".split(" ")));
		//ASSERT
		Assert.assertEquals("Teams: red", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleListExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "list".split(" ")));
		//ASSERT
		Assert.assertEquals("Teams: ONE, two, red, blue", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
