package me.protocos.xteam.command.teamadmin;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamHqSetRecentlyException;
import me.protocos.xteam.exception.TeamPlayerDyingException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotAdminException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.IHeadquarters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamAdminSetHeadquartersTest
{
	private TeamPlugin teamPlugin;
	private TeamAdminCommand fakeCommand;
	private ITeamManager teamManager;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamAdminSetHeadquarters(teamPlugin);
		teamManager = teamPlugin.getTeamManager();
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
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		Headquarters newHQ = new Headquarters(teamPlugin, fakePlayerSender.getLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals("You set the team headquarters", fakePlayerSender.getLastMessage());
		Assert.assertEquals(newHQ, teamManager.getTeam("one").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		IHeadquarters oldHQ = teamManager.getTeam("one").getHeadquarters();
		fakePlayerSender.setNoDamageTicks(1);
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, teamManager.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		IHeadquarters oldHQ = teamManager.getTeam("one").getHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, teamManager.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		IHeadquarters oldHQ = teamManager.getTeam("one").getHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, teamManager.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecuteRecentlySet()
	{
		//ASSEMBLE
		Configuration.HQ_INTERVAL = 1;
		teamManager.getTeam("one").setTimeHeadquartersLastSet(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		IHeadquarters oldHQ = teamManager.getTeam("one").getHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamHqSetRecentlyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, teamManager.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.HQ_INTERVAL = 0;
	}
}
