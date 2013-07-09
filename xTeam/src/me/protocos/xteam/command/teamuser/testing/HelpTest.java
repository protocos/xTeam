package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.teamuser.UserHelp;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamInvalidPageException;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HelpTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		Data.LOCATIONS_ENABLED = false;
		xTeam.registerAdminCommands(xTeam.cm);
		xTeam.registerLeaderCommands(xTeam.cm);
		xTeam.registerUserCommands(xTeam.cm);
	}
	@Test
	public void ShouldBeTeamUserHelpPageExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp(fakePlayerSender, new CommandParser("/team help"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 1/3] {optional} [required] pick/one\n" +
				"/team info {Team/Player} - Get team info or other team's info\n" +
				"/team list - UserList all teams on the server\n" +
				"/team create [Name] - UserCreate a team\n" +
				"/team join [Team] - UserJoin a team\n" +
				"/team leave - UserLeave your team\n" +
				"/team accept - UserAccept the most recent team invite\n" +
				"/team hq - UserTeleport to the team headquarters\n" +
				"/team tele {Player} - UserTeleport to nearest or specific teammate\n" +
				"/team return - UserTeleport to saved return location (1 use)", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHelpPageExecute2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp(fakePlayerSender, new CommandParser("/team help 2"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 2/3] {optional} [required] pick/one\n" +
				"/team chat {On/Off} - Toggle chatting with teammates\n" +
				"/team message [UserMessage] - Send message to teammates\n" +
				"/team sethq - Set headquarters of team (every 1 hours)\n" +
				"/team invite [Player] - UserInvite sender to your team\n" +
				"/team promote [Player] - UserPromote sender on your team\n" +
				"/team demote [Player] - UserDemote sender on your team\n" +
				"/team disband - UserDisband the team\n" +
				"/team open - UserOpen team to public joining\n" +
				"/team remove [Player] - UserRemove sender from your team", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHelpPageExecute3()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp(fakePlayerSender, new CommandParser("/team help 3"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 3/3] {optional} [required] pick/one\n" +
				"/team rename [Name] - UserRename the team\n" +
				"/team tag [UserTag] - Set the team tag\n" +
				"/team setleader [Player] - Set new leader for the team\n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" ", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHelpPageExecuteInvalidPage()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp(fakePlayerSender, new CommandParser("/team help 4"));
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamInvalidPageException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
