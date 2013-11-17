package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.exception.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderTagTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamLeaderTag()
	{
		Assert.assertTrue("tag TEAM".matches(new TeamLeaderTag().getPattern()));
		Assert.assertTrue("tag TEAM ".matches(new TeamLeaderTag().getPattern()));
		Assert.assertTrue("tag TEAM ".matches(new TeamLeaderTag().getPattern()));
		Assert.assertTrue("t TEAM ".matches(new TeamLeaderTag().getPattern()));
		Assert.assertTrue("ta TEAM".matches(new TeamLeaderTag().getPattern()));
		Assert.assertTrue("tg TEAM ".matches(new TeamLeaderTag().getPattern()));
		Assert.assertFalse("tg TEAM sdfhkabkl".matches(new TeamLeaderTag().getPattern()));
		Assert.assertTrue(new TeamLeaderTag().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamLeaderTag().getPattern()));
	}

	@Test
	public void ShouldBeTagExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tag tag"));
		//ASSERT
		Assert.assertEquals("The team tag has been set to tag", fakePlayerSender.getLastMessage());
		Assert.assertEquals("tag", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteAlreadyExists()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tag two"));
		//ASSERT
		Assert.assertEquals((new TeamNameConflictsWithNameException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tag ÃºÃ"));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecutenNmeTooLong()
	{
		//ASSEMBLE
		Configuration.TEAM_TAG_LENGTH = 10;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tag tagiswaytoolong"));
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tag tag"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamLeaderCommand fakeCommand = new TeamLeaderTag();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team tag tag"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.getInstance().getTeamManager().getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}