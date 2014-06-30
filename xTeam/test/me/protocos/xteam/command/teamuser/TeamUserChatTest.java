package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserChatTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserChat(teamPlugin);
	}

	@Test
	public void ShouldBeTeamUserChat()
	{
		Assert.assertTrue("chat".matches(fakeCommand.getPattern()));
		Assert.assertTrue("chat ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("chat ONOFF".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ch ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("c".matches(fakeCommand.getPattern()));
		Assert.assertFalse("ch daj;nme rjkn".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserChatExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "chat");
		//ASSERT
		Assert.assertEquals("You are now only chatting with your team", fakePlayerSender.getLastMessages());
		Assert.assertTrue(Configuration.chatStatus.contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserChatExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "chat");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(Configuration.chatStatus.contains("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
