package me.protocos.xteam.core;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.*;
import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.entity.TeamWolf;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamWolfTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeDistanceTo()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamWolf wolf = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(world, 200, 64, 0)));
		//ACT
		double distance = player.getDistanceTo(wolf);
		//ASSERT
		Assert.assertEquals(200.0D, distance, 0);
	}

	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		Location location = new FakeLocation(new FakeWorld(), 0, 64, 0);
		TeamWolf wolf1 = new TeamWolf(new FakeWolf("protocos", 20, location));
		TeamWolf wolf2 = new TeamWolf(new FakeWolf("protocos", 20, location));
		//ACT
		boolean equals = wolf1.equals(wolf2);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeGetHealth()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamWolf wolf = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		double health = wolf.getHealth();
		//ASSERT
		Assert.assertEquals(20.0, health, 0);
	}

	@Test
	public void ShouldBeGetLocation()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamWolf wolf = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		Location location = wolf.getLocation();
		//ASSERT
		Assert.assertEquals(world, location.getWorld());
		Assert.assertEquals(0.0D, location.getX(), 0);
		Assert.assertEquals(64.0D, location.getY(), 0);
		Assert.assertEquals(0.0D, location.getZ(), 0);
	}

	@Test
	public void ShouldBeGetServer()
	{
		//ASSEMBLE
		Server server = new FakeServer();
		TeamWolf wolf = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(new FakeWorld(), 0, 64, 0), server));
		//ACT
		//ASSERT
		Assert.assertEquals(server, wolf.getServer());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		TeamWolf wolf = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		ITeam team = wolf.getTeam();
		//ASSERT
		Assert.assertEquals(xTeam.getInstance().getTeamManager().getTeam("one"), team);
	}

	@Test
	public void ShouldBeGetWorld()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamWolf wolf = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		//ASSERT
		Assert.assertEquals(world, wolf.getWorld());
	}

	@Test
	public void ShouldBeHasTeam()
	{
		//ASSEMBLE
		TeamWolf wolf = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean hasTeam = wolf.hasTeam();
		//ASSERT
		Assert.assertTrue(hasTeam);
	}

	@Test
	public void ShouldBeIsOnSameTeam()
	{
		//ASSEMBLE
		TeamWolf wolf1 = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		TeamWolf wolf2 = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(new FakeWorld(), 20, 64, 0)));
		//ACT
		boolean equals = wolf1.isOnSameTeam(wolf2);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeNotEquals()
	{
		//ASSEMBLE
		Location location = new FakeLocation(new FakeWorld(), 0, 64, 0);
		TeamWolf wolf1 = new TeamWolf(new FakeWolf("protocos", 20, location));
		TeamWolf wolf2 = new TeamWolf(new FakeWolf("kmlanglois", 20, location));
		//ACT
		boolean equals = wolf1.equals(wolf2);
		//ASSERT
		Assert.assertFalse(equals);
	}

	@Test
	public void ShouldBeRelativeXYZ()
	{
		//ASSEMBLE
		Location location = new FakeLocation(new FakeWorld(), 64.4, 64.6, 64.4);
		TeamWolf wolf = new TeamWolf(new FakeWolf("protocos", 20, location));
		//ACT
		double relativeX = wolf.getRelativeX(), relativeY = wolf.getRelativeY(), relativeZ = wolf.getRelativeZ();
		//ASSERT
		Assert.assertEquals(Math.round(location.getX()), relativeX, 0);
		Assert.assertEquals(Math.round(location.getY()), relativeY, 0);
		Assert.assertEquals(Math.round(location.getZ()), relativeZ, 0);
	}

	@Test
	public void ShouldBeTeleportEntity()
	{
		//ASSEMBLE
		TeamWolf wolf1 = new TeamWolf(new FakeWolf("protocos", 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		TeamWolf wolf2 = new TeamWolf(new FakeWolf("kmlanglois", 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean teleport = wolf1.teleportTo(wolf2);
		//ASSERT
		Assert.assertTrue(teleport);
	}

	@Test
	public void ShouldBeTeleportLocaiton()
	{
		//ASSEMBLE
		Location location = new FakeLocation(new FakeWorld(), 0, 64, 0);
		TeamWolf wolf = new TeamWolf(new FakeWolf("kmlanglois", 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean teleport = wolf.teleport(location);
		//ASSERT
		Assert.assertTrue(teleport);
	}

	@After
	public void takedown()
	{
	}
}