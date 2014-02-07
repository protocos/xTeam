package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotLeaderException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderDisbandTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamLeaderDisband()
	{
		Assert.assertTrue("disband".matches(new TeamLeaderDisband().getPattern()));
		Assert.assertTrue("disband  ".matches(new TeamLeaderDisband().getPattern()));
		Assert.assertFalse("d ".matches(new TeamLeaderDisband().getPattern()));
		Assert.assertFalse("disband  fdsa ".matches(new TeamLeaderDisband().getPattern()));
		Assert.assertTrue(new TeamLeaderDisband().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamLeaderDisband().getPattern()));
	}

	@Test
	public void ShouldBeDisbandExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "disband".split(" ")));
		//ASSERT
		Assert.assertEquals("You disbanded your team", fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().containsTeam("one"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeDisbandExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "disband".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().containsTeam("one"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamOpenExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderDisband();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "disband".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().containsTeam("one"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}