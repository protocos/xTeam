package me.protocos.xteam.command.console;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsoleSetRallyTest
{
	private TeamPlugin teamPlugin;
	private FakeConsoleSender fakeConsoleSender;
	private ConsoleCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;
	private BukkitUtil bukkitUtil;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeConsoleSender = new FakeConsoleSender();
		fakeCommand = new ConsoleSetRally(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Test
	public void ShouldBeConsoleSetRally()
	{
		Assert.assertTrue("setrally TEAM WORLD 0 0 0".matches(fakeCommand.getPattern()));
		Assert.assertTrue("sral TEAM WORLD 0 0 0 ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("sr TEAM WORLD 0 0 0  ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("sr TEAM WORLD".matches(fakeCommand.getPattern()));
		Assert.assertFalse("setr TEAM WORLD ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("setr".matches(fakeCommand.getPattern()));
		Assert.assertFalse("setr ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").replaceAll("[XYZ]", "0").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleSetExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setrally one world 1.65 2.65 3.65".split(" ")));
		//ASSERT
		Assert.assertEquals("You set the rally point for ONE to X:1.65 Y:2.65 Z:3.65", fakeConsoleSender.getLastMessage());
		Assert.assertEquals(new Location(bukkitUtil.getWorld("world"), 1.65D, 2.65D, 3.65D, 0.0F, 0.0F), teamCoordinator.getTeam("one").getRally());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteRallyAlreadySet()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").setRally(new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setrally one world 1.65 2.65 3.65".split(" ")));
		//ASSERT
		Assert.assertEquals("You set the rally point for ONE to X:1.65 Y:2.65 Z:3.65", fakeConsoleSender.getLastMessage());
		Assert.assertEquals(new Location(bukkitUtil.getWorld("world"), 1.65D, 2.65D, 3.65D, 0.0F, 0.0F), teamCoordinator.getTeam("one").getRally());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleSetExecuteTeamNotExist()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "setrally DNE world 1.65 2.65 3.65".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
