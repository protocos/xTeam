package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamuser.Return;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamUserReturnExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location returnLocation = xTeam.tm.getTeam("one").getHeadquarters();
		Data.returnLocations.put(fakePlayerSender, returnLocation);
		BaseUserCommand fakeCommand = new Return(fakePlayerSender, "return");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("WHOOSH!", fakePlayerSender.getLastMessage());
		Assert.assertEquals(returnLocation, fakePlayerSender.getLocation());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserReturnExecuteNoReturn()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("mastermind", new FakeLocation());
		BaseUserCommand fakeCommand = new Return(fakePlayerSender, "return");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoReturnException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserReturnExecutePlayerDying()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		Location returnLocation = xTeam.tm.getTeam("one").getHeadquarters();
		Data.returnLocations.put(fakePlayerSender, returnLocation);
		fakePlayerSender.setNoDamageTicks(1);
		BaseUserCommand fakeCommand = new Return(fakePlayerSender, "return");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerDyingException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserReturnExecutePlayerNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		Location returnLocation = xTeam.tm.getTeam("one").getHeadquarters();
		Data.returnLocations.put(fakePlayerSender, returnLocation);
		BaseUserCommand fakeCommand = new Return(fakePlayerSender, "return");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserReturnExecuteRecentAttacked()
	{
		//ASSEMBLE
		Data.LAST_ATTACKED_DELAY = 15;
		Data.lastAttacked.put("protocos", System.currentTimeMillis());
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		Location returnLocation = xTeam.tm.getTeam("one").getHeadquarters();
		Data.returnLocations.put(fakePlayerSender, returnLocation);
		BaseUserCommand fakeCommand = new Return(fakePlayerSender, "return");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleException("Player was attacked in the last 15 seconds\nYou must wait 15 more seconds")).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserReturnExecuteRecentRequest()
	{
		//ASSEMBLE
		Data.taskIDs.put("protocos", null);
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		Location before = fakePlayerSender.getLocation();
		Location returnLocation = xTeam.tm.getTeam("one").getHeadquarters();
		Data.returnLocations.put(fakePlayerSender, returnLocation);
		BaseUserCommand fakeCommand = new Return(fakePlayerSender, "return");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerTeleRequestException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals(before, fakePlayerSender.getLocation());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
		Data.returnLocations.clear();
	}
}
