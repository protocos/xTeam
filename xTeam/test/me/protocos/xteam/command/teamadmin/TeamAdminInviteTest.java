package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamAdminInviteTest
{
	private TeamPlugin teamPlugin;
	private TeamAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private InviteHandler inviteHandler;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamAdminInvite(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
		inviteHandler = teamPlugin.getInviteHandler();
	}

	@Test
	public void ShouldBeTeamAdminInvite()
	{
		Assert.assertTrue("invite PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("invite PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("inv PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("i PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertFalse("in PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("inv PLAYER 2".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminInviteExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals("You invited Lonely", fakePlayerSender.getLastMessage());
		Assert.assertTrue(inviteHandler.hasInvite("Lonely"));
		Assert.assertEquals(teamCoordinator.getTeam("one"), inviteHandler.getInviteTeam("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecuteAdmin()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").promote("protocos");
		FakePlayer fakePlayerSender = FakePlayer.from("protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals("You invited Lonely", fakePlayerSender.getLastMessage());
		Assert.assertTrue(inviteHandler.hasInvite("Lonely"));
		Assert.assertEquals(teamCoordinator.getTeam("one"), inviteHandler.getInviteTeam("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecuteNotAdmin()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerHasInvite()
	{
		//ASSEMBLE
		ITeamPlayer playerSender = playerFactory.getPlayer("kmlanglois");
		ITeamPlayer playerReceiver = playerFactory.getPlayer("Lonely");
		Long timeSent = System.currentTimeMillis();
		InviteRequest request = new InviteRequest(playerSender, playerReceiver, timeSent);
		inviteHandler.addInvite(request);
		FakePlayer fakePlayerSender = FakePlayer.from("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(inviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite newbie".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(inviteHandler.hasInvite("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite mastermind".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(inviteHandler.hasInvite("mastermind"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecuteSelfInvite()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite kmlanglois".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerInviteException("Player cannot invite self")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(inviteHandler.hasInvite("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecuteAlreadyOnTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerAlreadyOnTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(inviteHandler.hasInvite("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
