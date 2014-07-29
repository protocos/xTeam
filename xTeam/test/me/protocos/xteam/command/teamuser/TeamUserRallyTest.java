package me.protocos.xteam.command.teamuser;

import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommandUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserRallyTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private BukkitUtil bukkitUtil;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private TeleportScheduler teleportScheduler;
	private ITeam team;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		bukkitUtil = teamPlugin.getBukkitUtil();
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
		teleportScheduler = teamPlugin.getTeleportScheduler();
		team = teamCoordinator.getTeam("one");
		fakeCommand = new TeamUserRally(teamPlugin);
	}

	@Test
	public void ShouldBeTeamUserRally()
	{
		Assert.assertTrue("rally".matches(fakeCommand.getPattern()));
		Assert.assertTrue("rally ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ral".matches(fakeCommand.getPattern()));
		Assert.assertFalse("r ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("rally HOME ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserRallyExecute()
	{
		//ASSEMBLE
		Location beforeLocation = new FakeLocation(bukkitUtil.getWorld("world")).toLocation();
		FakePlayer fakePlayerSender = new FakePlayer.Builder().name("protocos").location(beforeLocation).build();
		Location rallyLocation = team.getHeadquarters().getLocation();
		team.setRally(rallyLocation);
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rally");
		//ASSERT
		Assert.assertEquals("You have been teleported to the rally point", fakePlayerSender.getLastMessages());
		Assert.assertEquals(rallyLocation, fakePlayerSender.getLocation());
		Assert.assertEquals(beforeLocation, playerFactory.getPlayer("protocos").getReturnLocation());
		Assert.assertTrue(team.hasRally());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteNoRally()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("mastermind");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rally");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotHaveRallyException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		Location before = fakePlayerSender.getLocation();
		team.setRally(team.getHeadquarters().getLocation());
		fakePlayerSender.setNoDamageTicks(1);
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rally");
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		team.setRally(team.getHeadquarters().getLocation());
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rally");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		playerFactory.getPlayer("protocos").setLastAttacked(System.currentTimeMillis());
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		Location before = fakePlayerSender.getLocation();
		teamCoordinator.getTeam("one").setRally(new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rally");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(playerFactory.getPlayer("kmlanglois"), TeamPlayer.class);
		teleportScheduler.setCurrentTask(teamPlayer, 0);
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		teamCoordinator.getTeam("one").setRally(new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rally");
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteAlreadyRallied()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(playerFactory.getPlayer("kmlanglois"), TeamPlayer.class);
		Location rallyLocation = team.getHeadquarters().getLocation();
		team.setRally(rallyLocation);
		teleportScheduler.setRallyUsedFor(teamPlayer);
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		Location before = fakePlayerSender.getLocation();
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "rally");
		//ASSERT
		Assert.assertEquals((new TeamPlayerAlreadyUsedRallyException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}

}
