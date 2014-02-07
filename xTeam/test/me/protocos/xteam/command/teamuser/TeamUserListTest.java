package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserListTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamUserList()
	{
		Assert.assertTrue("list".matches(new TeamUserList().getPattern()));
		Assert.assertTrue("list ".matches(new TeamUserList().getPattern()));
		Assert.assertTrue("ls".matches(new TeamUserList().getPattern()));
		Assert.assertFalse("list TEAM".matches(new TeamUserList().getPattern()));
		Assert.assertTrue(new TeamUserList().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserList().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserListExecuteNoTeams()
	{
		//ASSEMBLE
		XTeam.getInstance().getTeamManager().clear();
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "list".split(" ")));
		//ASSERT
		Assert.assertEquals("There are no teams", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserListExecuteOneTeam()
	{
		//ASSEMBLE
		XTeam.getInstance().getTeamManager().disbandTeam("ONE");
		XTeam.getInstance().getTeamManager().disbandTeam("TWO");
		XTeam.getInstance().getTeamManager().disbandTeam("blue");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "list".split(" ")));
		//ASSERT
		Assert.assertEquals("Teams: red", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserListExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "list".split(" ")));
		//ASSERT
		Assert.assertEquals("Teams: ONE, two, red, blue", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
