package me.protocos.xteam.command.serveradmin;

import org.junit.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNeverPlayedException;
import me.protocos.xteam.exception.TeamPlayerNotOnTeamException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminPromoteTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminPromote(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeServerAdminPromote()
	{
		Assert.assertTrue("promote TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("promote TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("pr TEAM PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("p TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("pmte TEAM PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("pmte TEAM PLAYER dksjafl ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("pmte TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminPromoteExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote one protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("You promoted protocos", fakePlayerSender.getLastMessage());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote one Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerHeverPlayed()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote one newbie".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminPromoteExecutePlayerNotOnTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote one mastermind".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("mastermind"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminPromoteExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote three protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
