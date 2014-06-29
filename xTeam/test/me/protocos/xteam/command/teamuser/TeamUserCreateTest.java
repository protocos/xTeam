package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakePlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserCreateTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserCreate(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamUserCreate()
	{
		Assert.assertTrue("create TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("create TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("cr TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("creat TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("c TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("c ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserCreateExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "create newteam".split(" ")));
		//ASSERT
		Assert.assertEquals("You created newteam", fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.containsTeam("newteam"));
		Assert.assertTrue(teamCoordinator.getTeam("newteam").getPlayers().contains("Lonely"));
		Assert.assertFalse(teamCoordinator.getTeam("newteam").isAdmin("Lonely"));
		Assert.assertTrue(teamCoordinator.getTeam("newteam").getLeader().equals("Lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserCreateExecuteCapitalLetters()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "create NEW".split(" ")));
		//ASSERT
		Assert.assertEquals("You created NEW", fakePlayerSender.getLastMessages());
		Assert.assertEquals("NEW", teamCoordinator.getTeam("NEW").getName());
		Assert.assertTrue(teamCoordinator.containsTeam("NEW"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserCreateExecuteNameTooLong()
	{
		//ASSEMBLE
		Configuration.TEAM_NAME_LENGTH = 10;
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "create newteamiswaytoolong".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.containsTeam("newteamiswaytoolong"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserCreateExecuteOnlyDefault()
	{
		//ASSEMBLE
		Configuration.DEFAULT_TEAM_ONLY = true;
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "create newteam".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamOnlyJoinDefaultException(teamCoordinator)).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.containsTeam("newteam"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserCreateExecutePlayerHasTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "create newteam".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.containsTeam("newteam"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserCreateExecuteTeamAlreadyExists()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "create one".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameAlreadyInUseException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertTrue(teamCoordinator.containsTeam("one"));
		Assert.assertFalse(teamCoordinator.getTeam("one").containsPlayer("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserCreateExecuteTeamCreatedRecently()
	{
		//ASSEMBLE
		Configuration.CREATE_INTERVAL = 1;
		Configuration.lastCreated.put("Lonely", System.currentTimeMillis());
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "create newteam".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamCreatedRecentlyException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.containsTeam("newteam"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserCreateExecuteTeamNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "create ��Eåm".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(teamCoordinator.containsTeam("��Eåm"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		Configuration.DEFAULT_TEAM_ONLY = false;
		Configuration.TEAM_NAME_LENGTH = 0;
		Configuration.CREATE_INTERVAL = 0;
		Configuration.ALPHA_NUM = false;
	}
}
