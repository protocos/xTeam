package me.protocos.xteam.command.serveradmin;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.util.CommonUtil;
import org.junit.After;
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
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT 
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "setrally two".split(" ")));
		//ASSERT
		Assert.assertEquals("You set the rally point for team two", fakePlayerSender.getLastMessage());
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
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT 
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "setrally one".split(" ")));
		//ASSERT
		Assert.assertEquals("You set the rally point for team ONE", fakePlayerSender.getLastMessage());
		Assert.assertEquals(fakePlayerSender.getLocation(), teamCoordinator.getTeam("one").getRally());
		Assert.assertTrue(teleportScheduler.canRally(teamPlayer));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminSetRallyExecuteTeamNotExist()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "setrally three".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}