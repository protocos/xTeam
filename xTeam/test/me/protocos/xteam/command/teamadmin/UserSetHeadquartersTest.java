package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Headquarters;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamHqSetRecentlyException;
import me.protocos.xteam.core.exception.TeamPlayerDyingException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNotAdminException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserSetHeadquartersTest
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
		Headquarters newHQ = new Headquarters(fakePlayerSender.getLocation());
		UserCommand fakeCommand = new UserSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals("You set the team headquarters", fakePlayerSender.getLastMessage());
		Assert.assertEquals(newHQ, xTeam.getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Headquarters oldHQ = xTeam.getTeamManager().getTeam("one").getHeadquarters();
		fakePlayerSender.setNoDamageTicks(1);
		UserCommand fakeCommand = new UserSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Headquarters oldHQ = xTeam.getTeamManager().getTeam("one").getHeadquarters();
		UserCommand fakeCommand = new UserSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		Headquarters oldHQ = xTeam.getTeamManager().getTeam("one").getHeadquarters();
		UserCommand fakeCommand = new UserSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminSetHQExecuteRecentlySet()
	{
		//ASSEMBLE
		Data.HQ_INTERVAL = 1;
		xTeam.getTeamManager().getTeam("one").setTimeLastSet(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Headquarters oldHQ = xTeam.getTeamManager().getTeam("one").getHeadquarters();
		UserCommand fakeCommand = new UserSetHeadquarters();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team sethq"));
		//ASSERT
		Assert.assertEquals((new TeamHqSetRecentlyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(oldHQ, xTeam.getTeamManager().getTeam("one").getHeadquarters());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		InviteHandler.clear();
		Data.HQ_INTERVAL = 0;
	}
}
