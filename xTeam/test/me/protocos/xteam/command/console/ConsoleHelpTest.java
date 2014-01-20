package me.protocos.xteam.command.console;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.command.CommandContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleHelpTest
{
	FakeConsoleSender fakeConsoleSender;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		fakeConsoleSender = new FakeConsoleSender();
	}

	@Test
	public void ShouldBeConsoleHelp()
	{
		Assert.assertTrue("".matches(new ConsoleHelp().getPattern()));
		Assert.assertTrue("help".matches(new ConsoleHelp().getPattern()));
		Assert.assertTrue("help ".matches(new ConsoleHelp().getPattern()));
		Assert.assertFalse("help 1".matches(new ConsoleHelp().getPattern()));
		Assert.assertTrue("?".matches(new ConsoleHelp().getPattern()));
		Assert.assertTrue("? ".matches(new ConsoleHelp().getPattern()));
		Assert.assertFalse("? 1".matches(new ConsoleHelp().getPattern()));
		Assert.assertFalse("1".matches(new ConsoleHelp().getPattern()));
		Assert.assertTrue(new ConsoleHelp().getUsage().replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ConsoleHelp().getPattern()));
	}

	@Test
	public void ShouldBeConsoleHelpExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(new CommandContainer(fakeConsoleSender, "team", "".split(" ")));
		//ASSERT
		Assert.assertEquals("Console Commands: {optional} [required] pick/one\n" +
				"/team debug [Option] - console debug menu for xTeam\n" +
				"/team demote [Team] [Player] - demote team admin\n" +
				"/team disband [Team] - disband a team\n" +
				"/team {help} - console help menu for xTeam\n" +
				"/team info [Player/Team] - get info on player/team\n" +
				"/team list - list all teams on the server\n" +
				"/team promote [Team] [Player] - promote player to admin\n" +
				"/team remove [Team] [Player] - remove player from team\n" +
				"/team rename [Team] [Name] - rename a team\n" +
				"/team tag [Team] [Tag] - set team tag\n" +
				"/team open [Team] - open team to public joining\n" +
				"/team set [Player] [Team] - set team of player\n" +
				"/team setleader [Team] [Player] - set leader of team\n" +
				"/team teleallhq - teleports everyone to their headquarters\n" +
				"", fakeConsoleSender.getAllMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}
