package me.protocos.xteam.command.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatternLeaderTest
{
	private String command;
	private String baseCmd;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		xTeam.registerLeaderCommands(xTeam.cm);
	}
	@Test
	public void ShouldBeTeamLeaderDemote()
	{
		baseCmd = "demote";
		command = "demote PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "demote PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "dmte PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "d PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "dmte PLAYER dfsg ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamLeaderDisband()
	{
		baseCmd = "disband";
		command = "disband";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "disband  ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "d ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "disband  fdsa ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamLeaderOpen()
	{
		baseCmd = "open";
		command = "open";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "open  ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "o ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "open  fdsa ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamLeaderRemove()
	{
		baseCmd = "remove";
		command = "remove PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "remove PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "rm PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "rem PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "remv PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "rem PLAYER dfsa ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamLeaderRename()
	{
		baseCmd = "rename";
		command = "rename TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "rename TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "rn TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "ren TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "rename TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "rnm TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "rnm TEAM sdfhkabkl";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamLeaderTag()
	{
		baseCmd = "tag";
		command = "tag TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "tag TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "tag TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "t TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "ta TEAM";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "tg TEAM ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "tg TEAM sdfhkabkl";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamLeaderSetleader()
	{
		baseCmd = "setleader";
		command = "setleader PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "setleader PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "setl PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "setlead PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "set PLAYER dfsa";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
		command = "stl PLAYER ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("leader_" + baseCmd)));
	}
	@After
	public void takedown()
	{
		Assert.assertTrue(xTeam.cm.getUsage("leader_" + baseCmd).replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + xTeam.cm.getPattern("leader_" + baseCmd)));
	}
}
