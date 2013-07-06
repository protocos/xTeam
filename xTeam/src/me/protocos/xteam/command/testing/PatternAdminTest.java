package me.protocos.xteam.command.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.Command;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatternAdminTest
{
	private String command;
	private String baseCmd;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		xTeam.registerAdminCommands(xTeam.cm);
	}
	@Test
	public void ShouldBeTeamAdminInvite()
	{
		baseCmd = "invite";
		command = "invite PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "invite PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "inv PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "i PLAYER";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "in PLAYER ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "inv PLAYER 2";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamAdminPromote()
	{
		baseCmd = "promote";
		command = "promote PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "promote PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "p PLAYER";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "pmte PLAYER ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "promote PLAYER dfsagf";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamAdminSethq()
	{
		baseCmd = "sethq";
		command = "sethq";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "sethq ";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "shq";
		Assert.assertTrue(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
		command = "sethq dsaf ";
		Assert.assertFalse(command.matches(xTeam.cm.getPattern("admin_" + baseCmd)));
	}
	@After
	public void takedown()
	{
		Command.setBaseCommand("/team");
		Assert.assertTrue(xTeam.cm.getUsage("admin_" + baseCmd).replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + xTeam.cm.getPattern("admin_" + baseCmd)));
	}
}
