package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.core.exception.TeamPlayerMaxException;
import me.protocos.xteam.core.exception.TeamPlayerNeverPlayedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminSetTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminSet()
	{
		Assert.assertTrue("set PLAYER TEAM".matches(new ServerAdminSet().getPattern()));
		Assert.assertTrue("set PLAYER TEAM ".matches(new ServerAdminSet().getPattern()));
		Assert.assertTrue("s PLAYER TEAM".matches(new ServerAdminSet().getPattern()));
		Assert.assertTrue("st PLAYER TEAM ".matches(new ServerAdminSet().getPattern()));
		Assert.assertTrue("s PLAYER TEAM".matches(new ServerAdminSet().getPattern()));
		Assert.assertTrue("st PLAYER TEAM ".matches(new ServerAdminSet().getPattern()));
		Assert.assertFalse("s".matches(new ServerAdminSet().getPattern()));
		Assert.assertFalse("st PLAYER TEAM jadsldkn ".matches(new ServerAdminSet().getPattern()));
		Assert.assertTrue(new ServerAdminSet().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminSet().getPattern()));
	}

	@Test
	public void ShouldBeConsoleSetMaxPlayers()
	{
		//ASSEMBLE
		Configuration.MAX_PLAYERS = 2;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team set Lonely one"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerMaxException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team set Lonely two"));
		//ASSERT
		Assert.assertEquals("Lonely has been added to two", fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("two").containsPlayer("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetExecuteCreateTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team set Lonely three"));
		//ASSERT
		Assert.assertEquals("three has been created\n" +
				"Lonely has been added to three\n", fakePlayerSender.getAllMessages());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().contains("three"));
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("three").containsPlayer("Lonely"));
		Assert.assertEquals(1, xTeam.getInstance().getTeamManager().getTeam("three").size());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetExecuteLastPerson()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").removePlayer("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team set kmlanglois two"));
		//ASSERT
		Assert.assertEquals("kmlanglois has been removed from ONE\n" +
				"ONE has been disbanded\n" +
				"kmlanglois has been added to two\n", fakePlayerSender.getAllMessages());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().contains("one"));
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("two").containsPlayer("kmlanglois"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetExecuteLeaderLeaving()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team set kmlanglois two"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team set newbie one"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminSet();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team set protocos three"));
		//ASSERT
		Assert.assertEquals("protocos has been removed from ONE\n" +
				"three has been created\n" +
				"protocos has been added to three\n", fakePlayerSender.getAllMessages());
		Assert.assertFalse(xTeam.getInstance().getTeamManager().getTeam("one").containsPlayer("protocos"));
		Assert.assertTrue(xTeam.getInstance().getTeamManager().contains("three"));
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeam("three").containsPlayer("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
