package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import me.protocos.xteam.util.CommonUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminSetRallyTest
{
	private TeamPlugin teamPlugin;
	private ServerAdminCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;
	private TeleportScheduler teleportScheduler;
	private IPlayerFactory playerFactory;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new ServerAdminSetRally(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		teleportScheduler = teamPlugin.getTeleportScheduler();
		playerFactory = teamPlugin.getPlayerFactory();
	}

	@Test
	public void ShouldBeServerAdminSetRally()
	{
		Assert.assertTrue("setrally TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("sral TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("sr TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("sr TEAM WORLD".matches(fakeCommand.getPattern()));
		Assert.assertFalse("setr TEAM WORLD ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("setr".matches(fakeCommand.getPattern()));
		Assert.assertFalse("setr ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeServerAdminSetRallyExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT 
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "setrally two");
		//ASSERT
		Assert.assertEquals("You set the rally point for team two", fakePlayerSender.getLastMessages());
		Assert.assertEquals(fakePlayerSender.getLocation(), teamCoordinator.getTeam("two").getRally());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetRallyExecuteCanRallyAfterSet()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").setRally(new FakeLocation());
		TeamPlayer teamPlayer = CommonUtil.assignFromType(playerFactory.getPlayer("protocos"), TeamPlayer.class);
		teleportScheduler.setRallyUsedFor(teamPlayer);
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT 
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "setrally one");
		//ASSERT
		Assert.assertEquals("You set the rally point for team ONE", fakePlayerSender.getLastMessages());
		Assert.assertEquals(fakePlayerSender.getLocation(), teamCoordinator.getTeam("one").getRally());
		Assert.assertTrue(teleportScheduler.canRally(teamPlayer));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetRallyExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "setrally three");
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
