package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.fakeobjects.FakePlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserMainHelpTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserMainHelp(teamPlugin);
	}

	@Test
	public void ShouldBeTeamUserMainHelp()
	{
		Assert.assertTrue("".matches(fakeCommand.getPattern()));
		Assert.assertTrue(" ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("help".matches(fakeCommand.getPattern()));
		Assert.assertTrue("help ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("???".matches(fakeCommand.getPattern()));
		Assert.assertTrue("? ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserMainHelpExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "".split(" ")));
		//ASSERT
		Assert.assertEquals("------------------ [xTeam vTEST Help] ------------------\n" +
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
