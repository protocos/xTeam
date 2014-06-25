package me.protocos.xteam.command.console;

import org.junit.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamNameAlreadyInUseException;
import me.protocos.xteam.exception.TeamNameNotAlphaException;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleRenameTest
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
		fakeCommand = new ConsoleRename(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeConsoleRename()
	{
		Assert.assertTrue("rename TEAM NAME".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rename TEAM NAME ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ren TEAM NAME ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rn TEAM NAME ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("re TEAM NAME ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("r TEAM NAME".matches(fakeCommand.getPattern()));
		Assert.assertFalse("re TEAM NAME ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("r".matches(fakeCommand.getPattern()));
		Assert.assertFalse("re".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleRenameExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "rename one newname".split(" ")));
		//ASSERT
		Assert.assertEquals("You renamed the team to newname", fakeConsoleSender.getLastMessage());
		Assert.assertEquals("newname", teamCoordinator.getTeam("newname").getName());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRenameExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "rename two one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameAlreadyInUseException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRenameExecuteTeamNotExists()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "rename three one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRenameExecutnNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "rename two € EÃ¥m".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
	}
}
