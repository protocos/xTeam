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
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserReturnTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamUserReturnExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamPlayer teamPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("protocos"), TeamPlayer.class);
		Location returnLocation = xTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters().getLocation();
		xTeam.getInstance().getPlayerManager().getPlayer("protocos").setReturnLocation(returnLocation);
		UserCommand fakeCommand = new UserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team return"));
		//ASSERT
		Assert.assertEquals("You've been teleported to your return location", fakePlayerSender.getLastMessage());
		Assert.assertEquals(returnLocation, fakePlayerSender.getLocation());
		Assert.assertFalse(teamPlayer.hasReturnLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecuteNoReturn()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", new FakeLocation());
		UserCommand fakeCommand = new UserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team return"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoReturnException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		xTeam.getInstance().getPlayerManager().getPlayer("protocos").setReturnLocation(new FakeLocation());
		fakePlayerSender.setNoDamageTicks(1);
		UserCommand fakeCommand = new UserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team return"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		xTeam.getInstance().getPlayerManager().getPlayer("protocos").setReturnLocation(new FakeLocation());
		UserCommand fakeCommand = new UserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team return"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecuteRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		xTeam.getInstance().getPlayerManager().getPlayer("protocos").setLastAttacked(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		xTeam.getInstance().getPlayerManager().getPlayer("protocos").setReturnLocation(new FakeLocation());
		UserCommand fakeCommand = new UserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team return"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecuteRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(xTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		TeleportScheduler.getInstance().setCurrentTask(teamPlayer, 0);
		teamPlayer.setReturnLocation(new FakeLocation());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		UserCommand fakeCommand = new UserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team return"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
		//		Configuration.returnLocations.clear();
	}
}
