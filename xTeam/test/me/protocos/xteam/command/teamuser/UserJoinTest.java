package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserJoinTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		Configuration.MAX_PLAYERS = 3;
		InviteHandler.addInvite("Lonely", xTeam.getInstance().getTeamManager().getTeam("one"));
	}
	@Test
	public void ShouldBeTeamUserJoinExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team join one"));
		//ASSERT
		Assert.assertEquals("You joined ONE", fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserJoinExecuteMaxPlayers()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").addPlayer("stranger");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team join one"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserJoinExecuteNoInvite()
	{
		//ASSEMBLE
		InviteHandler.removeInvite("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team join one"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserJoinExecuteOnlyJoinDefault()
	{
		//ASSEMBLE
		Configuration.DEFAULT_TEAM_ONLY = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team join one"));
		//ASSERT
		Assert.assertEquals((new TeamOnlyJoinDefaultException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserJoinExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		UserCommand fakeCommand = new UserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team join one"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserJoinExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team join three"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Configuration.DEFAULT_TEAM_ONLY = false;
		Configuration.MAX_PLAYERS = 0;
	}
}
