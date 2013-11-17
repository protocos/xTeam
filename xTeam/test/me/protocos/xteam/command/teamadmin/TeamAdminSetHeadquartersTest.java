package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamAdminCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.Headquarters;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamHqSetRecentlyException;
import me.protocos.xteam.core.exception.TeamPlayerDyingException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNotAdminException;
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
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals("You set the team headquarters", fakePlayerSender.getLastMessage());
		Assert.assertEquals(newHQ, xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Headquarters oldHQ = xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters();
		fakePlayerSender.setNoDamageTicks(1);
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Headquarters oldHQ = xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters();
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		Headquarters oldHQ = xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters();
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminSetHQExecuteRecentlySet()
	{
		//ASSEMBLE
		Configuration.HQ_INTERVAL = 1;
		xTeam.getInstance().getTeamManager().getTeam("one").setTimeLastSet(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Headquarters oldHQ = xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters();
		TeamAdminCommand fakeCommand = new TeamAdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals((new TeamHqSetRecentlyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		InviteHandler.clear();
		Configuration.HQ_INTERVAL = 0;
	}
}
