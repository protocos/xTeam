package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserInfoTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeTeamUserInfo()
	{
		Assert.assertTrue("info".matches(new TeamUserInfo().getPattern()));
		Assert.assertTrue("info ".matches(new TeamUserInfo().getPattern()));
		Assert.assertTrue("info PLAYERTEAM".matches(new TeamUserInfo().getPattern()));
		Assert.assertTrue("info PLAYERTEAM ".matches(new TeamUserInfo().getPattern()));
		Assert.assertTrue("info 1".matches(new TeamUserInfo().getPattern()));
		Assert.assertTrue("i".matches(new TeamUserInfo().getPattern()));
		Assert.assertTrue("i ".matches(new TeamUserInfo().getPattern()));
		Assert.assertTrue(new TeamUserInfo().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new TeamUserInfo().getPattern()));
	}

	@Test
	public void ShouldBeTeamUserInfoExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - ONE\n" +
				"Team Tag - TeamAwesome\n" +
				"Team Leader - kmlanglois\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - X:170 Y:65 Z:209\n" +
				"Teammates online:\n" +
				"    kmlanglois Health: 100% Location: 0 64 0 in \"world\"\n" +
				"    protocos Health: 100% Location: 0 64 0 in \"world\"", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info two".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - two\n" +
				"Team Leader - mastermind\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - None set\n" +
				"Teammates online:\n" +
				"    mastermind", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute3()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info mastermind".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - two\n" +
				"Team Leader - mastermind\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - None set\n" +
				"Teammates online:\n" +
				"    mastermind", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute4()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info red".split(" ")));
		//ASSERT
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute5()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info strandedhelix".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - red\n" +
				"Team Tag - REDONE\n" +
				"Team Joining - Open\n" +
				"Team Headquarters - None set\n" +
				"Teammates offline:\n" +
				"    strandedhelix\n" +
				"    teammate", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute6()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("red").addPlayer("Lonely");
		xTeam.getInstance().getTeamManager().getTeam("red").promote("strandedhelix");
		xTeam.getInstance().getTeamManager().getTeam("red").promote("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("strandedhelix", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - red\n" +
				"Team Tag - REDONE\n" +
				"Team Admins - strandedhelix, Lonely\n" +
				"Team Joining - Open\n" +
				"Team Headquarters - None set\n" +
				"Teammates online:\n" +
				"    Lonely Health: 100% Location: 0 64 0 in \"world\"\n" +
				"Teammates offline:\n" +
				"    strandedhelix was last online on Dec 31 @ 6:00 PM\n" +
				"    teammate was last online on Dec 31 @ 6:00 PM", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute7()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - ONE\n" +
				"Team Tag - TeamAwesome\n" +
				"Team Leader - kmlanglois\n" +
				"Team Admins - protocos\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - X:170 Y:65 Z:209\n" +
				"Teammates online:\n" +
				"    kmlanglois Health: 100% Location: 0 64 0 in \"world\"\n" +
				"    protocos Health: 100% Location: 0 64 0 in \"world\"", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecutePlayerNotOnTeamUsingTag()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info REDONE".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - red\n" +
				"Team Tag - REDONE\n" +
				"Team Joining - Open\n" +
				"Team Headquarters - None set\n" +
				"Teammates offline:\n" +
				"    strandedhelix\n" +
				"    teammate", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecutePlayerOnTeamUsingTag()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info TeamAwesome".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - ONE\n" +
				"Team Tag - TeamAwesome\n" +
				"Team Leader - kmlanglois\n" +
				"Team Admins - protocos\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - X:170 Y:65 Z:209\n" +
				"Teammates online:\n" +
				"    kmlanglois Health: 100% Location: 0 64 0 in \"world\"\n" +
				"    protocos Health: 100% Location: 0 64 0 in \"world\"", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		TeamUserCommand fakeCommand = new TeamUserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info truck".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamOrPlayerDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
