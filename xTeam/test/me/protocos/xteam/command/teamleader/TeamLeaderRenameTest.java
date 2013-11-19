package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderRenameTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamLeaderRename()
	{
		Assert.assertTrue("rename TEAM".matches(new TeamLeaderRename().getPattern()));
		Assert.assertTrue("rename TEAM ".matches(new TeamLeaderRename().getPattern()));
		Assert.assertTrue("rn TEAM".matches(new TeamLeaderRename().getPattern()));
		Assert.assertTrue("ren TEAM".matches(new TeamLeaderRename().getPattern()));
		Assert.assertTrue("rename TEAM ".matches(new TeamLeaderRename().getPattern()));
		Assert.assertTrue("rnm TEAM ".matches(new TeamLeaderRename().getPattern()));
		Assert.assertFalse("rnm TEAM sdfhkabkl".matches(new TeamLeaderRename().getPattern()));
		Assert.assertTrue(new TeamLeaderRename().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamLeaderRename().getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminRenameExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename name".split(" ")));
		//ASSERT
		Assert.assertEquals("You renamed the team to name", fakePlayerSender.getLastMessage());
		Assert.assertEquals("name", xTeam.getInstance().getTeamManager().getTeam("name").getName());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRenameExecuteAlreadyExists()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename two".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameConflictsWithNameException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.getInstance().getTeamManager().getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRenameExecuteNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename ÃºÃ".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.getInstance().getTeamManager().getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRenameExecutenNmeTooLong()
	{
		//ASSEMBLE
		Configuration.TEAM_TAG_LENGTH = 10;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename nameiswaytoolong".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.getInstance().getTeamManager().getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRenameExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename name".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.getInstance().getTeamManager().getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminRenameExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderRename();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "rename name".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("ONE", xTeam.getInstance().getTeamManager().getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
		Configuration.TEAM_TAG_LENGTH = 0;
	}
}
