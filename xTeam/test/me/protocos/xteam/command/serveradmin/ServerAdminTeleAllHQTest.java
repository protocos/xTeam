package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.fakeobjects.FakePlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminTeleAllHQTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminTeleAllHQ(teamPlugin);
	}

	@Test
	public void ShouldBeServerAdminTeleAllHQ()
	{
		Assert.assertTrue("teleallhq".matches(fakeCommand.getPattern()));
		Assert.assertTrue("teleallhq ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tahq".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tele ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("teleallhq fdsakjn".matches(fakeCommand.getPattern()));
		Assert.assertFalse("teleallhq awkejnr ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminTeleAllHQExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "teleallhq".split(" ")));
		//ASSERT
		Assert.assertEquals("You have been teleported to the team headquarters\n" +
				"Players teleported", fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
