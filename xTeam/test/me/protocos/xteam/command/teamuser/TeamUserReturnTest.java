package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserReturnTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamUserReturn()
	{
		Assert.assertTrue("return".matches(new TeamUserReturn().getPattern()));
		Assert.assertTrue("return ".matches(new TeamUserReturn().getPattern()));
		Assert.assertTrue("ret".matches(new TeamUserReturn().getPattern()));
		Assert.assertTrue("r ".matches(new TeamUserReturn().getPattern()));
		Assert.assertFalse("return HOME ".matches(new TeamUserReturn().getPattern()));
		Assert.assertTrue(new TeamUserReturn().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserReturn().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserReturnExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamPlayer teamPlayer = CommonUtil.assignFromType(XTeam.getInstance().getPlayerManager().getPlayer("protocos"), TeamPlayer.class);
		Location returnLocation = new FakeLocation(XTeam.getInstance().getTeamManager().getTeam("one").getHeadquarters().getLocation()).toLocation();
		XTeam.getInstance().getPlayerManager().getPlayer("protocos").setReturnLocation(returnLocation);
		TeamUserCommand fakeCommand = new TeamUserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "return".split(" ")));
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
		TeamUserCommand fakeCommand = new TeamUserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "return".split(" ")));
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
		XTeam.getInstance().getPlayerManager().getPlayer("protocos").setReturnLocation(new FakeLocation());
		fakePlayerSender.setNoDamageTicks(1);
		TeamUserCommand fakeCommand = new TeamUserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "return".split(" ")));
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
		XTeam.getInstance().getPlayerManager().getPlayer("protocos").setReturnLocation(new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "return".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecuteRecentAttacked()
	{
		//ASSEMBLE
		Configuration.LAST_ATTACKED_DELAY = 15;
		XTeam.getInstance().getPlayerManager().getPlayer("protocos").setLastAttacked(System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		XTeam.getInstance().getPlayerManager().getPlayer("protocos").setReturnLocation(new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "return".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserReturnExecuteRecentRequest()
	{
		//ASSEMBLE
		TeamPlayer teamPlayer = CommonUtil.assignFromType(XTeam.getInstance().getPlayerManager().getPlayer("kmlanglois"), TeamPlayer.class);
		TeleportScheduler.getInstance().setCurrentTask(teamPlayer, 0);
		teamPlayer.setReturnLocation(new FakeLocation());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		TeamUserCommand fakeCommand = new TeamUserReturn();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "return".split(" ")));
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
