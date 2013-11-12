package me.protocos.xteam.core;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.api.fakeobjects.*;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamPlayerTest
{
	TeamPlayer player;

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
		TeamPlayer player1 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 200, 64, 0)));
		//ACT
		double distance = player1.getDistanceTo(player2);
		//ASSERT
		Assert.assertEquals(200.0D, distance, 0);
	}

	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		TeamPlayer player1 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		OfflineTeamPlayer player2 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		//ACT
		boolean equals = player1.equals(player2);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeGetHealth()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		double health = player.getHealth();
		//ASSERT
		Assert.assertEquals(20.0, health, 0);
	}

	@Test
	public void ShouldBeGetLocation()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		Location location = player.getLocation();
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
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0), server));
		//ACT
		//ASSERT
		Assert.assertEquals(server, player.getServer());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		Team team = player.getTeam();
		//ASSERT
		Assert.assertEquals(xTeamPlugin.getInstance().getTeamManager().getTeam("one"), team);
	}

	@Test
	public void ShouldBeGetTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder("test").build();
		ITeamPlayer player1 = xTeamPlugin.getInstance().getPlayerManager().getPlayer("one");
		team.addPlayer("one");
		team.addPlayer("two");
		team.addPlayer("thr");
		xTeamPlugin.getInstance().getTeamManager().addTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(2, player1.getTeammates().size());
	}

	@Test
	public void ShouldBeGetWorld()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		//ASSERT
		Assert.assertEquals(world, player.getWorld());
	}

	@Test
	public void ShouldBeHasPermission()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean hasPermission = player.hasPermission("test");
		//ASSERT
		Assert.assertTrue(hasPermission);
	}

	@Test
	public void ShouldBeHasPlayedBefore()
	{
		//ASSEMBLE
		ITeamPlayer p = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		//ACT
		boolean hasPlayedbefore = p.hasPlayedBefore();
		//ASSERT
		Assert.assertTrue(hasPlayedbefore);
	}

	@Test
	public void ShouldBeHasTeam()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean hasTeam = player.hasTeam();
		//ASSERT
		Assert.assertTrue(hasTeam);
	}

	@Test
	public void ShouldBeIsOnline()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean isOnline = player.isOnline();
		//ASSERT
		Assert.assertTrue(isOnline);
	}

	@Test
	public void ShouldBeIsOnSameTeam()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player1 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		boolean sameTeam = player1.isOnSameTeam(player2);
		//ASSERT
		Assert.assertEquals(true, sameTeam);
	}

	@Test
	public void ShouldBeIsOp()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean isOp = player.isOp();
		//ASSERT
		Assert.assertTrue(isOp);
	}

	@Test
	public void ShouldBeIsTeamAdmin()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		xTeamPlugin.getInstance().getTeamManager().getTeam("one").promote("protocos");
		//ACT
		boolean isTeamAdmin = player.isAdmin();
		//ASSERT
		Assert.assertTrue(isTeamAdmin);
	}

	@Test
	public void ShouldBeIsTeamLeader()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		xTeamPlugin.getInstance().getTeamManager().getTeam("one").setLeader("protocos");
		//ACT
		boolean isTeamLeader = player.isLeader();
		//ASSERT
		Assert.assertTrue(isTeamLeader);
	}

	@Test
	public void ShouldBeLastPlayed()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		String lastPlayed = player.getLastPlayed();
		//ASSERT
		Assert.assertEquals("Dec 31 @ 6:00 PM", lastPlayed);
	}

	@Test
	public void ShouldBeNotEquals()
	{
		//ASSEMBLE
		ITeamPlayer player1 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		ITeamPlayer player2 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakeOfflinePlayer("kmlanglois", true, true, true));
		//ACT
		boolean equals = player1.equals(player2);
		//ASSERT
		Assert.assertFalse(equals);
	}

	//	@Test
	//	public void ShouldBeOfflinePlayer()
	//	{
	//		//ASSEMBLE
	//		OfflinePlayer offlinePlayer = new FakeOfflinePlayer("protocos");
	//		//ACT
	//		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(offlinePlayer);
	//		//ASSERT
	//		Assert.assertEquals(offlinePlayer, player.getOfflinePlayer());
	//	}
	@Test
	public void ShouldBeOfflineTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder("test").build();
		ITeamPlayer player1 = xTeamPlugin.getInstance().getPlayerManager().getPlayer("one");
		ITeamPlayer player2 = xTeamPlugin.getInstance().getPlayerManager().getPlayer("two");
		ITeamPlayer player3 = xTeamPlugin.getInstance().getPlayerManager().getPlayer("thr");
		team.addPlayer(player1.getName());
		team.addPlayer(player2.getName());
		team.addPlayer(player3.getName());
		xTeamPlugin.getInstance().getTeamManager().addTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(true, player1.getOfflineTeammates().contains(player3));
		Assert.assertEquals(1, player1.getOfflineTeammates().size());
	}

	@Test
	public void ShouldBeOnlineTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder("test").build();
		ITeamPlayer player1 = xTeamPlugin.getInstance().getPlayerManager().getPlayer("one");
		ITeamPlayer player2 = xTeamPlugin.getInstance().getPlayerManager().getPlayer("two");
		ITeamPlayer player3 = xTeamPlugin.getInstance().getPlayerManager().getPlayer("thr");
		team.addPlayer(player1.getName());
		team.addPlayer(player2.getName());
		team.addPlayer(player3.getName());
		xTeamPlugin.getInstance().getTeamManager().addTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(true, player1.getOnlineTeammates().contains(player2));
		Assert.assertEquals(1, player1.getOnlineTeammates().size());
	}

	@Test
	public void ShouldBeRelativeXYZ()
	{
		//ASSEMBLE
		Location location = new FakeLocation(new FakeWorld(), 64.4, 64.6, 64.4);
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, location));
		//ACT
		double relativeX = player.getRelativeX(), relativeY = player.getRelativeY(), relativeZ = player.getRelativeZ();
		//ASSERT
		Assert.assertEquals(Math.round(location.getX()), relativeX, 0);
		Assert.assertEquals(Math.round(location.getY()), relativeY, 0);
		Assert.assertEquals(Math.round(location.getZ()), relativeZ, 0);
	}

	@Test
	public void ShouldBeSendMessage()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean sendMessage = player.sendMessage("test message");
		//ASSERT
		Assert.assertEquals(true, sendMessage);
	}

	@Test
	public void ShouldBeTeleportEntity()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player1 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		boolean teleport = player1.teleportTo(player2);
		//ASSERT
		Assert.assertTrue(teleport);
		Assert.assertEquals(player2.getLocation(), player1.getLocation());
	}

	@Test
	public void ShouldBeTeleportLocation()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		Location location = new FakeLocation(world, 0, 64, 0);
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 1, 64, 1)));
		//ACT
		boolean teleport = player.teleport(location);
		//ASSERT
		Assert.assertTrue(teleport);
		Assert.assertEquals(location, player.getLocation());
	}

	@Test
	public void ShouldBeToString()
	{
		//ASSEMBLE
		player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		//ASSERT
		Assert.assertEquals("name:protocos hasPlayed:true team:ONE admin:false leader:false", player.toString());
	}

	@After
	public void takedown()
	{
	}
}