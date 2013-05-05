package me.protocos.xteam.command.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatternServerAdminTest
{
	private String command;
	private String baseCmd;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		xTeam.registerServerAdminCommands(xTeam.cm);
	}
	@Test
	public void ShouldBeServerAdminChatSpy()
	{
		baseCmd = "chatspy";
		command = "chatspy";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "chatspy ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "chspy";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "cspy ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "c";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "cspy dasflk;j";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminDelete()
	{
		baseCmd = "disband";
		command = "disband TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "disband TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "dis TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "dis TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "d TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "dis TEAM dfsaiphjkl";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminDemote()
	{
		baseCmd = "demote";
		command = "demote TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "demote TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "dem TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "dem TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "d TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "d TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "dem TEAM PLAYER sdfkjahl";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminHelp()
	{
		baseCmd = "admin";
		command = "admin";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "admin ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "admin help";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "admn hlp ";// WHOA, cool?
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "admin 1";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "admin 1 ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = " ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminHQ()
	{
		baseCmd = "hq";
		command = "hq TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "hq TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "h TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "h TEAM ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "hq TEAM fdsaj;k";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminPromote()
	{
		baseCmd = "promote";
		command = "promote TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "promote TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "pr TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "p TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "pmte TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "pmte TEAM PLAYER dksjafl ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "pmte TEAM ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminReload()
	{
		baseCmd = "reload";
		command = "reload";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "reload ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "rl ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "rel ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "r";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "r alksldj;ladjksf";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminRemove()
	{
		baseCmd = "remove";
		command = "remove TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "remove TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "rem TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "remv TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "rm TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "r TEAM PLAYER";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminRename()
	{
		baseCmd = "rename";
		command = "rename TEAM NAME";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "rename TEAM NAME ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "ren TEAM NAME";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "ren TEAM NAME ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "r TEAM NAME";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "ren TEAM NAME dmtrnsabknb ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamAdminTag()
	{
		baseCmd = "tag";
		command = "tag TEAM TAG";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tag TEAM TAG ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tag TEAM TAG ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "t TEAM TAG ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "ta TEAM TAG";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tg TEAM TAG ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tg TEAM TAG sdfhkabkl";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamAdminOpen()
	{
		baseCmd = "open";
		command = "open TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "open TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "open TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "o TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "op TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "open TEAM sdfhkabkl";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminSet()
	{
		baseCmd = "set";
		command = "set PLAYER TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "set PLAYER TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "s PLAYER TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "st PLAYER TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "s PLAYER TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "st PLAYER TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "s";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "st PLAYER TEAM jadsldkn ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminSetHQ()
	{
		baseCmd = "sethq";
		command = "sethq TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "sethq TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "seth TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "set TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "set TEAM ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "sethq TEAM dfsjkal";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminSetLeader()
	{
		baseCmd = "setleader";
		command = "setleader TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "setleader TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "setl TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "setlead TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "s TEAM PLAYER";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "sl TEAM PLAYER ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminTeleALlHQ()
	{
		baseCmd = "teleallhq";
		command = "teleallhq";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "teleallhq ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "telea";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tele ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "teleallhq fdsakjn";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "teleallhq awkejnr ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminTpAll()
	{
		baseCmd = "tpall";
		command = "tpall TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tpall TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tp TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tp TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "t TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tpall";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "tpall ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@Test
	public void ShouldBeServerAdminUpdate()
	{
		baseCmd = "update";
		command = "update";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "update ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "up";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "u ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "update wkejlnrkjlksjf";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
		command = "update eqwlkejrnfs";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
	@After
	public void takedown()
	{
		BaseCommand.setBaseCommand("/team");
		Assert.assertTrue(xTeam.cm.getUsage("serveradmin_" + baseCmd).replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + xTeam.cm.getPattern("serveradmin_" + baseCmd)));
	}
}
