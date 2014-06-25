package me.protocos.xteam.command.teamuser;

import org.junit.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserMessageTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserMessage(teamPlugin);
	}

	@Test
	public void ShouldBeTeamUserMessage()
	{
		Assert.assertTrue("message example test message".matches(fakeCommand.getPattern()));
		Assert.assertTrue("message example test message ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("msg example test message".matches(fakeCommand.getPattern()));
		Assert.assertTrue("msg example test message ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tell example test message".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tell example test message ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("m example test message ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("message".matches(fakeCommand.getPattern()));
		Assert.assertFalse("msg ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserMsgExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "msg hello team".split(" ")));
		//ASSERT
		Assert.assertEquals("[protocos] hello team", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserMsgExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "msg hello team".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
