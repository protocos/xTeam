package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.TeamInvalidPageException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerAdminHelpTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeServerAdminHelp()
	{
		Assert.assertTrue("admin".matches(new ServerAdminHelp().getPattern()));
		Assert.assertTrue("admin ".matches(new ServerAdminHelp().getPattern()));
		Assert.assertTrue("admin help".matches(new ServerAdminHelp().getPattern()));
		Assert.assertTrue("admn hlp ".matches(new ServerAdminHelp().getPattern()));
		Assert.assertTrue("admin 1".matches(new ServerAdminHelp().getPattern()));
		Assert.assertTrue("admin 1 ".matches(new ServerAdminHelp().getPattern()));
		Assert.assertFalse("".matches(new ServerAdminHelp().getPattern()));
		Assert.assertFalse(" ".matches(new ServerAdminHelp().getPattern()));
		Assert.assertTrue(new ServerAdminHelp().getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + new ServerAdminHelp().getPattern()));
	}

	@Test
	public void ShouldBeServerAdminHelpPageExecute1()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team admin"));
		//ASSERT
		Assert.assertEquals("Admin Commands: [Page 1/2] {optional} [required] pick/one\n" +
				"/team chatspy - spy on team chat\n" +
				"/team disband [Team] - disband a team\n" +
				"/team demote [Team] [Player] - demote team admin\n" +
				"/team admin {Page} - server admin help menu for xTeam\n" +
				"/team hq [Team] - teleport to team headquarters for team\n" +
				"/team promote [Team] [Player] - promote player to admin\n" +
				"/team remove [Team] [Player] - remove player from team\n" +
				"/team rename [Team] [Name] - rename a team\n" +
				"/team tag [Team] [Tag] - set team tag", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeServerAdminHelpPageExecute2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		ServerAdminCommand fakeCommand = new ServerAdminHelp();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team admin 2"));
		//ASSERT
		Assert.assertEquals("Admin Commands: [Page 2/2] {optional} [required] pick/one\n" +
				"/team open [Team] - open team to public joining\n" +
				"/team set [Player] [Team] - set team of player\n" +
				"/team sethq [Team] - set team headquarters for team\n" +
				"/team setleader [Team] [Player] - set leader of team\n" +
				"/team teleallhq - teleports everyone to their headquarters\n" +
				"/team tpall [Team] - teleports a team to yourself\n" +
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
		ServerAdminCommand fakeCommand = new ServerAdminHelp();
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
