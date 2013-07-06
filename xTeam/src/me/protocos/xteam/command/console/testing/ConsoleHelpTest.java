package me.protocos.xteam.command.console.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.console.ConsoleHelp;
import me.protocos.xteam.testing.FakeConsoleSender;
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
		xTeam.registerConsoleCommands(xTeam.cm);
		fakeConsoleSender = new FakeConsoleSender();
	}
	@Test
	public void ShouldBeConsoleHelpExecute()
	{
		//ASSEMBLE
		ConsoleCommand fakeCommand = new ConsoleHelp(fakeConsoleSender, "", "team");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Console Commands: {optional} [required] pick/one\n" +
				"team info [Player/Team] - get info on sender/team\n" +
				"team list - list all teams on the server\n" +
				"team set [Player] [Team] - set team of sender\n" +
				"team setleader [Team] [Player] - set leader of team\n" +
				"team promote [Team] [Player] - promote admin of team\n" +
				"team demote [Team] [Player] - demote admin of team\n" +
				"team remove [Team] [Player] - remove member of team\n" +
				"team rename [Team] [Name] - rename a team\n" +
				"team tag [Team] [UserTag] - set team tag\n" +
				"team disband [Team] - disband a team\n" +
				"team open [Team] - open team to public joining\n" +
				"team teleallhq - teleports everyone to their UserHeadquarters\n" +
				"team reload - reloads the configuration file\n", fakeConsoleSender.getAllMessages());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
