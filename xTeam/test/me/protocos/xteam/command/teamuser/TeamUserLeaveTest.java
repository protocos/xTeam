package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerLeaderLeavingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserLeaveTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
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
	public void ShouldBeDefaultTeamLeavingOnePerson()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("strandedhelix", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals("You left red", fakePlayerSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().containsTeam("red"));
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("red").containsPlayer("strandedhelix"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeRegularTeamLeavingOnePerson()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals("You left two", fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().containsTeam("two"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals("You left ONE", fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecuteLeaderLeaving()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
