package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
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
		Assert.assertTrue("teleahq".matches(fakeCommand.getPattern()));
		Assert.assertFalse("t".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tele ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleTeleAllHQExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "teleallhq");
		//ASSERT
		Assert.assertEquals("Players teleported", fakeConsoleSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
