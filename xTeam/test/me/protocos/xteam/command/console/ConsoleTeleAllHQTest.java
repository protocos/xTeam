package me.protocos.xteam.command.console;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleTeleAllHQTest
{
	private TeamPlugin teamPlugin;
	private FakeConsoleSender fakeConsoleSender;
	private ConsoleCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeConsoleSender = new FakeConsoleSender();
		fakeCommand = new ConsoleTeleAllHQ(teamPlugin);
	}

	@Test
	public void ShouldBeConsoleTeleAllHQ()
	{
		Assert.assertTrue("teleallhq".matches(fakeCommand.getPattern()));
		Assert.assertTrue("teleallhq ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("teleah".matches(fakeCommand.getPattern()));
		Assert.assertFalse("t".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tele ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "teleallhq".split(" ")));
		//ASSERT
		Assert.assertEquals("Players teleported", fakeConsoleSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
