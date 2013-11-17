package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamNameConflictsWithNameException;
import me.protocos.xteam.core.exception.TeamNameNotAlphaException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleTagTest
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
	public void ShouldBeTeamConsoleTag()
	{
		Assert.assertTrue("tag TEAM TAG".matches(new ConsoleTag().getPattern()));
		Assert.assertTrue("tag TEAM TAG ".matches(new ConsoleTag().getPattern()));
		Assert.assertTrue("tag TEAM TAG ".matches(new ConsoleTag().getPattern()));
		Assert.assertTrue("t TEAM TAG ".matches(new ConsoleTag().getPattern()));
		Assert.assertTrue("ta TEAM TAG".matches(new ConsoleTag().getPattern()));
		Assert.assertTrue("tg TEAM TAG ".matches(new ConsoleTag().getPattern()));
		Assert.assertFalse("tg TEAM TAG sdfhkabkl".matches(new ConsoleTag().getPattern()));
		Assert.assertTrue(new ConsoleTag().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleTag().getPattern()));
	}

	@Test
	public void ShouldBeConsoleTagExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team tag one three"));
		//ASSERT
		Assert.assertEquals("The team tag has been set to three", fakeConsoleSender.getLastMessage());
		Assert.assertEquals("three", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleTagExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team tag two one"));
		//ASSERT
		Assert.assertEquals((new TeamNameConflictsWithNameException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleTagExecuteTeamNotExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team tag three one"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleTagExecutnNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		ConsoleCommand fakeCommand = new ConsoleTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team tag two Ã"));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
	}
}
