package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderRenameTest
{
	private TeamPlugin teamPlugin;
	private TeamLeaderCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamLeaderRename(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamLeaderRename()
	{
		Assert.assertTrue("rename TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rename TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rn TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ren TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rename TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rnm TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("rnm TEAM sdfhkabkl".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rename name");
		//ASSERT
		Assert.assertEquals("You renamed the team to name", fakePlayerSender.getLastMessages());
		Assert.assertEquals("name", teamCoordinator.getTeam("name").getName());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecuteAlreadyExists()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rename two");
		//ASSERT
		Assert.assertEquals((new TeamNameAlreadyInUseException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecuteNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rename ��Eåm");
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecutenNmeTooLong()
	{
		//ASSEMBLE
		Configuration.TEAM_NAME_LENGTH = 10;
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rename nameiswaytoolong");
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rename name");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamLeaderRenameExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rename name");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("ONE", teamCoordinator.getTeam("one").getName());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.ALPHA_NUM = false;
		Configuration.TEAM_NAME_LENGTH = 0;
	}
}
