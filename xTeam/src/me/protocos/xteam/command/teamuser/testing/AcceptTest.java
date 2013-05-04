package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamuser.Accept;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamPlayerHasNoInviteException;
import me.protocos.xteam.core.exception.TeamPlayerHasTeamException;
import me.protocos.xteam.core.exception.TeamPlayerMaxException;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
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
		Data.invites.put("lonely", xTeam.tm.getTeam("one"));
	}
	@Test
	public void ShouldBeTeamUserAcceptExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		BaseUserCommand fakeCommand = new Accept(fakePlayerSender, "accept");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You joined ONE", fakePlayerSender.getLastMessage());
		Assert.assertFalse(Data.invites.containsKey("lonely"));
		Assert.assertTrue(xTeam.tm.getTeam("one").contains("lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserAcceptExecuteMaxPlayers()
	{
		//ASSEMBLE
		xTeam.tm.getTeam("one").addPlayer("stranger");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		BaseUserCommand fakeCommand = new Accept(fakePlayerSender, "accept");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(Data.invites.containsKey("lonely"));
		Assert.assertFalse(xTeam.tm.getTeam("one").contains("lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserAcceptExecuteNoInvite()
	{
		//ASSEMBLE
		Data.invites.remove("lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		BaseUserCommand fakeCommand = new Accept(fakePlayerSender, "accept");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(Data.invites.containsKey("lonely"));
		Assert.assertFalse(xTeam.tm.getTeam("one").contains("lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserAcceptExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		BaseUserCommand fakeCommand = new Accept(fakePlayerSender, "accept");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(Data.invites.containsKey("lonely"));
		Assert.assertFalse(xTeam.tm.getTeam("one").contains("lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.MAX_PLAYERS = 0;
		Data.invites.clear();
	}
}
