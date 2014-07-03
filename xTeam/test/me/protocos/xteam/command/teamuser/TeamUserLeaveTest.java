package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
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
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeDefaultTeamLeavingOnePerson()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("strandedhelix");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "leave");
		//ASSERT
		Assert.assertEquals("You left red", fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.containsTeam("red"));
		Assert.assertFalse(teamCoordinator.getTeam("red").containsPlayer("strandedhelix"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeRegularTeamLeavingOnePerson()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("mastermind");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "leave");
		//ASSERT
		Assert.assertEquals("You left two", fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.containsTeam("two"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "leave");
		//ASSERT
		Assert.assertEquals("You left ONE", fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecuteLeaderLeaving()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "leave");
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserLeaveExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "leave");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
