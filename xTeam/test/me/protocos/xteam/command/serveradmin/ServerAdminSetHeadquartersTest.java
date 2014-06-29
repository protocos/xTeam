package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminSetHeadquartersTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminSetHeadquarters(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
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
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		Headquarters newHQ = new Headquarters(teamPlugin, fakePlayerSender.getLocation());
		//ACT 
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "sethq two");
		//ASSERT
		Assert.assertEquals("You set the team headquarters for team two", fakePlayerSender.getLastMessages());
		Assert.assertEquals(newHQ, teamCoordinator.getTeam("two").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetHQExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "sethq three");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
