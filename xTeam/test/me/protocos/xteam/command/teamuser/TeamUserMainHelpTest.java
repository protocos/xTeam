package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
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
		Assert.assertFalse("help".matches(fakeCommand.getPattern()));
		Assert.assertFalse("help ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("???".matches(fakeCommand.getPattern()));
		Assert.assertFalse("? ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team"));
	}

	@Test
	public void ShouldBeTeamUserMainHelpExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "");
		//ASSERT
		Assert.assertEquals("------------------ [xTeam vTEST Help] ------------------\n" +
				"xTeam is a team-based PvP plugin that allows for creating and joining teams, setting a headquarters and various other features. It is meant for use on hardcore PvP servers where land is not protected!\n" +
				"Type '/team [Page #]' to see commands\n" +
				"Gray command: command for Team User\n" +
				"Yellow command: command for Team Admin\n" +
				"Purple command: command for Team Leader\n" +
				"Report BUGS to http://dev.bukkit.org/server-mods/xteam/", fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
