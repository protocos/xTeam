package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.teamuser.UserCreate;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamUserCreateExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserCreate(fakePlayerSender, new CommandParser("/team create newteam"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You created newteam", fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.contains("newteam"));
		Assert.assertTrue(xTeam.tm.getTeam("newteam").getPlayers().contains("Lonely"));
		Assert.assertTrue(xTeam.tm.getTeam("newteam").getAdmins().contains("Lonely"));
		Assert.assertTrue(xTeam.tm.getTeam("newteam").getLeader().equals("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserCreateExecuteCapitalLetters()
	{
		//ASSEMBLE
		Data.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserCreate(fakePlayerSender, new CommandParser("/team create NEW"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You created NEW", fakePlayerSender.getLastMessage());
		Assert.assertEquals("NEW", xTeam.tm.getTeam("NEW").getName());
		Assert.assertTrue(xTeam.tm.contains("NEW"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserCreateExecuteNameTooLong()
	{
		//ASSEMBLE
		Data.TEAM_TAG_LENGTH = 10;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserCreate(fakePlayerSender, new CommandParser("/team create newteamiswaytoolong"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.contains("newteamiswaytoolong"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserCreateExecuteOnlyDefault()
	{
		//ASSEMBLE
		Data.DEFAULT_TEAM_ONLY = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserCreate(fakePlayerSender, new CommandParser("/team create newteam"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamOnlyJoinDefaultException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.contains("newteam"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserCreateExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		UserCommand fakeCommand = new UserCreate(fakePlayerSender, new CommandParser("/team create newteam"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.contains("newteam"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserCreateExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserCreate(fakePlayerSender, new CommandParser("/team create one"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamAlreadyExistsException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.contains("one"));
		Assert.assertFalse(xTeam.tm.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserCreateExecuteTeamCreatedRecently()
	{
		//ASSEMBLE
		Data.CREATE_INTERVAL = 1;
		Data.lastCreated.put("Lonely", System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserCreate(fakePlayerSender, new CommandParser("/team create newteam"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamCreatedRecentlyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.contains("newteam"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserCreateExecuteTeamNotAlpha()
	{
		//ASSEMBLE
		Data.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserCreate(fakePlayerSender, new CommandParser("/team create ���"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.contains("���"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.DEFAULT_TEAM_ONLY = false;
		Data.TEAM_TAG_LENGTH = 0;
		Data.CREATE_INTERVAL = 0;
		Data.ALPHA_NUM = false;
	}
}
