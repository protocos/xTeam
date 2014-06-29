package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotAdminException;
import me.protocos.xteam.exception.TeamPlayerNotTeammateException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamAdminPromoteTest
{
	private TeamPlugin teamPlugin;
	private TeamAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamAdminPromote(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamAdminPromote()
	{
		Assert.assertTrue("promote PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("promote PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("p PLAYER".matches(fakeCommand.getPattern()));
		Assert.assertTrue("pmte PLAYER ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("promote PLAYER dfsagf".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("You promoted protocos", fakePlayerSender.getLastMessage());
		Assert.assertTrue(teamCoordinator.getTeam("one").isAdmin("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote kmlanglois".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNotTeammate()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.from("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(teamCoordinator.getTeam("one").isAdmin("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
