package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamAdminInviteTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		InviteHandler.clear();
	}

	@Test
	public void ShouldBeTeamAdminInvite()
	{
		Assert.assertTrue("invite PLAYER".matches(new TeamAdminInvite().getPattern()));
		Assert.assertTrue("invite PLAYER ".matches(new TeamAdminInvite().getPattern()));
		Assert.assertTrue("inv PLAYER ".matches(new TeamAdminInvite().getPattern()));
		Assert.assertFalse("i PLAYER".matches(new TeamAdminInvite().getPattern()));
		Assert.assertFalse("in PLAYER ".matches(new TeamAdminInvite().getPattern()));
		Assert.assertFalse("inv PLAYER 2".matches(new TeamAdminInvite().getPattern()));
		Assert.assertTrue(new TeamAdminInvite().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamAdminInvite().getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminInviteExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminInvite();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals("You invited Lonely", fakePlayerSender.getLastMessage());
		Assert.assertTrue(InviteHandler.hasInvite("Lonely"));
		Assert.assertEquals(xTeam.getInstance().getTeamManager().getTeam("one"), InviteHandler.getInviteTeam("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecuteAdmin()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminInvite();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals("You invited Lonely", fakePlayerSender.getLastMessage());
		Assert.assertTrue(InviteHandler.hasInvite("Lonely"));
		Assert.assertEquals(xTeam.getInstance().getTeamManager().getTeam("one"), InviteHandler.getInviteTeam("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecuteNotAdmin()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminInvite();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(InviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerHasInvite()
	{
		//ASSEMBLE
		ITeamPlayer playerSender = xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois");
		ITeamPlayer playerReceiver = xTeam.getInstance().getPlayerManager().getPlayer("Lonely");
		Long timeSent = System.currentTimeMillis();
		InviteRequest request = new InviteRequest(playerSender, playerReceiver, timeSent);
		InviteHandler.addInvite(request);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminInvite();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(InviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminInvite();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite newbie".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(InviteHandler.hasInvite("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminInvite();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite mastermind".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(InviteHandler.hasInvite("mastermind"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminInviteExecuteSelfInvite()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminInvite();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "invite kmlanglois".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerInviteException("Player cannot invite self")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(InviteHandler.hasInvite("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
