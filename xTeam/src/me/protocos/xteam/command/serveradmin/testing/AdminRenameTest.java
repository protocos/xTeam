package me.protocos.xteam.command.serveradmin.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.serveradmin.AdminRename;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamAlreadyExistsException;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamNameNotAlphaException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminRenameTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeServerAdminRenameExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rename one newname"));
		//ASSERT
		Assert.assertEquals("You renamed the team to newname", fakePlayerSender.getLastMessage());
		Assert.assertEquals("newname", xTeam.tm.getTeam("newname").getName());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminRenameExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rename one two"));
		//ASSERT
		Assert.assertEquals((new TeamAlreadyExistsException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminRenameExecuteTeamNotAlpha()
	{
		//ASSEMBLE
		Data.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rename one ƒçß"));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminRenameExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rename three newname"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.ALPHA_NUM = false;
	}
}
