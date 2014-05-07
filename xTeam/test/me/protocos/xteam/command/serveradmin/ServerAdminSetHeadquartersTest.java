package me.protocos.xteam.command.serveradmin;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.model.Headquarters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminSetHeadquartersTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;
	private ITeamManager teamManager;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminSetHeadquarters(teamPlugin);
		teamManager = teamPlugin.getTeamManager();
	}

	@Test
	public void ShouldBeServerAdminSetHeadquarters()
	{
		Assert.assertTrue("sethq TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("sethq TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("setheadquarters TEAM".matches(fakeCommand.getPattern()));
		Assert.assertFalse("set TEAM".matches(fakeCommand.getPattern()));
		Assert.assertFalse("set TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("sethq TEAM dfsjkal".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminSetHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		Headquarters newHQ = new Headquarters(teamPlugin, fakePlayerSender.getLocation());
		//ACT 
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq two".split(" ")));
		//ASSERT
		Assert.assertEquals("You set the team headquarters for team two", fakePlayerSender.getLastMessage());
		Assert.assertEquals(newHQ, teamManager.getTeam("two").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetHQExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq three".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
