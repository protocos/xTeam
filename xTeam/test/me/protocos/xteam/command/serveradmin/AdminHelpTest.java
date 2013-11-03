package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.exception.TeamInvalidPageException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminHelpTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		xTeam.registerServerAdminCommands(xTeam.getCommandManager());
	}
	@Test
	public void ShouldBeServerAdminHelpPageExecute1()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team admin"));
		//ASSERT
		Assert.assertEquals("Admin Commands: [Page 1/2] {optional} [required] pick/one\n" +
				"/team set [Player] [Team] - <admin> set team of sender\n" +
				"/team hq [Team] - <admin> teleport to team headquarters\n" +
				"/team sethq [Team] - <admin> set team headquarters for team\n" +
				"/team setleader [Team] [Player] - <admin> set leader of team\n" +
				"/team promote [Team] [Player] - <admin> promote admin of team\n" +
				"/team demote [Team] [Player] - <admin> demote admin of team\n" +
				"/team remove [Team] [Player] - <admin> remove sender of team\n" +
				"/team teleallhq - <admin> teleports everyone to their HQ\n" +
				"/team tpall [Team] - <admin> teleports team to yourself", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminHelpPageExecute2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team admin 2"));
		//ASSERT
		Assert.assertEquals("Admin Commands: [Page 2/2] {optional} [required] pick/one\n" +
				"/team chatspy - <admin> spy on team chat\n" +
				"/team rename [Team] [Name] - <admin> rename a team\n" +
				"/team tag [Team] [Tag] - <admin> set team tag\n" +
				"/team disband [Team] - <admin> disband a team\n" +
				"/team open [Team] - <admin> open team to public joining\n" +
				"/team reload - <admin> reload the config files\n" +
				" \n" +
				" \n" +
				" ", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminHelpPageExecuteInvaidPage()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new AdminHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team admin 3"));
		//ASSERT
		Assert.assertEquals((new TeamInvalidPageException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
