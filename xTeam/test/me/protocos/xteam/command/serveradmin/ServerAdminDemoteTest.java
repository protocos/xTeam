package me.protocos.xteam.command.serveradmin;

import org.junit.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminDemoteTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminDemote(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		teamCoordinator.getTeam("one").promote("protocos");
	}

	@Test
	public void ShouldBeServerAdminDemote()
	{
		Assert.assertTrue("demote TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("demote TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("dem TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("dem TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("d TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertFalse("d TEAM".matches(fakeCommand.getPattern()));
		Assert.assertFalse("dem TEAM PLAYER sdfkjahl".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminDemoteExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "demote one protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("You demoted protocos", fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDemoteExecuteDemoteLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "demote one kmlanglois".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderDemoteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDemoteExecuteIncorrectTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "demote one mastermind".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDemoteExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "demote one Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDemoteExecutePlayerHasNotPlayed()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "demote one newbie".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDemoteExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").demote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "demote one protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminDemoteExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "demote three protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
