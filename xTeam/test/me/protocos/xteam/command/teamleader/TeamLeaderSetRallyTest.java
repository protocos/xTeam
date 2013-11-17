package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamAlreadyHasRallyException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNotLeaderException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderSetRallyTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamLeaderSetRally()
	{
		Assert.assertTrue("setrally".matches(new TeamLeaderSetRally().getPattern()));
		Assert.assertTrue("setr".matches(new TeamLeaderSetRally().getPattern()));
		Assert.assertTrue("setral ".matches(new TeamLeaderSetRally().getPattern()));
		Assert.assertTrue("str ".matches(new TeamLeaderSetRally().getPattern()));
		Assert.assertFalse("setrally fasds ".matches(new TeamLeaderSetRally().getPattern()));
		Assert.assertFalse("set tdfgvbnm".matches(new TeamLeaderSetRally().getPattern()));
		Assert.assertTrue(new TeamLeaderSetRally().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamLeaderSetRally().getPattern()));
	}

	@Test
	public void ShouldBeSetRally()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderSetRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setrally"));
		//ASSERT
		Assert.assertEquals("You set the team rally point", fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("one").hasRally());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeSetRallyPlayerHasNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderSetRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setrally"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeSetRallyPlayerNotTeamLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderSetRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setrally"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeSetRallyAlreadySet()
	{
		//ASSEMBLE
		Team team = xTeam.getInstance().getTeamManager().getTeam("one");
		team.setRally(team.getHeadquarters().getLocation());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderSetRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setrally"));
		//ASSERT
		Assert.assertEquals((new TeamAlreadyHasRallyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}