package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNotLeaderException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderSetLeaderTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamLeaderSetLeader()
	{
		Assert.assertTrue("setleader PLAYER".matches(new TeamLeaderSetLeader().getPattern()));
		Assert.assertTrue("setleader PLAYER ".matches(new TeamLeaderSetLeader().getPattern()));
		Assert.assertTrue("setl PLAYER".matches(new TeamLeaderSetLeader().getPattern()));
		Assert.assertTrue("setlead PLAYER ".matches(new TeamLeaderSetLeader().getPattern()));
		Assert.assertTrue("stl PLAYER ".matches(new TeamLeaderSetLeader().getPattern()));
		Assert.assertFalse("set PLAYER dfsa".matches(new TeamLeaderSetLeader().getPattern()));
		Assert.assertTrue(new TeamLeaderSetLeader().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamLeaderSetLeader().getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminSetLeaderExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "setleader protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("protocos is now the team leader (you are an admin)\n" +
				"You can now leave the team", fakePlayerSender.getLastMessage());
		Assert.assertEquals("protocos", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetLeaderExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "setleader protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetLeaderExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "setleader protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetLeaderExecuteNotTeammate()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "setleader newbie".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
