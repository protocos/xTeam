package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotAdminException;
import me.protocos.xteam.exception.TeamPlayerNotTeammateException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamAdminPromoteTest
{
	private TeamPlugin teamPlugin;
	private TeamAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamAdminPromote(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamAdminPromote()
	{
		Assert.assertTrue("promote PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("promote PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("p PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("pmte PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("promote PLAYER dfsagf".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote protocos");
		//ASSERT
		Assert.assertEquals("You promoted protocos", fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote kmlanglois");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote protocos");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNotTeammate()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote Lonely");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
