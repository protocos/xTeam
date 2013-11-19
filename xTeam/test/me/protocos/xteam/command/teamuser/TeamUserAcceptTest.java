package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamPlayerHasNoInviteException;
import me.protocos.xteam.core.exception.TeamPlayerHasTeamException;
import me.protocos.xteam.core.exception.TeamPlayerMaxException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserAcceptTest
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
		Assert.assertFalse(InviteHandler.hasInvite("Lonely"));
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecuteMaxPlayers()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").addPlayer("stranger");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "accept".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(InviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserAcceptExecuteNoInvite()
	{
		//ASSEMBLE
		InviteHandler.removeInvite("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserAccept();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "accept".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(InviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
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
		Assert.assertTrue(InviteHandler.hasInvite("Lonely"));
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.MAX_PLAYERS = 0;
		InviteHandler.clear();
	}
}
