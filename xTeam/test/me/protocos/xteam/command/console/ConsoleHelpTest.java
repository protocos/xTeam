package me.protocos.xteam.command.console;

import junit.framework.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleHelpTest
{
	private TeamPlugin teamPlugin;
	private FakeConsoleSender fakeConsoleSender;
	private ConsoleCommand fakeCommand;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeConsoleSender = new FakeConsoleSender();
		fakeCommand = new ConsoleHelp(teamPlugin);
	}

	@Test
	public void ShouldBeConsoleHelp()
	{
		Assert.assertTrue("".matches(fakeCommand.getPattern()));
		Assert.assertTrue("help".matches(fakeCommand.getPattern()));
		Assert.assertTrue("help ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("help 1".matches(fakeCommand.getPattern()));
		Assert.assertTrue("?".matches(fakeCommand.getPattern()));
		Assert.assertTrue("? ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("? 1".matches(fakeCommand.getPattern()));
		Assert.assertFalse("1".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeConsoleHelpExecute()
	{
		//ASSEMBLE
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "".split(" ")));
		//ASSERT
		Assert.assertEquals("Console Commands: {optional} [required] pick/one\n" +
				"team debug [Option] - console debug menu for xTeam\n" +
				"team demote [Team] [Player] - demote team admin\n" +
				"team disband [Team] - disband a team\n" +
				"team {help} - console help menu for xTeam\n" +
				"team info [Player/Team] - get info on player/team\n" +
				"team list - list all teams on the server\n" +
				"team promote [Team] [Player] - promote player to admin\n" +
				"team remove [Player] [Team] - remove player from team\n" +
				"team rename [Team] [Name] - rename a team\n" +
				"team tag [Team] [Tag] - set team tag\n" +
				"team open [Team] - open team to public joining\n" +
				"team set [Player] [Team] - set team of player\n" +
				"team sethq [Team] [World] [X] [Y] [Z] - set headquarters of team\n" +
				"team setleader [Team] [Player] - set leader of team\n" +
				"team setrally [Team] [World] [X] [Y] [Z] - set rally point of team\n" +
				"team teleallhq - teleports everyone to their headquarters", fakeConsoleSender.getAllMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
