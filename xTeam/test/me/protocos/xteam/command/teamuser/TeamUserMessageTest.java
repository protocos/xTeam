package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
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
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "msg hello team");
		//ASSERT
		Assert.assertEquals("[protocos] hello team", fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserMsgExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "msg hello team");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
