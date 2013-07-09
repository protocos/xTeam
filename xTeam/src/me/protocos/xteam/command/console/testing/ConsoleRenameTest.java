package me.protocos.xteam.command.console.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.console.ConsoleRename;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamAlreadyExistsException;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamNameNotAlphaException;
import me.protocos.xteam.testing.FakeConsoleSender;
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
	public void ShouldBeConsoleRenameExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleRename(fakeConsoleSender, new CommandParser("/team rename one newname"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You renamed the team to newname", fakeConsoleSender.getLastMessage());
		Assert.assertEquals("newname", xTeam.tm.getTeam("newname").getName());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleRenameExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleRename(fakeConsoleSender, new CommandParser("/team rename two one"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamAlreadyExistsException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleRenameExecuteTeamNotExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleRename(fakeConsoleSender, new CommandParser("/team rename three one"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeConsoleRenameExecutnNameNotAlpha()
	{
		//ASSEMBLE
		Data.ALPHA_NUM = true;
		ConsoleCommand fakeCommand = new ConsoleRename(fakeConsoleSender, new CommandParser("/team rename two Ã"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.ALPHA_NUM = false;
	}
}
