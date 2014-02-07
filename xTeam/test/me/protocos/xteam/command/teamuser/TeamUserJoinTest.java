package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserJoinTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		Configuration.MAX_PLAYERS = 3;
		ITeamPlayer playerSender = XTeam.getInstance().getPlayerManager().getPlayer("kmlanglois");
		ITeamPlayer playerReceiver = XTeam.getInstance().getPlayerManager().getPlayer("Lonely");
		Long timeSent = System.currentTimeMillis();
		InviteRequest request = new InviteRequest(playerSender, playerReceiver, timeSent);
		InviteHandler.addInvite(request);
	}

	@Test
	public void ShouldBeTeamUserJoin()
	{
		Assert.assertTrue("join TEAM".matches(new TeamUserJoin().getPattern()));
		Assert.assertTrue("join TEAM ".matches(new TeamUserJoin().getPattern()));
		Assert.assertTrue("j TEAM".matches(new TeamUserJoin().getPattern()));
		Assert.assertTrue("jn TEAM ".matches(new TeamUserJoin().getPattern()));
		Assert.assertFalse("j TEAM sdaf".matches(new TeamUserJoin().getPattern()));
		Assert.assertTrue(new TeamUserJoin().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserJoin().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserJoinExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals("You joined ONE", fakePlayerSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteMaxPlayers()
	{
		//ASSEMBLE
		XTeam.getInstance().getTeamManager().getTeam("one").addPlayer("stranger");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteNoInvite()
	{
		//ASSEMBLE
		InviteHandler.removeInvite("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteOnlyJoinDefault()
	{
		//ASSEMBLE
		Configuration.DEFAULT_TEAM_ONLY = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamOnlyJoinDefaultException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserJoin();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join three".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.DEFAULT_TEAM_ONLY = false;
		Configuration.MAX_PLAYERS = 0;
	}
}
