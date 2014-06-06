package me.protocos.xteam.command.serveradmin;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamNameConflictsWithNameException;
import me.protocos.xteam.exception.TeamNameNotAlphaException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminTagTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminTag(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeServerAdminTag()
	{
		Assert.assertTrue("tag TEAM TAG".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tag TEAM TAG ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tag TEAM TAG ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("t TEAM TAG ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ta TEAM TAG".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tg TEAM TAG ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tg TEAM TAG sdfhkabkl".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminTagExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag one tag".split(" ")));
		//ASSERT
		Assert.assertEquals("The team tag has been set to tag", fakePlayerSender.getLastMessage());
		Assert.assertEquals("tag", teamCoordinator.getTeam("one").getTag());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminTagExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag one two".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameConflictsWithNameException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminTagExecuteTeamNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag one †Eåm".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminTagExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "tag three tag".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
	}
}
