package me.protocos.xteam.command.serveradmin.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.serveradmin.AdminPromote;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNeverPlayedException;
import me.protocos.xteam.core.exception.TeamPlayerNotOnTeamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminPromoteTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeServerAdminPromoteExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team promote one protocos"));
		//ASSERT
		Assert.assertEquals("You promoted protocos", fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team promote one Lonely"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerHeverPlayed()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team promote one newbie"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerNotOnTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team promote one mastermind"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("mastermind"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminPromoteExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team promote three protocos"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
