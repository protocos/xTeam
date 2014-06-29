package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.exception.TeamInvalidPageException;
import me.protocos.xteam.fakeobjects.FakePlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamUserHelpTest
{
	private TeamPlugin teamPlugin;
	private TeamUserCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamUserHelp(teamPlugin);
	}

	@Test
	public void ShouldBeTeamUserHelp()
	{
		Assert.assertTrue("help 1".matches(fakeCommand.getPattern()));
		Assert.assertTrue("help 1".matches(fakeCommand.getPattern()));
		Assert.assertTrue("help 1 ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("? 1".matches(fakeCommand.getPattern()));
		Assert.assertTrue("??? 1 ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("1".matches(fakeCommand.getPattern()));
		Assert.assertTrue("2 ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("".matches(fakeCommand.getPattern()));
		Assert.assertFalse("1 dfas".matches(fakeCommand.getPattern()));
		Assert.assertFalse("11 ?".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTeamUserHelpPageExecutePage1()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "help 1".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 1/5] {optional} [required] pick/one\n" +
				"/team {help} - main help menu for xTeam\n" +
				"/team {help} [Page] - user help page for xTeam\n" +
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
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "help 2".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 2/5] {optional} [required] pick/one\n" +
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
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "help 3".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 3/5] {optional} [required] pick/one\n" +
				"/team disband - disband the team\n" +
				"/team open - open team to public joining\n" +
				"/team remove [Player] - remove player from your team\n" +
				"/team rename [Name] - rename the team\n" +
				"/team tag [Tag] - set the team tag\n" +
				"/team setleader [Player] - set new leader for the team\n" +
				"/team setrally - set rally point for the team\n" +
				"/team chatspy - spy on team chat\n" +
				"/team debug {Option} - server admin debug menu for xTeam", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHelpPageExecutePage4()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "help 4".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 4/5] {optional} [required] pick/one\n" +
				"/team disband [Team] - disband a team\n" +
				"/team demote [Team] [Player] - demote team admin\n" +
				"/team hq [Team] - teleport to team headquarters for team\n" +
				"/team promote [Team] [Player] - promote player to admin\n" +
				"/team remove [Player] [Team] - remove player from team\n" +
				"/team rename [Team] [Name] - rename a team\n" +
				"/team tag [Team] [Tag] - set team tag\n" +
				"/team open [Team] - open team to public joining\n" +
				"/team set [Player] [Team] - set team of player", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamUserHelpPageExecutePage5()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "help 5".split(" ")));
		//ASSERT
		Assert.assertEquals("Team Commands: [Page 5/5] {optional} [required] pick/one\n" +
				"/team sethq [Team] - set team headquarters for team\n" +
				"/team setleader [Team] [Player] - set leader of team\n" +
				"/team setrally [Team] - set team rally point for team\n" +
				"/team teleallhq - teleports everyone to their headquarters\n" +
				"/team tpall [Team] - teleports a team to yourself\n" +
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
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakePlayerSender, "team", "help 10".split(" ")));
		//ASSERT
		Assert.assertEquals((new TeamInvalidPageException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
