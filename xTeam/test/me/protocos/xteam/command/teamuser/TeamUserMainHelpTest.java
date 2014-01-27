package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserMainHelpTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamUserMainHelp()
	{
		Assert.assertTrue("".matches(new TeamUserMainHelp().getPattern()));
		Assert.assertTrue(" ".matches(new TeamUserMainHelp().getPattern()));
		Assert.assertTrue("help".matches(new TeamUserMainHelp().getPattern()));
		Assert.assertTrue("help ".matches(new TeamUserMainHelp().getPattern()));
		Assert.assertTrue("???".matches(new TeamUserMainHelp().getPattern()));
		Assert.assertTrue("? ".matches(new TeamUserMainHelp().getPattern()));
		Assert.assertTrue(new TeamUserMainHelp().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserMainHelp().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserMainHelpExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserMainHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "".split(" ")));
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
