package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.teamuser.UserLeave;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerLeaderLeavingException;
import me.protocos.xteam.command.CommandParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LeaveTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeDefaultTeamLeavingOnePerson()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("strandedhelix", new FakeLocation());
		UserCommand fakeCommand = new UserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team leave"));
		//ASSERT
		Assert.assertEquals("You left red", fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.contains("red"));
		Assert.assertFalse(xTeam.tm.getTeam("red").containsPlayer("strandedhelix"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeRegularTeamLeavingOnePerson()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", new FakeLocation());
		UserCommand fakeCommand = new UserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team leave"));
		//ASSERT
		Assert.assertEquals("You left two", fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.contains("two"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserLeaveExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Data.returnLocations.put(fakePlayerSender, new FakeLocation());
		UserCommand fakeCommand = new UserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team leave"));
		//ASSERT
		Assert.assertEquals("You left ONE", fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").containsPlayer("protocos"));
		Assert.assertFalse(Data.returnLocations.containsKey(fakePlayerSender));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserLeaveExecuteLeaderLeaving()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team leave"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderLeavingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").containsPlayer("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserLeaveExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserLeave();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team leave"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
