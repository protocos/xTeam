package me.protocos.xteam.command.teamadmin.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamadmin.Invite;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InviteTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		Data.invites.clear();
	}
	@Test
	public void ShouldBeTeamAdminInviteExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Invite(fakePlayerSender, "invite lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You invited lonely", fakePlayerSender.getLastMessage());
		Assert.assertTrue(Data.invites.containsKey("lonely"));
		Assert.assertEquals(xTeam.tm.getTeam("one"), Data.invites.get("lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminInviteExecuteAdmin()
	{
		//ASSEMBLE
		xTeam.tm.getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		BaseUserCommand fakeCommand = new Invite(fakePlayerSender, "invite lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You invited lonely", fakePlayerSender.getLastMessage());
		Assert.assertTrue(Data.invites.containsKey("lonely"));
		Assert.assertEquals(xTeam.tm.getTeam("one"), Data.invites.get("lonely"));
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminInviteExecuteNotAdmin()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		BaseUserCommand fakeCommand = new Invite(fakePlayerSender, "invite lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotAdminException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(Data.invites.containsKey("lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerHasInvite()
	{
		//ASSEMBLE
		Data.invites.put("lonely", xTeam.tm.getTeam("two"));
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Invite(fakePlayerSender, "invite lonely");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasInviteException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertTrue(Data.invites.containsKey("lonely"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerNeverPlayed()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Invite(fakePlayerSender, "invite newbie");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNeverPlayedException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(Data.invites.containsKey("newbie"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminInviteExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		BaseUserCommand fakeCommand = new Invite(fakePlayerSender, "invite mastermind");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(Data.invites.containsKey("mastermind"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamAdminInviteExecuteSelfInvite()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Invite(fakePlayerSender, "invite kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerInviteException("Player cannot invite self")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(Data.invites.containsKey("kmlanglois"));
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
