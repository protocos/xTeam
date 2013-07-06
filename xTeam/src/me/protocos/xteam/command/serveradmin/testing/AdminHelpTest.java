package me.protocos.xteam.command.serveradmin.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.command.serveradmin.AdminHelp;
import me.protocos.xteam.core.exception.TeamInvalidPageException;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
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
		xTeam.registerServerAdminCommands(xTeam.cm);
	}
	@Test
	public void ShouldBeServerAdminHelpPageExecute1()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		BaseServerAdminCommand fakeCommand = new AdminHelp(fakePlayerSender, "admin 1", "/team");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Admin Commands: [Page 1/2] {optional} [required] pick/one\n" +
				"/team set [Player] [Team] - <admin> set team of player\n" +
				"/team hq [Team] - <admin> teleport to team headquarters\n" +
				"/team sethq [Team] - <admin> set team headquarters for team\n" +
				"/team setleader [Team] [Player] - <admin> set leader of team\n" +
				"/team promote [Team] [Player] - <admin> promote admin of team\n" +
				"/team demote [Team] [Player] - <admin> demote admin of team\n" +
				"/team remove [Team] [Player] - <admin> remove player of team\n" +
				"/team teleallhq - <admin> teleports everyone to their HQ\n" +
				"/team tpall [Team] - <admin> teleports team to yourself", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeServerAdminHelpPageExecute2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		BaseServerAdminCommand fakeCommand = new AdminHelp(fakePlayerSender, "admin 2", "/team");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("Admin Commands: [Page 2/2] {optional} [required] pick/one\n" +
				"/team chatspy - <admin> spy on team chat\n" +
				"/team rename [Team] [Name] - <admin> rename a team\n" +
				"/team tag [Team] [UserTag] - <admin> set team tag\n" +
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
		BaseServerAdminCommand fakeCommand = new AdminHelp(fakePlayerSender, "admin 3", "/team");
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
