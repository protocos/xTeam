package me.protocos.xteam.command.serveradmin.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.command.serveradmin.AdminSetHeadquarters;
import me.protocos.xteam.core.TeamHeadquarters;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
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
		TeamHeadquarters hq = new TeamHeadquarters(fakePlayerSender.getLocation());
		BaseServerAdminCommand fakeCommand = new AdminSetHeadquarters(fakePlayerSender, "sethq two");
		//ACT 
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You set the team headquarters for team two", fakePlayerSender.getLastMessage());
		Assert.assertEquals(hq, xTeam.tm.getTeam("two").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminSetHQExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		BaseServerAdminCommand fakeCommand = new AdminSetHeadquarters(fakePlayerSender, "sethq three");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
