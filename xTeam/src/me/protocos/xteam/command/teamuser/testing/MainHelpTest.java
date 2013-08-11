package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.teamuser.UserMainHelp;
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
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserMainHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team"));
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
