package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeTeamPlayer;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserInfoTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private InviteHandler inviteHandler;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserInfo(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
		inviteHandler = teamPlugin.getInviteHandler();
		Configuration.DISPLAY_RELATIVE_COORDINATES = false;
	}

	@Test
	public void ShouldBeTeamUserInfo()
	{
		Assert.assertTrue("info".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info PLAYERTEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info PLAYERTEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info 1".matches(fakeCommand.getPattern()));
		Assert.assertTrue("i".matches(fakeCommand.getPattern()));
		Assert.assertTrue("i ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserInfoExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("one").getInfoFor(new FakeTeamPlayer.Builder().name("protocos").build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute2()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info two");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("two").getInfoFor(new FakeTeamPlayer.Builder().name("kmlanglois").isOnSameTeam(false).build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute3()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info mastermind");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeamByPlayer("mastermind").getInfoFor(new FakeTeamPlayer.Builder().name("protocos").isOnSameTeam(false).build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute4()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info red");
		//ASSERT
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute5()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info strandedhelix");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeamByPlayer("strandedhelix").getInfoFor(new FakeTeamPlayer.Builder().name("protocos").isOnSameTeam(false).build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute6()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("red").addPlayer("Lonely");
		teamCoordinator.getTeam("red").promote("strandedhelix");
		teamCoordinator.getTeam("red").promote("Lonely");
		FakePlayer fakePlayerSender = FakePlayer.get("strandedhelix");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("red").getInfoFor(new FakeTeamPlayer.Builder().name("strandedhelix").build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute7()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").promote("protocos");
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("one").getInfoFor(new FakeTeamPlayer.Builder().name("kmlanglois").build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecuteRelativeLocation()
	{
		//ASSEMBLE
		Configuration.DISPLAY_RELATIVE_COORDINATES = true;
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("one").getInfoFor(new FakeTeamPlayer.Builder().name("protocos").build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
		Configuration.DISPLAY_RELATIVE_COORDINATES = false;
	}

	@Test
	public void ShouldBeTeamUserInfoExecutePendingInvites()
	{
		//ASSEMBLE
		inviteHandler.addInvite(new InviteRequest(playerFactory.getPlayer("kmlanglois"), playerFactory.getPlayer("Lonely"), System.currentTimeMillis()));
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("one").getInfoFor(new FakeTeamPlayer.Builder().name("kmlanglois").build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecutePlayerNotOnTeamUsingTag()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").promote("protocos");
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info REDONE");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("REDONE").getInfoFor(new FakeTeamPlayer.Builder().name("kmlanglois").isOnSameTeam(false).build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecutePlayerOnTeamUsingTag()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").promote("protocos");
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info TeamAwesome");
		//ASSERT
		Assert.assertEquals(MessageUtil.resetFormatting(teamCoordinator.getTeam("TeamAwesome").getInfoFor(new FakeTeamPlayer.Builder().name("kmlanglois").build())), fakePlayerSender.getLastMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "info truck");
		//ASSERT
		Assert.assertEquals((new TeamOrPlayerDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
