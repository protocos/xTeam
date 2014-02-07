package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.exception.TeamPlayerNotAdminException;
import me.protocos.xteam.exception.TeamPlayerNotTeammateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamAdminPromoteTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamAdminPromote()
	{
		Assert.assertTrue("promote PLAYER".matches(new TeamAdminPromote().getPattern()));
		Assert.assertTrue("promote PLAYER ".matches(new TeamAdminPromote().getPattern()));
		Assert.assertTrue("p PLAYER".matches(new TeamAdminPromote().getPattern()));
		Assert.assertTrue("pmte PLAYER ".matches(new TeamAdminPromote().getPattern()));
		Assert.assertFalse("promote PLAYER dfsagf".matches(new TeamAdminPromote().getPattern()));
		Assert.assertTrue(new TeamAdminPromote().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamAdminPromote().getPattern()));
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote protocos".split(" ")));
		//ASSERT
		Assert.assertEquals("You promoted protocos", fakePlayerSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("protocos"));
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNotAdmin()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote kmlanglois".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote protocos".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamAdminPromoteExecutePlayerNotTeammate()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamAdminCommand fakeCommand = new TeamAdminPromote();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "promote Lonely".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(XTeam.getInstance().getTeamManager().getTeam("one").getAdmins().contains("Lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
