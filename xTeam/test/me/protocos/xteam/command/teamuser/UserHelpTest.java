package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamInvalidPageException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserHelpTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		Data.LOCATIONS_ENABLED = false;
		xTeam.registerUserCommands(xTeam.getCommandManager());
		xTeam.registerAdminCommands(xTeam.getCommandManager());
		xTeam.registerLeaderCommands(xTeam.getCommandManager());
	}

	@Test
	public void ShouldBeTeamUserHelpPageExecutePage1()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team help 1"));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 1/3] {optional} [required] pick/one\n" +
				"/team {help} - main help menu for xTeam\n" +
				"/team {help} [Page] - user help menu for xTeam\n" +
				"/team info {Team/Player} - get team info or other team's info\n" +
				"/team list - list all teams on the server\n" +
				"/team create [Name] - create a team\n" +
				"/team join [Team] - join a team\n" +
				"/team leave - leave your team\n" +
				"/team accept - accept the most recent team invite\n" +
				"/team hq - teleport to the team headquarters", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHelpPageExecutePage2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team help 2"));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 2/3] {optional} [required] pick/one\n" +
				"/team tele {Player} - teleport to nearest or specific teammate\n" +
				"/team return - teleport to saved return location (1 use)\n" +
				"/team rally - teleport to team rally location\n" +
				"/team chat {On/Off} - toggle chatting with teammates\n" +
				"/team message [Message] - send message to teammates\n" +
				"/team sethq - set headquarters of team\n" +
				"/team invite [Player] - invite player to your team\n" +
				"/team promote [Player] - promote player to team admin\n" +
				"/team demote [Player] - demote team admin", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHelpPageExecutePage3()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team help 3"));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 3/3] {optional} [required] pick/one\n" +
				"/team disband - disband the team\n" +
				"/team open - open team to public joining\n" +
				"/team remove [Player] - remove player from your team\n" +
				"/team rename [Name] - rename the team\n" +
				"/team tag [Tag] - set the team tag\n" +
				"/team setleader [Player] - set new leader for the team\n" +
				"/team setrally - set rally point for the team\n" +
				" \n" +
				" ", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHelpPageExecuteInvalidPage()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team help 4"));
		//ASSERT
		Assert.assertEquals((new TeamInvalidPageException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
