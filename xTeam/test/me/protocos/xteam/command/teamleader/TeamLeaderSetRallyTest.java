package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamAlreadyHasRallyException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotLeaderException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderSetRallyTest
{
	private TeamPlugin teamPlugin;
	private TeamLeaderCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamLeaderSetRally(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamLeaderSetRally()
	{
		Assert.assertTrue("setrally".matches(fakeCommand.getPattern()));
		Assert.assertTrue("setr".matches(fakeCommand.getPattern()));
		Assert.assertTrue("setral ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("str ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("setrally fasds ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("set tdfgvbnm".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeSetRally()
	{
		//ASSEMBLE
		Configuration.RALLY_DELAY = 2;
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "setrally");
		//ASSERT
		Assert.assertEquals("You set the team rally point (expires in 2 minutes)", fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").hasRally());
		Assert.assertTrue(fakeExecuteResponse);
		Configuration.RALLY_DELAY = 0;
	}

	@Test
	public void ShouldBeSetRallyPlayerHasNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "setrally");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeSetRallyPlayerNotTeamLeader()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "setrally");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeSetRallyAlreadySet()
	{
		//ASSEMBLE
		ITeam team = teamCoordinator.getTeam("one");
		team.setRally(team.getHeadquarters().getLocation());
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "setrally");
		//ASSERT
		Assert.assertEquals((new TeamAlreadyHasRallyException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}