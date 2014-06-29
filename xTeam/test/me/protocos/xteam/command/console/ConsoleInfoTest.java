package me.protocos.xteam.command.console;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamInvalidCommandException;
import me.protocos.xteam.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeConsoleTeamEntity;
import me.protocos.xteam.message.MessageUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsoleInfoTest
{
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private FakeConsoleSender fakeConsoleSender;
	private ConsoleCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		teamCoordinator = teamPlugin.getTeamCoordinator();
		fakeConsoleSender = new FakeConsoleSender();
		fakeCommand = new ConsoleInfo(teamPlugin);
		teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeConsoleInfo()
	{
		Assert.assertTrue("info TEAM/PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info TEAM/PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("info".matches(fakeCommand.getPattern()));
		Assert.assertFalse("info ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleInfoExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info protocos".split(" ")));
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeamByPlayer("protocos").getInfoFor(new FakeConsoleTeamEntity())), fakeConsoleSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleInfoExecute2()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info two".split(" ")));
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("two").getInfoFor(new FakeConsoleTeamEntity())), fakeConsoleSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecuteNotRelativeLocation()
	{
		//ASSEMBLE
		Configuration.DISPLAY_RELATIVE_COORDINATES = true;
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info one".split(" ")));
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("one").getInfoFor(new FakeConsoleTeamEntity())), fakeConsoleSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
		Configuration.DISPLAY_RELATIVE_COORDINATES = false;
	}

	@Test
	public void ShouldBeConsoleInfoExecuteInvalidCommand()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamInvalidCommandException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleInfoExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleInfoExecuteTeamNotExists()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "info truck".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamOrPlayerDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
