package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminSetLeaderTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminSetLeader()
	{
		Assert.assertTrue("setleader TEAM PLAYER".matches(new ServerAdminSetLeader().getPattern()));
		Assert.assertTrue("setleader TEAM PLAYER ".matches(new ServerAdminSetLeader().getPattern()));
		Assert.assertTrue("setl TEAM PLAYER".matches(new ServerAdminSetLeader().getPattern()));
		Assert.assertTrue("setlead TEAM PLAYER ".matches(new ServerAdminSetLeader().getPattern()));
		Assert.assertFalse("s TEAM PLAYER".matches(new ServerAdminSetLeader().getPattern()));
		Assert.assertFalse("sl TEAM PLAYER ".matches(new ServerAdminSetLeader().getPattern()));
		Assert.assertTrue(new ServerAdminSetLeader().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminSetLeader().getPattern()));
	}

	@Test
	public void ShouldBeServerAdminSetLeaderExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setleader one protocos"));
		//ASSERT
		Assert.assertEquals("protocos is now the team leader for ONE", fakePlayerSender.getLastMessage());
		Assert.assertEquals("protocos", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetLeaderExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setleader one newbie"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetLeaderExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setleader one Lonely"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetLeaderExecutePlayerNotOnTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setleader one mastermind"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetLeaderExecutePlayerTeamIsDefault()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setleader red strandedhelix"));
		//ASSERT
		Assert.assertEquals((new TeamIsDefaultException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetLeaderExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSetLeader();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team setleader three protocos"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("kmlanglois", xTeam.getInstance().getTeamManager().getTeam("one").getLeader());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
