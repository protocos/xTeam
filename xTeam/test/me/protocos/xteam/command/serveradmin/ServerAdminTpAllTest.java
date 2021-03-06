package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminTpAllTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminTpAll(teamPlugin);
	}

	@Test
	public void ShouldBeServerAdminTpAll()
	{
		Assert.assertTrue("tpall TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tpall TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tpa TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tpa TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("t TEAM".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tpall".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tpall ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminTpAllExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tpall one");
		//ASSERT
		Assert.assertEquals("Players have been teleported", fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminTpAllExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tpall three");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
