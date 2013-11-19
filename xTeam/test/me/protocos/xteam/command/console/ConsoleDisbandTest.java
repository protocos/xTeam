package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamIsDefaultException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleDisbandTest
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
	public void ShouldBeConsoleDisband()
	{
		Assert.assertTrue("disband TEAM".matches(new ConsoleDisband().getPattern()));
		Assert.assertTrue("disband TEAM ".matches(new ConsoleDisband().getPattern()));
		Assert.assertFalse("disband".matches(new ConsoleDisband().getPattern()));
		Assert.assertFalse("disband ".matches(new ConsoleDisband().getPattern()));
		Assert.assertFalse("d TEAM".matches(new ConsoleDisband().getPattern()));
		Assert.assertTrue(new ConsoleDisband().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleDisband().getPattern()));
	}

	@Test
	public void ShouldBeConsoleDisbandExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "disband one".split(" ")));
		//ASSERT
		Assert.assertEquals("You disbanded ONE [TeamAwesome]", fakeConsoleSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().containsTeam("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleDisbandExecuteTeamDoesNotExixts()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "disband ooga".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleDisbandExecuteTeamIsDefault()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "disband RED".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamIsDefaultException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().containsTeam("RED"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
