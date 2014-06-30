package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamPlayerHasNoInviteException;
import me.protocos.xteam.exception.TeamPlayerHasTeamException;
import me.protocos.xteam.exception.TeamPlayerMaxException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserAcceptTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private InviteHandler inviteHandler;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserAccept(teamPlugin);
		inviteHandler = teamPlugin.getInviteHandler();
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
		Configuration.MAX_PLAYERS = 3;
		ITeamPlayer playerSender = playerFactory.getPlayer("kmlanglois");
		ITeamPlayer playerReceiver = playerFactory.getPlayer("Lonely");
		Long timeSent = System.currentTimeMillis();
		InviteRequest request = new InviteRequest(playerSender, playerReceiver, timeSent);
		inviteHandler.addInvite(request);
	}

	@Test
	public void ShouldBeTeamUserAccept()
	{
		Assert.assertTrue("accept".matches(fakeCommand.getPattern()));
		Assert.assertTrue("accept ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("acc".matches(fakeCommand.getPattern()));
		Assert.assertTrue("acpt ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("a".matches(fakeCommand.getPattern()));
		Assert.assertFalse("a dsafkln".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserAcceptExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "accept");
		//ASSERT
		Assert.assertEquals("You joined ONE", fakePlayerSender.getLastMessages());
		Assert.assertFalse(inviteHandler.hasInvite("Lonely"));
		Assert.assertTrue(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecuteMaxPlayers()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").addPlayer("stranger");
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "accept");
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertTrue(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecuteNoInvite()
	{
		//ASSEMBLE
		inviteHandler.removeInvite("Lonely");
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "accept");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecuteTeamNoLongerExists()
	{
		//ASSEMBLE
		teamCoordinator.disbandTeam("ONE");
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "accept");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "accept");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertTrue(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.MAX_PLAYERS = 0;
	}
}
