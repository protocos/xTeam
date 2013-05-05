package me.protocos.xteam.command.teamleader.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamleader.Rename;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenameTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamAdminRenameExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Rename(fakePlayerSender, "rename name");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You renamed the team to name", fakePlayerSender.getLastMessage());
		Assert.assertEquals("name", xTeam.tm.getTeam("name").getName());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminRenameExecuteAlreadyExists()
	{
		//ASSEMBLE
		Data.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Rename(fakePlayerSender, "rename two");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamAlreadyExistsException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminRenameExecuteNameNotAlpha()
	{
		//ASSEMBLE
		Data.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Rename(fakePlayerSender, "rename ÃºÃ");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminRenameExecutenNmeTooLong()
	{
		//ASSEMBLE
		Data.TEAM_TAG_LENGTH = 10;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Rename(fakePlayerSender, "rename nameiswaytoolong");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminRenameExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		BaseUserCommand fakeCommand = new Rename(fakePlayerSender, "rename name");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminRenameExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		BaseUserCommand fakeCommand = new Rename(fakePlayerSender, "rename name");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.tm.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.ALPHA_NUM = false;
		Data.TEAM_TAG_LENGTH = 0;
	}
}
