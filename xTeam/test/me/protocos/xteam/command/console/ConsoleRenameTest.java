package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.exception.TeamAlreadyExistsException;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamNameNotAlphaException;
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
		ConsoleCommand fakeCommand = new ConsoleRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team rename one newname"));
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
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team rename two one"));
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
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team rename three one"));
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
		boolean fakeExecuteResponse = fakeCommand.execute(fakeConsoleSender, new CommandParser("/team rename two Ã"));
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
