package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Headquarters;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminSetHeadquartersTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeServerAdminSetHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Headquarters newHQ = new Headquarters(fakePlayerSender.getLocation());
		ServerAdminCommand fakeCommand = new AdminSetHeadquarters();
		//ACT 
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq two"));
		//ASSERT
		Assert.assertEquals("You set the team headquarters for team two", fakePlayerSender.getLastMessage());
		Assert.assertEquals(newHQ, xTeam.getTeamManager().getTeam("two").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminSetHQExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq three"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
