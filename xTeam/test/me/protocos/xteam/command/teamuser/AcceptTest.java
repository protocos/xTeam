package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamPlayerHasNoInviteException;
import me.protocos.xteam.core.exception.TeamPlayerHasTeamException;
import me.protocos.xteam.core.exception.TeamPlayerMaxException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AcceptTest
{

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		Data.MAX_PLAYERS = 3;
		InviteHandler.addInvite("Lonely", xTeam.getTeamManager().getTeam("one"));
	}
	@Test
	public void ShouldBeTeamUserAcceptExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team accept"));
		//ASSERT
		Assert.assertEquals("You joined ONE", fakePlayerSender.getLastMessage());
		Assert.assertFalse(InviteHandler.hasInvite("Lonely"));
		Assert.assertTrue(xTeam.getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserAcceptExecuteMaxPlayers()
	{
		//ASSEMBLE
		xTeam.getTeamManager().getTeam("one").addPlayer("stranger");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team accept"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(InviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(xTeam.getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserAcceptExecuteNoInvite()
	{
		//ASSEMBLE
		InviteHandler.removeInvite("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team accept"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(InviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(xTeam.getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserAcceptExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		UserCommand fakeCommand = new UserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team accept"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(InviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(xTeam.getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.MAX_PLAYERS = 0;
		InviteHandler.clear();
	}
}
