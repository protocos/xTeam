package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerLeaderDemoteException;
import me.protocos.xteam.exception.TeamPlayerNotLeaderException;
import me.protocos.xteam.exception.TeamPlayerNotTeammateException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderDemoteTest
{
	private TeamPlugin teamPlugin;
	private TeamLeaderCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamLeaderDemote(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		teamCoordinator.getTeam("one").promote("protocos");
	}

	@Test
	public void ShouldBeTeamLeaderDemote()
	{
		Assert.assertTrue("demote PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("demote PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("dmte PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("d PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("dmte PLAYER dfsg ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminDemoteExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "demote protocos");
		//ASSERT
		Assert.assertEquals("You demoted protocos", fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminDemoteExecuteLeaderDemote()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "demote kmlanglois");
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderDemoteException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminDemoteExecuteSenderNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "demote protocos");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminDemoteExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "demote Lonely");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminDemoteExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "demote protocos");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminDemoteExecuteNotTeammate()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "demote mastermind");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
