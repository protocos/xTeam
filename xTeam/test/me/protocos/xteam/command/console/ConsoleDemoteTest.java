package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleDemoteTest
{
	FakeConsoleSender fakeConsoleSender;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		fakeConsoleSender = new FakeConsoleSender();
		XTeam.getInstance().getTeamManager().getTeam("one").promote("protocos");
	}

	@Test
	public void ShouldBeConsoleDemote()
	{
		Assert.assertTrue("dem TEAM PLAYER".matches(new ConsoleDemote().getPattern()));
		Assert.assertTrue("demote TEAM PLAYER ".matches(new ConsoleDemote().getPattern()));
		Assert.assertTrue("de TEAM PLAYER".matches(new ConsoleDemote().getPattern()));
		Assert.assertFalse("dem TEAM".matches(new ConsoleDemote().getPattern()));
		Assert.assertFalse("demote TEAM ".matches(new ConsoleDemote().getPattern()));
		Assert.assertFalse("dem".matches(new ConsoleDemote().getPattern()));
		Assert.assertFalse("demote ".matches(new ConsoleDemote().getPattern()));
		Assert.assertTrue(new ConsoleDemote().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleDemote().getPattern()));
	}

	@Test
	public void ShouldBeConsoleDemoteExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "demote one protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("You demoted protocos", fakeConsoleSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleDemoteExecuteDemoteLeader()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "demote one kmlanglois".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderDemoteException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleDemoteExecuteIncorrectTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "demote one mastermind".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotOnTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleDemoteExecutePlayerHasNoTeam()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "demote one Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleDemoteExecutePlayerHasNotPlayed()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "demote one newbie".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleDemoteExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		XTeam.getInstance().getTeamManager().getTeam("one").demote("protocos");
		ConsoleCommand fakeCommand = new ConsoleDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "demote one protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeConsoleDemoteExecuteTeamNotExists()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "demote three protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakeConsoleSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
