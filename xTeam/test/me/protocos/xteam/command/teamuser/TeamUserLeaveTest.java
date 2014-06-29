package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserLeaveTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserLeave(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamUserLeave()
	{
		Assert.assertTrue("leave".matches(fakeCommand.getPattern()));
		Assert.assertTrue("leave ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("lv  ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("l ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("l sdaf".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeDefaultTeamLeavingOnePerson()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("strandedhelix");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals("You left red", fakePlayerSender.getLastMessage());
		Assert.assertTrue(teamCoordinator.containsTeam("red"));
		Assert.assertFalse(teamCoordinator.getTeam("red").containsPlayer("strandedhelix"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeRegularTeamLeavingOnePerson()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("mastermind");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals("You left two", fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.containsTeam("two"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals("You left ONE", fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecuteLeaderLeaving()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "leave".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(teamCoordinator.getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("Lonely");
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
