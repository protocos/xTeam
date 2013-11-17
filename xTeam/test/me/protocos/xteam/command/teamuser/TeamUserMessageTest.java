package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserMessageTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamUserMessage()
	{
		Assert.assertTrue("message example test message".matches(new TeamUserMessage().getPattern()));
		Assert.assertTrue("message example test message ".matches(new TeamUserMessage().getPattern()));
		Assert.assertTrue("msg example test message".matches(new TeamUserMessage().getPattern()));
		Assert.assertTrue("msg example test message ".matches(new TeamUserMessage().getPattern()));
		Assert.assertTrue("tell example test message".matches(new TeamUserMessage().getPattern()));
		Assert.assertTrue("tell example test message ".matches(new TeamUserMessage().getPattern()));
		Assert.assertTrue("m example test message ".matches(new TeamUserMessage().getPattern()));
		Assert.assertFalse("message".matches(new TeamUserMessage().getPattern()));
		Assert.assertFalse("msg ".matches(new TeamUserMessage().getPattern()));
		Assert.assertTrue(new TeamUserMessage().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserMessage().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserMsgExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserMessage();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team msg hello team"));
		//ASSERT
		Assert.assertEquals("[protocos] hello team", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserMsgExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserMessage();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team msg hello team"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
