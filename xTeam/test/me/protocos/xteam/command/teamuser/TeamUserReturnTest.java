package me.protocos.xteam.command.teamuser;

import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserReturnTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private TeleportScheduler teleportScheduler;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
		teleportScheduler = teamPlugin.getTeleportScheduler();
		fakeCommand = new TeamUserReturn(teamPlugin);
	}

	@Test
	public void ShouldBeTeamUserReturn()
	{
		Assert.assertTrue("return".matches(new TeamUserReturn(teamPlugin).getPattern()));
		Assert.assertTrue("return ".matches(new TeamUserReturn(teamPlugin).getPattern()));
		Assert.assertTrue("ret".matches(new TeamUserReturn(teamPlugin).getPattern()));
		Assert.assertTrue("r ".matches(new TeamUserReturn(teamPlugin).getPattern()));
		Assert.assertFalse("return HOME ".matches(new TeamUserReturn(teamPlugin).getPattern()));
		Assert.assertTrue(new TeamUserReturn(teamPlugin).getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserReturn(teamPlugin).getPattern()));
	}

	@Test
	public void ShouldBeTeamUserReturnExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		TeamPlayer teamPlayer = CommonUtil.assignFromType(playerFactory.getPlayer("protocos"), TeamPlayer.class);
		Location returnLocation = new FakeLocation(teamCoordinator.getTeam("one").getHeadquarters().getLocation()).toLocation();
		playerFactory.getPlayer("protocos").setReturnLocation(returnLocation);
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "return");
		//ASSERT
		Assert.assertEquals("You have been teleported to your return location", fakePlayerSender.getLastMessages());
		Assert.assertEquals(returnLocation, fakePlayerSender.getLocation());
		Assert.assertFalse(teamPlayer.hasReturnLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecuteNoReturn()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("mastermind");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "return");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoReturnException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		Location before = fakePlayerSender.getLocation();
		playerFactory.getPlayer("protocos").setReturnLocation(new FakeLocation());
		fakePlayerSender.setNoDamageTicks(1);
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "return");
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		playerFactory.getPlayer("protocos").setReturnLocation(new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "return");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecuteRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		playerFactory.getPlayer("protocos").setLastAttacked(System.currentTimeMillis());
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		Location before = fakePlayerSender.getLocation();
		playerFactory.getPlayer("protocos").setReturnLocation(new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "return");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecuteRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(playerFactory.getPlayer("kmlanglois"), TeamPlayer.class);
		teleportScheduler.setCurrentTask(teamPlayer, 0);
		teamPlayer.setReturnLocation(new FakeLocation());
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "return");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		//		Configuration.returnLocations.clear();
	}
}
