package me.protocos.xteam.command.serveradmin;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminChatSpyTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminChatSpy(teamPlugin);
	}

	@Test
	public void ShouldBeServerAdminChatSpy()
	{
		Assert.assertTrue("chatspy".matches(fakeCommand.getPattern()));
		Assert.assertTrue("chatspy ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("chspy".matches(fakeCommand.getPattern()));
		Assert.assertTrue("cspy ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("c".matches(fakeCommand.getPattern()));
		Assert.assertFalse("cspy dasflk;j".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminChatSpyExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "chatspy".split(" ")));
		//ASSERT
		Assert.assertEquals("You are now spying on team chat", fakePlayerSender.getLastMessage());
		Assert.assertTrue(Configuration.spies.contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminChatSpyExecuteDisable()
	{
		//ASSEMBLE
		Configuration.spies.add("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "chatspy".split(" ")));
		//ASSERT
		Assert.assertEquals("You are no longer spying on team chat", fakePlayerSender.getLastMessage());
		Assert.assertFalse(Configuration.spies.contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.spies.clear();
	}
}
