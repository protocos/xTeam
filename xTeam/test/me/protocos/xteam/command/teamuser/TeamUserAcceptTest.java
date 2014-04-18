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
import me.protocos.xteam.exception.TeamPlayerHasNoInviteException;
import me.protocos.xteam.exception.TeamPlayerHasTeamException;
import me.protocos.xteam.exception.TeamPlayerMaxException;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserAcceptTest
{

	private InviteHandler inviteHandler;

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
		inviteHandler = InviteHandler.getInstance();
		inviteHandler.addInvite(request);
	}

	@Test
	public void ShouldBeTeamUserAccept()
	{
		Assert.assertTrue("accept".matches(new TeamUserAccept().getPattern()));
		Assert.assertTrue("accept ".matches(new TeamUserAccept().getPattern()));
		Assert.assertTrue("acc".matches(new TeamUserAccept().getPattern()));
		Assert.assertTrue("acpt ".matches(new TeamUserAccept().getPattern()));
		Assert.assertTrue("a".matches(new TeamUserAccept().getPattern()));
		Assert.assertFalse("a dsafkln".matches(new TeamUserAccept().getPattern()));
		Assert.assertTrue(new TeamUserAccept().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserAccept().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserAcceptExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "accept".split(" ")));
		//ASSERT
		Assert.assertEquals("You joined ONE", fakePlayerSender.getLastMessage());
		Assert.assertFalse(inviteHandler.hasInvite("Lonely"));
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecuteMaxPlayers()
	{
		//ASSEMBLE
		XTeam.getInstance().getTeamManager().getTeam("one").addPlayer("stranger");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "accept".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecuteNoInvite()
	{
		//ASSEMBLE
		inviteHandler.removeInvite("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "accept".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "accept".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.MAX_PLAYERS = 0;
		inviteHandler.clear();
	}
}
