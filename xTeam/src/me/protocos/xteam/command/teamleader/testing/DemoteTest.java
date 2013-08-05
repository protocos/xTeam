package me.protocos.xteam.command.teamleader.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.teamleader.UserDemote;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerLeaderDemoteException;
import me.protocos.xteam.core.exception.TeamPlayerNotLeaderException;
import me.protocos.xteam.core.exception.TeamPlayerNotTeammateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DemoteTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		xTeam.tm.getTeam("one").promote("protocos");
	}
	@Test
	public void ShouldBeTeamAdminDemoteExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team demote protocos"));
		//ASSERT
		Assert.assertEquals("You demoted protocos", fakePlayerSender.getLastMessage());
		Assert.assertFalse(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminDemoteExecuteLeaderDemote()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team demote kmlanglois"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderDemoteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminDemoteExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team demote protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminDemoteExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		UserCommand fakeCommand = new UserDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team demote protocos"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminDemoteExecuteNotTeammate()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserDemote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team demote mastermind"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
