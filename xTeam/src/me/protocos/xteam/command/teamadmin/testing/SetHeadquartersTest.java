package me.protocos.xteam.command.teamadmin.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamadmin.SetHeadquarters;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamHeadquarters;
import me.protocos.xteam.exception.TeamHqSetRecentlyException;
import me.protocos.xteam.exception.TeamPlayerDyingException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotAdminException;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SetHeadquartersTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamHeadquarters newHQ = new TeamHeadquarters(fakePlayerSender.getLocation());
		BaseUserCommand fakeCommand = new SetHeadquarters(fakePlayerSender, "sethq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You set the team headquarters", fakePlayerSender.getLastMessage());
		Assert.assertEquals(newHQ, xTeam.tm.getTeam("one").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamHeadquarters oldHQ = xTeam.tm.getTeam("one").getHeadquarters();
		fakePlayerSender.setNoDamageTicks(1);
		BaseUserCommand fakeCommand = new SetHeadquarters(fakePlayerSender, "sethq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.tm.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamHeadquarters oldHQ = xTeam.tm.getTeam("one").getHeadquarters();
		BaseUserCommand fakeCommand = new SetHeadquarters(fakePlayerSender, "sethq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.tm.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		TeamHeadquarters oldHQ = xTeam.tm.getTeam("one").getHeadquarters();
		BaseUserCommand fakeCommand = new SetHeadquarters(fakePlayerSender, "sethq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.tm.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecuteRecentlySet()
	{
		//ASSEMBLE
		Data.HQ_INTERVAL = 1;
		xTeam.tm.getTeam("one").setTimeLastSet(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamHeadquarters oldHQ = xTeam.tm.getTeam("one").getHeadquarters();
		BaseUserCommand fakeCommand = new SetHeadquarters(fakePlayerSender, "sethq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamHqSetRecentlyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.tm.getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.invites.clear();
		Data.HQ_INTERVAL = 0;
	}
}