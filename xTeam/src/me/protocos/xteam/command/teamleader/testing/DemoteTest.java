package me.protocos.xteam.command.teamleader.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamleader.Demote;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerLeaderDemoteException;
import me.protocos.xteam.exception.TeamPlayerNotLeaderException;
import me.protocos.xteam.exception.TeamPlayerNotTeammateException;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
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
		BaseUserCommand fakeCommand = new Demote(fakePlayerSender, "demote protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
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
		BaseUserCommand fakeCommand = new Demote(fakePlayerSender, "demote kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerLeaderDemoteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(xTeam.tm.getTeam("one").getAdmins().contains("protocos"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminDemoteExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		BaseUserCommand fakeCommand = new Demote(fakePlayerSender, "demote protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
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
		BaseUserCommand fakeCommand = new Demote(fakePlayerSender, "demote protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
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
		BaseUserCommand fakeCommand = new Demote(fakePlayerSender, "demote mastermind");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
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