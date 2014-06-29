package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamHqSetRecentlyException;
import me.protocos.xteam.exception.TeamPlayerDyingException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotAdminException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.IHeadquarters;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamAdminSetHeadquartersTest
{
	private TeamPlugin teamPlugin;
	private TeamAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamAdminSetHeadquarters(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamAdminSetHeadquarters()
	{
		Assert.assertTrue("sethq".matches(fakeCommand.getPattern()));
		Assert.assertTrue("sethq ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("shq".matches(fakeCommand.getPattern()));
		Assert.assertFalse("set".matches(fakeCommand.getPattern()));
		Assert.assertFalse("sethq dsaf ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		Headquarters newHQ = new Headquarters(teamPlugin, fakePlayerSender.getLocation());
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "sethq");
		//ASSERT
		Assert.assertEquals("You set the team headquarters", fakePlayerSender.getLastMessages());
		Assert.assertEquals(newHQ, teamCoordinator.getTeam("one").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		IHeadquarters oldHQ = teamCoordinator.getTeam("one").getHeadquarters();
		fakePlayerSender.setNoDamageTicks(1);
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "sethq");
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(oldHQ, teamCoordinator.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		IHeadquarters oldHQ = teamCoordinator.getTeam("one").getHeadquarters();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "sethq");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(oldHQ, teamCoordinator.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		IHeadquarters oldHQ = teamCoordinator.getTeam("one").getHeadquarters();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "sethq");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(oldHQ, teamCoordinator.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecuteRecentlySet()
	{
		//ASSEMBLE
		Configuration.HQ_INTERVAL = 1;
		teamCoordinator.getTeam("one").setTimeHeadquartersLastSet(System.currentTimeMillis());
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		IHeadquarters oldHQ = teamCoordinator.getTeam("one").getHeadquarters();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "sethq");
		//ASSERT
		Assert.assertEquals((new TeamHqSetRecentlyException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(oldHQ, teamCoordinator.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.HQ_INTERVAL = 0;
	}
}
