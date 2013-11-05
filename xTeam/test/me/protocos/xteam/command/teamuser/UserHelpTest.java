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
		xTeam.registerAdminCommands(xTeam.getCommandManager());
		xTeam.registerLeaderCommands(xTeam.getCommandManager());
		xTeam.registerUserCommands(xTeam.getCommandManager());
	}
	@Test
	public void ShouldBeTeamUserHelpPageExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team help"));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 1/3] {optional} [required] pick/one\n" +
				"/team info {Team/Player} - Get team info or other team's info\n" +
				"/team list - List all teams on the server\n" +
				"/team create [Name] - Create a team\n" +
				"/team join [Team] - Join a team\n" +
				"/team leave - Leave your team\n" +
				"/team accept - Accept the most recent team invite\n" +
				"/team hq - Teleport to the team headquarters\n" +
				"/team tele {Player} - Teleport to nearest or specific teammate\n" +
				"/team return - Teleport to saved return location (1 use)", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHelpPageExecute2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team help 2"));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 2/3] {optional} [required] pick/one\n" +
				"/team chat {On/Off} - Toggle chatting with teammates\n" +
				"/team message [Message] - Send message to teammates\n" +
				"/team sethq - Set headquarters of team (every 1 hours)\n" +
				"/team invite [Player] - Invite sender to your team\n" +
				"/team promote [Player] - Promote sender on your team\n" +
				"/team demote [Player] - Demote sender on your team\n" +
				"/team disband - Disband the team\n" +
				"/team open - Open team to public joining\n" +
				"/team remove [Player] - Remove sender from your team", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHelpPageExecute3()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team help 3"));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 3/3] {optional} [required] pick/one\n" +
				"/team rename [Name] - Rename the team\n" +
				"/team tag [Tag] - Set the team tag\n" +
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
