package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamuser.UserHeadquarters;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HeadquartersTest
{
	Location before;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
		before = new FakeLocation();
	}
	@Test
	public void ShouldBeTeamUserHQ()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		BaseUserCommand fakeCommand = new UserHeadquarters(fakePlayerSender, "hq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("WHOOSH!", fakePlayerSender.getLastMessage());
		Assert.assertEquals(xTeam.tm.getTeam("one").getHeadquarters(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
		//TODO assert everything! (including teleport)
	}
	@Test
	public void ShouldBeTeamUserHQNoHeadquarters()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", before);
		BaseUserCommand fakeCommand = new UserHeadquarters(fakePlayerSender, "hq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNoHeadquartersException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHQPlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		fakePlayerSender.setNoDamageTicks(1);
		BaseUserCommand fakeCommand = new UserHeadquarters(fakePlayerSender, "hq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHQPlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", before);
		BaseUserCommand fakeCommand = new UserHeadquarters(fakePlayerSender, "hq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHQRecentAttacked()
	{
		//ASSEMBLE
		Data.LAST_ATTACKED_DELAY = 15;
		Data.lastAttacked.put("kmlanglois", System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		BaseUserCommand fakeCommand = new UserHeadquarters(fakePlayerSender, "hq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHQRecentRequest()
	{
		//ASSEMBLE
		Data.taskIDs.put("kmlanglois", null);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		BaseUserCommand fakeCommand = new UserHeadquarters(fakePlayerSender, "hq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserHQRecentTeleport()
	{
		//ASSEMBLE
		Data.hasTeleported.put("kmlanglois", System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", before);
		BaseUserCommand fakeCommand = new UserHeadquarters(fakePlayerSender, "hq");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport within 60 seconds of last teleport\nYou must wait 60 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.LAST_ATTACKED_DELAY = 0;
		Data.hasTeleported.clear();
		Data.lastAttacked.clear();
	}
}
