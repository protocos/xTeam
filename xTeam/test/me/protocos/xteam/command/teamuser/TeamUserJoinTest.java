package me.protocos.xteam.command.teamuser;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.core.IPlayerManager;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserJoinTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private ITeamManager teamManager;
	private IPlayerManager playerFactory;
	private InviteHandler inviteHandler;

	@Before
	public void setup()
	{
		Configuration.MAX_PLAYERS = 3;
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserJoin(teamPlugin);
		teamManager = teamPlugin.getTeamManager();
		playerFactory = teamPlugin.getPlayerManager();
		inviteHandler = teamPlugin.getInviteHandler();
		ITeamPlayer playerSender = playerFactory.getPlayer("kmlanglois");
		ITeamPlayer playerReceiver = playerFactory.getPlayer("Lonely");
		Long timeSent = System.currentTimeMillis();
		InviteRequest request = new InviteRequest(playerSender, playerReceiver, timeSent);
		inviteHandler.addInvite(request);
	}

	@Test
	public void ShouldBeTeamUserJoin()
	{
		Assert.assertTrue("join TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("join TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("j TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("jn TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("j TEAM sdaf".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserJoinExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals("You joined ONE", fakePlayerSender.getLastMessage());
		Assert.assertTrue(teamManager.getTeam("one").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteMaxPlayers()
	{
		//ASSEMBLE
		teamManager.getTeam("one").addPlayer("stranger");
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamManager.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteNoInvite()
	{
		//ASSEMBLE
		inviteHandler.removeInvite("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamManager.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteOnlyJoinDefault()
	{
		//ASSEMBLE
		Configuration.DEFAULT_TEAM_ONLY = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamOnlyJoinDefaultException(teamManager)).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamManager.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamManager.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "join three".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamManager.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.DEFAULT_TEAM_ONLY = false;
		Configuration.MAX_PLAYERS = 0;
	}
}
