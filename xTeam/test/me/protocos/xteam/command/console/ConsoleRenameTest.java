package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.configuration.Configuration;
import me.protocos.xteam.exception.TeamAlreadyExistsException;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamNameNotAlphaException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleRenameTest
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
	public void ShouldBeConsoleRename()
	{
		Assert.assertTrue("rename TEAM NAME".matches(new ConsoleRename().getPattern()));
		Assert.assertTrue("rename TEAM NAME ".matches(new ConsoleRename().getPattern()));
		Assert.assertTrue("ren TEAM NAME ".matches(new ConsoleRename().getPattern()));
		Assert.assertTrue("rn TEAM NAME ".matches(new ConsoleRename().getPattern()));
		Assert.assertFalse("re TEAM NAME ".matches(new ConsoleRename().getPattern()));
		Assert.assertFalse("r TEAM NAME".matches(new ConsoleRename().getPattern()));
		Assert.assertFalse("re TEAM NAME ".matches(new ConsoleRename().getPattern()));
		Assert.assertFalse("r".matches(new ConsoleRename().getPattern()));
		Assert.assertFalse("re".matches(new ConsoleRename().getPattern()));
		Assert.assertTrue(new ConsoleRename().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleRename().getPattern()));
	}

	@Test
	public void ShouldBeConsoleRenameExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "rename one newname".split(" ")));
		//ASSERT
		Assert.assertEquals("You renamed the team to newname", fakeConsoleSender.getLastMessage());
		Assert.assertEquals("newname", xTeam.getInstance().getTeamManager().getTeam("newname").getName());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRenameExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "rename two one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamAlreadyExistsException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.getInstance().getTeamManager().getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRenameExecuteTeamNotExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "rename three one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.getInstance().getTeamManager().getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleRenameExecutnNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		ConsoleCommand fakeCommand = new ConsoleRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "rename two ¡™£¢".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.getInstance().getTeamManager().getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
	}
}
