package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserChatTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamUserChat()
	{
		Assert.assertTrue("chat".matches(new TeamUserChat().getPattern()));
		Assert.assertTrue("chat ".matches(new TeamUserChat().getPattern()));
		Assert.assertTrue("chat ONOFF".matches(new TeamUserChat().getPattern()));
		Assert.assertTrue("ch ".matches(new TeamUserChat().getPattern()));
		Assert.assertFalse("c".matches(new TeamUserChat().getPattern()));
		Assert.assertFalse("ch daj;nme rjkn".matches(new TeamUserChat().getPattern()));
		Assert.assertTrue(new TeamUserChat().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserChat().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserChatExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserChat();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "chat".split(" ")));
		//ASSERT
		Assert.assertEquals("You are now only chatting with your team", fakePlayerSender.getLastMessage());
		Assert.assertTrue(Configuration.chatStatus.contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserChatExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserChat();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "chat".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(Configuration.chatStatus.contains("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
