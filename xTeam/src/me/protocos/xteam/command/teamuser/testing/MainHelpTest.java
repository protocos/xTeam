package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamuser.MainHelp;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainHelpTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamUserMainHelpExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new MainHelp(fakePlayerSender, "", "/team");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("------------------ [xTeam vCURRENT Help] ------------------\n" +
				"xTeam is a team-based PvP plugin that allows for creating and joining teams, setting a headquarters and various other features. It is meant for use on hardcore PvP servers where land is not protected!\n" +
				"Type '/team help [Page Number]' to get started\n" +
				"/team [command] = command for TEAM PLAYERS\n" +
				"/team [command] = command for TEAM ADMINS\n" +
				"/team [command] = command for TEAM LEADERS\n" +
				"Report BUGS to http://dev.bukkit.org/server-mods/xteam/", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
