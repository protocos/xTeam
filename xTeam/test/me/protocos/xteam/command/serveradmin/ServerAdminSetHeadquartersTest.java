package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.Headquarters;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminSetHeadquartersTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminSetHeadquarters()
	{
		Assert.assertTrue("sethq TEAM".matches(new ServerAdminSetHeadquarters().getPattern()));
		Assert.assertTrue("sethq TEAM ".matches(new ServerAdminSetHeadquarters().getPattern()));
		Assert.assertTrue("setheadquarters TEAM".matches(new ServerAdminSetHeadquarters().getPattern()));
		Assert.assertFalse("set TEAM".matches(new ServerAdminSetHeadquarters().getPattern()));
		Assert.assertFalse("set TEAM ".matches(new ServerAdminSetHeadquarters().getPattern()));
		Assert.assertFalse("sethq TEAM dfsjkal".matches(new ServerAdminSetHeadquarters().getPattern()));
		Assert.assertTrue(new ServerAdminSetHeadquarters().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminSetHeadquarters().getPattern()));
	}

	@Test
	public void ShouldBeServerAdminSetHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Headquarters newHQ = new Headquarters(fakePlayerSender.getLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSetHeadquarters();
		//ACT 
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "sethq two".split(" ")));
		//ASSERT
		Assert.assertEquals("You set the team headquarters for team two", fakePlayerSender.getLastMessage());
		Assert.assertEquals(newHQ, xTeam.getInstance().getTeamManager().getTeam("two").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetHQExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSetHeadquarters();
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
