package me.protocos.xteam.command.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatternConsoleTest
{
	private String command;
	private String baseCmd;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		xTeam.registerConsoleCommands(xTeam.cm);
	}
	@Test
	public void ShouldBeConsoleDelete()
	{
		baseCmd = "disband";
		command = "disband TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "disband TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "disband";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "disband ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "d TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleDemote()
	{
		baseCmd = "demote";
		command = "dem TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "demote TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "de TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "dem TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "demote TEAM ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "dem";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "demote ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleHelp()
	{
		baseCmd = "help";
		command = "";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "help";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "help ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "help 1";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "?";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "? ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "? 1";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "1";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleInfo()
	{
		baseCmd = "info";
		command = "info TEAM/PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "info TEAM/PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "info";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "info ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleList()
	{
		baseCmd = "list";
		command = "list";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "list ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "l";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "l ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "li";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "li ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "ls";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "ls ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "ls1";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "ls 1";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsolePromote()
	{
		baseCmd = "promote";
		command = "promote TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "promote TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "pr TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "prom TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "promote TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "promote TEAM ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "promote";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "promote ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleReload()
	{
		baseCmd = "reload";
		command = "reload";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "rel ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "r";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "rd";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "re 1";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleRemove()
	{
		baseCmd = "remove";
		command = "remove TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "remove TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "rem TEAM PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "remv TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "rm TEAM PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "r TEAM PLAYER";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleRename()
	{
		baseCmd = "rename";
		command = "rename TEAM NAME";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "rename TEAM NAME ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "ren TEAM NAME ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "rn TEAM NAME ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "re TEAM NAME ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "r TEAM NAME";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "re TEAM NAME ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "r";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "re";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamConsoleTag()
	{
		baseCmd = "tag";
		command = "tag TEAM TAG";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "tag TEAM TAG ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "tag TEAM TAG ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "t TEAM TAG ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "ta TEAM TAG";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "tg TEAM TAG ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "tg TEAM TAG sdfhkabkl";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamConsoleOpen()
	{
		baseCmd = "open";
		command = "open TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "open TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "open TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "o TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "op TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "open TEAM sdfhkabkl";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleSet()
	{
		baseCmd = "set";
		command = "set PLAYER TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "set PLAYER TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "s PLAYER TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "se PLAYER TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "s";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "se ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleSetLeader()
	{
		baseCmd = "setleader";
		command = "setleader PLAYER TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "setleader PLAYER TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "s PLAYER TEAM";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "se PLAYER TEAM ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "s";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "se ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@Test
	public void ShouldBeConsoleTeleAllHQ()
	{
		baseCmd = "teleallhq";
		command = "teleallhq";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "teleallhq ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "t";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
		command = "tele ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("console_" + baseCmd)));
	}
	@After
	public void takedown()
	{
		BaseCommand.setBaseCommand("/team");
		Assert.assertTrue(xTeam.cm.getUsage("console_" + baseCmd).replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + xTeam.cm.getPattern("console_" + baseCmd)));
	}
}
