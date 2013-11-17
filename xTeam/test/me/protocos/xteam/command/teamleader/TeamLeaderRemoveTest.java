package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.core.exception.TeamPlayerNotLeaderException;
import me.protocos.xteam.core.exception.TeamPlayerNotTeammateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderRemoveTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamLeaderRemove()
	{
		Assert.assertTrue("remove PLAYER".matches(new TeamLeaderRemove().getPattern()));
		Assert.assertTrue("remove PLAYER ".matches(new TeamLeaderRemove().getPattern()));
		Assert.assertTrue("rm PLAYER".matches(new TeamLeaderRemove().getPattern()));
		Assert.assertTrue("rem PLAYER".matches(new TeamLeaderRemove().getPattern()));
		Assert.assertTrue("remv PLAYER ".matches(new TeamLeaderRemove().getPattern()));
		Assert.assertFalse("rem PLAYER dfsa ".matches(new TeamLeaderRemove().getPattern()));
		Assert.assertTrue(new TeamLeaderRemove().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamLeaderRemove().getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminRemoveExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team remove protocos"));
		//ASSERT
		Assert.assertEquals("You removed protocos from your team", fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRemoveExecuteLeaderLeave()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team remove kmlanglois"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRemoveExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team remove protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRemoveExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team remove protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRemoveExecuteNotTeammate()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRemove();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team remove mastermind"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
