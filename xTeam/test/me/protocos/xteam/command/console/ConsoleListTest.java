package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
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
	public void ShouldBeTeamUserListExecuteNoTeams()
	{
		//ASSEMBLE
		xTeam.getTeamManager().clear();
		ConsoleCommand fakeCommand = new ConsoleList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team list"));
		//ASSERT
		Assert.assertEquals("There are no teams", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserListExecuteOneTeam()
	{
		//ASSEMBLE
		xTeam.getTeamManager().removeTeam("ONE");
		xTeam.getTeamManager().removeTeam("TWO");
		xTeam.getTeamManager().removeTeam("blue");
		ConsoleCommand fakeCommand = new ConsoleList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team list"));
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
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team list"));
		//ASSERT
		Assert.assertEquals("Teams: ONE, two, red, blue", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
