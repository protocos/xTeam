package me.protocos.xteam.command;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatternUserTest
{
	private String command;
	private String baseCmd;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		xTeam.registerUserCommands(xTeam.getCommandManager());
	}
	@Test
	public void ShouldBeTeamUserAccept()
	{
		baseCmd = "accept";
		command = "accept";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "accept ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "acc";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "acpt ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "a";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "a dsafkln";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserChat()
	{
		baseCmd = "chat";
		command = "chat";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "chat ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "chat ONOFF";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "ch ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "c";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "ch daj;nme rjkn";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserCreate()
	{
		baseCmd = "create";
		command = "create TEAM";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "create TEAM ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "cr TEAM";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "creat TEAM ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "c TEAM ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "c ";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserHelpPage()
	{
		baseCmd = "help";
		command = "help 1";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "help 1 ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "? 1";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "??? 1 ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "1";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "2 ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));

		command = "";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "1 dfas";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "11 ?";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserHQ()
	{
		baseCmd = "hq";
		command = "hq";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "hq ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "h";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "hq dsaf";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "h dsaf";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserInfo()
	{
		baseCmd = "info";
		command = "info";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "info ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "info PLAYERTEAM";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "info PLAYERTEAM ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "info 1";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "i";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "i ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserJoin()
	{
		baseCmd = "join";
		command = "join TEAM";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "join TEAM ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "j TEAM";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "jn TEAM ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "j TEAM sdaf";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserLeave()
	{
		baseCmd = "leave";
		command = "leave";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "leave ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "leav ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "lv ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "le";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "l";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "leave TEAM";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserList()
	{
		baseCmd = "list";
		command = "list";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "list ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "ls";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "list TEAM";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserMainHelp()
	{
		baseCmd = "mainhelp";
		command = "";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = " ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "help";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "help ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "???";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "? ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserMessage()
	{
		baseCmd = "message";
		command = "message example test message";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "message example test message ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "msg example test message";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "msg example test message ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "tell example test message";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "tell example test message ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "m example test message ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "message";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "msg ";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserReturn()
	{
		baseCmd = "return";
		command = "return";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "return ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "ret";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "r ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "return HOME ";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamUserTele()
	{
		baseCmd = "tele";
		command = "tele";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "tele ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "tele PLAYER";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "tele PLAYER ";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "tp PLAYER";
		Assert.assertTrue(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
		command = "tp PLAYER wekn;ljdkkmsnaf";
		Assert.assertFalse(command.matches(xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
	@After
	public void takedown()
	{
		Assert.assertTrue(xTeam.getCommandManager().getUsage("user_" + baseCmd).replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + xTeam.getCommandManager().getPattern("user_" + baseCmd)));
	}
}
