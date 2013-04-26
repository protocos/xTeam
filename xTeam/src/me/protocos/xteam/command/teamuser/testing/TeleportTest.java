package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamuser.Teleport;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeleportTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamUserTeleExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", Data.BUKKIT.getPlayer("protocos").getLocation());
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You've been teleported to protocos", fakePlayerSender.getLastMessage());
		Assert.assertEquals(Data.BUKKIT.getPlayer("protocos").getLocation(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecuteNoName()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", Data.BUKKIT.getPlayer("protocos").getLocation());
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("You've been teleported to protocos", fakePlayerSender.getLastMessage());
		Assert.assertEquals(Data.BUKKIT.getPlayer("protocos").getLocation(), fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecuteNotTeammate()
	{
		//ASSEMBLE
		Data.taskIDs.put("kmlanglois", null);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele mastermind");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotTeammateException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", Data.BUKKIT.getPlayer("protocos").getLocation());
		Location before = fakePlayerSender.getLocation();
		fakePlayerSender.setNoDamageTicks(1);
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecuteRecentAttacked()
	{
		//ASSEMBLE
		Data.LAST_ATTACKED_DELAY = 15;
		Data.lastAttacked.put("kmlanglois", System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecuteRecentRequest()
	{
		//ASSEMBLE
		Data.taskIDs.put("kmlanglois", null);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecuteRecentTeleport()
	{
		//ASSEMBLE
		Data.hasTeleported.put("kmlanglois", System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele protocos");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport within 60 seconds of last teleport\nYou must wait 60 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecuteSelfTele()
	{
		//ASSEMBLE
		Data.taskIDs.put("kmlanglois", null);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele kmlanglois");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player cannot teleport to themselves")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserTeleExecuteNoTeammatesOnline()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		BaseUserCommand fakeCommand = new Teleport(fakePlayerSender, "tele");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player has no teammates online")).getMessage(), fakePlayerSender.getLastMessage());
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
