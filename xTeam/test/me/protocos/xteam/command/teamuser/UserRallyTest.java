package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserRallyTest
{
	Team team;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		team = xTeam.getInstance().getTeamManager().getTeam("one");
	}

	@Test
	public void ShouldBeTeamUserRallyExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location rallyLocation = team.getHeadquarters().getLocation();
		team.setRally(rallyLocation);
		UserCommand fakeCommand = new UserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rally"));
		//ASSERT
		Assert.assertEquals("You've been teleported to the rally point", fakePlayerSender.getLastMessage());
		Assert.assertEquals(rallyLocation, fakePlayerSender.getLocation());
		Assert.assertTrue(team.hasRally());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteNoRally()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", new FakeLocation());
		UserCommand fakeCommand = new UserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rally"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotHaveRallyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		team.setRally(team.getHeadquarters().getLocation());
		fakePlayerSender.setNoDamageTicks(1);
		UserCommand fakeCommand = new UserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rally"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		team.setRally(team.getHeadquarters().getLocation());
		UserCommand fakeCommand = new UserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rally"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		xTeam.getInstance().getPlayerManager().getPlayer("protocos").setLastAttacked(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		xTeam.getInstance().getTeamManager().getTeam("one").setRally(new FakeLocation());
		UserCommand fakeCommand = new UserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rally"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		TeleportScheduler.getInstance().setCurrentTask(teamPlayer, 0);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		xTeam.getInstance().getTeamManager().getTeam("one").setRally(new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		UserCommand fakeCommand = new UserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rally"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserRallyExecuteAlreadyRallied()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		Location rallyLocation = team.getHeadquarters().getLocation();
		team.setRally(rallyLocation);
		TeleportScheduler.getInstance().useRally(teamPlayer);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		UserCommand fakeCommand = new UserRally();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team rally"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerAlreadyUsedRallyException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		TeleportScheduler.getInstance().clearTeamRally(team);
	}
}
