package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamuser.Help;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamInvalidPageException;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
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
		BaseUserCommand fakeCommand = new Help(fakePlayerSender, "help", "/team");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
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
		BaseUserCommand fakeCommand = new Help(fakePlayerSender, "help 2", "/team");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 2/3] {optional} [required] pick/one\n" +
				"/team chat {on/off} - Toggle chatting with teammates\n" +
				"/team msg [message] - Send message only to teammates\n" +
				"/team sethq - Set headquarters of team (every 1 hours)\n" +
				"/team invite [Player] - invite player to your team\n" +
				"/team promote [Player] - promote player on your team\n" +
				"/team demote [Player] - demote player on your team\n" +
				"/team open - open team to public joining\n" +
				"/team remove [Player] - remove player from your team\n" +
				"/team rename [Name] - rename the team", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHelpPageExecute3()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Help(fakePlayerSender, "help 3", "/team");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 3/3] {optional} [required] pick/one\n" +
				"/team tag [Tag] - set the team tag\n" +
				"/team setleader [Player] - set new leader for the team\n" +
				" \n" +
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
		BaseUserCommand fakeCommand = new Help(fakePlayerSender, "help 4", "/team");
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
