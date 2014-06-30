package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserJoinTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private InviteHandler inviteHandler;

	@Before
	public void setup()
	{
		Configuration.MAX_PLAYERS = 3;
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserJoin(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
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
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "join one");
		//ASSERT
		Assert.assertEquals("You joined ONE", fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteMaxPlayers()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").addPlayer("stranger");
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "join one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteNoInvite()
	{
		//ASSEMBLE
		inviteHandler.removeInvite("Lonely");
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "join one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteOnlyJoinDefault()
	{
		//ASSEMBLE
		Configuration.DEFAULT_TEAM_ONLY = true;
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "join one");
		//ASSERT
		Assert.assertEquals((new TeamOnlyJoinDefaultException(teamCoordinator)).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "join one");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserJoinExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "join three");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.DEFAULT_TEAM_ONLY = false;
		Configuration.MAX_PLAYERS = 0;
	}
}
