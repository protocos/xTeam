package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamHqSetRecentlyException;
import me.protocos.xteam.exception.TeamPlayerDyingException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotAdminException;
import me.protocos.xteam.model.Headquarters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamAdminSetHeadquartersTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamAdminSetHeadquarters()
	{
		Assert.assertTrue("sethq".matches(new TeamAdminSetHeadquarters().getPattern()));
		Assert.assertTrue("sethq ".matches(new TeamAdminSetHeadquarters().getPattern()));
		Assert.assertTrue("shq".matches(new TeamAdminSetHeadquarters().getPattern()));
		Assert.assertFalse("set".matches(new TeamAdminSetHeadquarters().getPattern()));
		Assert.assertFalse("sethq dsaf ".matches(new TeamAdminSetHeadquarters().getPattern()));
		Assert.assertTrue(new TeamAdminSetHeadquarters().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamAdminSetHeadquarters().getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Headquarters newHQ = new Headquarters(fakePlayerSender.getLocation());
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals("You set the team headquarters", fakePlayerSender.getLastMessage());
		Assert.assertEquals(newHQ, XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Headquarters oldHQ = XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters();
		fakePlayerSender.setNoDamageTicks(1);
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Headquarters oldHQ = XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters();
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		Headquarters oldHQ = XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters();
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecuteRecentlySet()
	{
		//ASSEMBLE
		Configuration.HQ_INTERVAL = 1;
		XTeam.getInstance().getTeamManager().getTeam("one").setTimeLastSet(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Headquarters oldHQ = XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters();
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamHqSetRecentlyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		InviteHandler.clear();
		Configuration.HQ_INTERVAL = 0;
	}
}
