package me.protocos.xteam.command.teamleader;

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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderTagTest
{
	private TeamPlugin teamPlugin;
	private TeamLeaderCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamLeaderTag(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamLeaderTag()
	{
		Assert.assertTrue("tag TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tag TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tag TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("t TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ta TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tg TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tg TEAM sdfhkabkl".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTagExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag tag".split(" ")));
		//ASSERT
		Assert.assertEquals("The team tag has been set to tag", fakePlayerSender.getLastMessage());
		Assert.assertEquals("tag", teamCoordinator.getTeam("one").getTag());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteAlreadyExists()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag two".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameAlreadyInUseException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag € EÃ¥m".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecutenNmeTooLong()
	{
		//ASSEMBLE
		Configuration.TEAM_NAME_LENGTH = 10;
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag tagiswaytoolong".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag tag".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag tag".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}