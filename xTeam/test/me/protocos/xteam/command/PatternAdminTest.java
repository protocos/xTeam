package me.protocos.xteam.command;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeamPlugin;
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
		xTeamPlugin.getInstance().registerAdminCommands(xTeamPlugin.getInstance().getCommandManager());
	}
	@Test
	public void ShouldBeTeamAdminInvite()
	{
		// "/team invite bob123"
		baseCmd = "invite";
		command = "invite PLAYER";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "invite PLAYER ";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "inv PLAYER ";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "i PLAYER";
		Assert.assertFalse(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "in PLAYER ";
		Assert.assertFalse(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "inv PLAYER 2";
		Assert.assertFalse(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamAdminPromote()
	{
		baseCmd = "promote";
		command = "promote PLAYER";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "promote PLAYER ";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "p PLAYER";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "pmte PLAYER ";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "promote PLAYER dfsagf";
		Assert.assertFalse(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
	}
	@Test
	public void ShouldBeTeamAdminSethq()
	{
		baseCmd = "sethq";
		command = "sethq";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "sethq ";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "shq";
		Assert.assertTrue(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
		command = "sethq dsaf ";
		Assert.assertFalse(command.matches(xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
	}
	@After
	public void takedown()
	{
		Assert.assertTrue(xTeamPlugin.getInstance().getCommandManager().getUsage("admin_" + baseCmd).replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + xTeamPlugin.getInstance().getCommandManager().getPattern("admin_" + baseCmd)));
	}
}
