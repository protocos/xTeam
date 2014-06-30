package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNeverPlayedException;
import me.protocos.xteam.exception.TeamPlayerNotOnTeamException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminPromoteTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminPromote(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeServerAdminPromote()
	{
		Assert.assertTrue("promote TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("promote TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("pr TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("p TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("pmte TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("pmte TEAM PLAYER dksjafl ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("pmte TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminPromoteExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote one protocos");
		//ASSERT
		Assert.assertEquals("You promoted protocos", fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote one Lonely");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerHeverPlayed()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote one newbie");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerNotOnTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote one mastermind");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("mastermind"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminPromoteExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "promote three protocos");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
