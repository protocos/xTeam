package me.protocos.xteam.command.teamleader;

import org.junit.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderRenameTest
{
	private TeamPlugin teamPlugin;
	private TeamLeaderCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamLeaderRename(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamLeaderRename()
	{
		Assert.assertTrue("rename TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rename TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rn TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ren TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rename TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rnm TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("rnm TEAM sdfhkabkl".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename name".split(" ")));
		//ASSERT
		Assert.assertEquals("You renamed the team to name", fakePlayerSender.getLastMessage());
		Assert.assertEquals("name", teamCoordinator.getTeam("name").getName());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecuteAlreadyExists()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename two".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameAlreadyInUseException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecuteNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename € EÃ¥m".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecutenNmeTooLong()
	{
		//ASSEMBLE
		Configuration.TEAM_NAME_LENGTH = 10;
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename nameiswaytoolong".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename name".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename name".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
		Configuration.TEAM_NAME_LENGTH = 0;
	}
}
