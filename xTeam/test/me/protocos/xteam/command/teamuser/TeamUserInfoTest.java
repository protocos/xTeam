package me.protocos.xteam.command.teamuser;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamUserInfoTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private InviteHandler inviteHandler;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserInfo(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
		playerFactory = teamPlugin.getPlayerFactory();
		inviteHandler = teamPlugin.getInviteHandler();
	}

	@Test
	public void ShouldBeTeamUserInfo()
	{
		Assert.assertTrue("info".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info PLAYERTEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info PLAYERTEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("info 1".matches(fakeCommand.getPattern()));
		Assert.assertTrue("i".matches(fakeCommand.getPattern()));
		Assert.assertTrue("i ".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserInfoExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "protocos", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - ONE\n" +
				"Team Tag - TeamAwesome\n" +
				"Team Leader - kmlanglois\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - X:170 Y:65 Z:209\n" +
				"Teammates online:\n" +
				"    protocos (100%) Location: 0 64 0 in \"world\"\n" +
				"    kmlanglois (100%) Location: 0 64 0 in \"world\""
				, fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
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
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
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
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info red".split(" ")));
		//ASSERT
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute5()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info strandedhelix".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - red\n" +
				"Team Tag - REDONE\n" +
				"Team Joining - Open\n" +
				"Team Headquarters - None set\n" +
				"Teammates offline:\n" +
				"    teammate\n" +
				"    strandedhelix", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute6()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("red").addPlayer("Lonely");
		teamCoordinator.getTeam("red").promote("strandedhelix");
		teamCoordinator.getTeam("red").promote("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "strandedhelix", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - red\n" +
				"Team Tag - REDONE\n" +
				"Team Admins - Lonely, strandedhelix\n" +
				"Team Joining - Open\n" +
				"Team Headquarters - None set\n" +
				"Teammates online:\n" +
				"    Lonely (100%) Location: 0 64 0 in \"world\"\n" +
				"Teammates offline:\n" +
				"    teammate was last online on Dec 31 @ 6:00 PM\n" +
				"    strandedhelix was last online on Dec 31 @ 6:00 PM", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecute7()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
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
				"    protocos (100%) Location: 0 64 0 in \"world\"\n" +
				"    kmlanglois (100%) Location: 0 64 0 in \"world\"", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecutePendingInvites()
	{
		//ASSEMBLE
		inviteHandler.addInvite(new InviteRequest(playerFactory.getPlayer("kmlanglois"), playerFactory.getPlayer("Lonely"), System.currentTimeMillis()));
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - ONE\n" +
				"Team Tag - TeamAwesome\n" +
				"Team Leader - kmlanglois\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - X:170 Y:65 Z:209\n" +
				"Team Invites - Lonely\n" +
				"Teammates online:\n" +
				"    protocos (100%) Location: 0 64 0 in \"world\"\n" +
				"    kmlanglois (100%) Location: 0 64 0 in \"world\"", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecutePlayerNotOnTeamUsingTag()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "info REDONE".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Name - red\n" +
				"Team Tag - REDONE\n" +
				"Team Joining - Open\n" +
				"Team Headquarters - None set\n" +
				"Teammates offline:\n" +
				"    teammate\n" +
				"    strandedhelix", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecutePlayerOnTeamUsingTag()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "kmlanglois", new FakeLocation());
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
				"    protocos (100%) Location: 0 64 0 in \"world\"\n" +
				"    kmlanglois (100%) Location: 0 64 0 in \"world\"", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserInfoExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
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
		FakePlayerSender fakePlayerSender = new FakePlayerSender(teamPlugin, "Lonely", new FakeLocation());
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
