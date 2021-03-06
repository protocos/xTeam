package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamNameAlreadyInUseException;
import me.protocos.xteam.exception.TeamNameNotAlphaException;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsoleTagTest
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
		fakeCommand = new ConsoleTag(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamConsoleTag()
	{
		Assert.assertTrue("tag TEAM TAG".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tag TEAM TAG ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tag TEAM TAG ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("t TEAM TAG ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ta TEAM TAG".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tg TEAM TAG ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tg TEAM TAG sdfhkabkl".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleTagExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "tag one three");
		//ASSERT
		Assert.assertEquals("The team tag has been set to three", fakeConsoleSender.getLastMessages());
		Assert.assertEquals("three", teamCoordinator.getTeam("one").getTag());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleTagExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "tag two one");
		//ASSERT
		Assert.assertEquals((new TeamNameAlreadyInUseException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleTagExecuteTeamNotExists()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "tag three one");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleTagExecutnNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakeConsoleSender, fakeCommand, "tag two ????");
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
	}
}
