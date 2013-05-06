package me.protocos.xteam.core.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import static org.mockito.Mockito.when;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.testing.*;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
	public void ShouldBePlayerFromName()
	{
		//ASSEMBLE
		//ACT
		player = new TeamPlayer("protocos");
		//ASSERT
		Assert.assertEquals("protocos", player.getName());
	}
	@Test
	public void ShouldBeOfflinePlayer()
	{
		//ASSEMBLE
		OfflinePlayer offlinePlayer = new FakeOfflinePlayer("protocos");
		//ACT
		player = new TeamPlayer(offlinePlayer);
		//ASSERT
		Assert.assertEquals(offlinePlayer, player.getOfflinePlayer());
	}
	@Test
	public void ShouldBeOnlinePlayer()
	{
		//ASSEMBLE
		Player onlinePlayer = new FakePlayer("protocos");
		//ACT
		player = new TeamPlayer(onlinePlayer);
		//ASSERT
		Assert.assertEquals(onlinePlayer, player.getOnlinePlayer());
	}
	@Test
	public void ShouldBeGetLocation()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		Location location = player.getLocation();
		//ASSERT
		Assert.assertEquals(world, location.getWorld());
		Assert.assertEquals(0.0D, location.getX(), 0);
		Assert.assertEquals(64.0D, location.getY(), 0);
		Assert.assertEquals(0.0D, location.getZ(), 0);
	}
	@Test
	public void ShouldBeDistanceTo()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player1 = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = new TeamPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 200, 64, 0)));
		//ACT
		double distance = player1.getDistanceTo(player2);
		//ASSERT
		Assert.assertEquals(200.0D, distance, 0);
	}
	@Test
	public void ShouldBeGetHealth()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		int health = player.getHealth();
		//ASSERT
		Assert.assertEquals(20, health);
	}
	@Test
	public void ShouldBeLastPlayed()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		String lastPlayed = player.getLastPlayed();
		//ASSERT
		Assert.assertEquals("Dec 31 @ 6:00 PM", lastPlayed);
	}
	@Test
	public void ShouldBeRelativeXYZ()
	{
		//ASSEMBLE
		Location location = new FakeLocation(new FakeWorld(), 64.4, 64.6, 64.4);
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, location));
		//ACT
		double relativeX = player.getRelativeX(), relativeY = player.getRelativeY(), relativeZ = player.getRelativeZ();
		//ASSERT
		Assert.assertEquals(Math.round(location.getX()), relativeX, 0);
		Assert.assertEquals(Math.round(location.getY()), relativeY, 0);
		Assert.assertEquals(Math.round(location.getZ()), relativeZ, 0);
	}
	@Test
	public void ShouldBeGetServer()
	{
		//ASSEMBLE
		Server server = new FakeServer();
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0), server));
		//ACT
		//ASSERT
		Assert.assertEquals(server, player.getServer());
	}
	@Test
	public void ShouldBeGetWorld()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		//ASSERT
		Assert.assertEquals(world, player.getWorld());
	}
	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		Team team = player.getTeam();
		//ASSERT
		Assert.assertEquals(xTeam.tm.getTeam("one"), team);
	}
	@Test
	public void ShouldBeHasTeam()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean hasTeam = player.hasTeam();
		//ASSERT
		Assert.assertTrue(hasTeam);
	}
	@Test
	public void ShouldBeHasPermission()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		//ACT
		boolean hasPermission = player.hasPermission("test");
		//ASSERT
		Assert.assertTrue(hasPermission);
	}
	@Test
	public void ShouldBeHasPlayedBefore()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		//ACT
		boolean hasPlayedbefore = player.hasPlayedBefore();
		//ASSERT
		Assert.assertTrue(hasPlayedbefore);
	}
	@Test
	public void ShouldBeIsOnline()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
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
		TeamPlayer player1 = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = new TeamPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		boolean sameTeam = player1.isOnSameTeam(player2);
		//ASSERT
		Assert.assertEquals(true, sameTeam);
	}
	@Test
	public void ShouldBeIsOp()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean isOp = player.isOp();
		//ASSERT
		Assert.assertTrue(isOp);
	}
	@Test
	public void ShouldBeIsTeamAdmin()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		xTeam.tm.getTeam("one").promote("protocos");
		//ACT
		boolean isTeamAdmin = player.isAdmin();
		//ASSERT
		Assert.assertTrue(isTeamAdmin);
	}
	@Test
	public void ShouldBeIsTeamLeader()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		xTeam.tm.getTeam("one").setLeader("protocos");
		//ACT
		boolean isTeamLeader = player.isLeader();
		//ASSERT
		Assert.assertTrue(isTeamLeader);
	}
	@Test
	public void ShouldBeSendMessage()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean sendMessage = player.sendMessage("test message");
		//ASSERT
		Assert.assertEquals(true, sendMessage);
	}
	@Test
	public void ShouldBeTeleportLocation()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		Location location = new FakeLocation(world, 0, 64, 0);
		player = new TeamPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		boolean teleport = player.teleport(location);
		//ASSERT
		Assert.assertEquals(true, teleport);
	}
	@Test
	public void ShouldBeTeleportEntity()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player1 = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = new TeamPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		boolean teleport = player1.teleport(player2);
		//ASSERT
		Assert.assertEquals(true, teleport);
	}
	@Test
	public void ShouldBeGetTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder("test").build();
		TeamPlayer player1 = new TeamPlayer("one");
		team.addPlayer("one");
		team.addPlayer("two");
		team.addPlayer("thr");
		xTeam.tm.addTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(true, player1.getTeammates().contains("two"));
		Assert.assertEquals(true, player1.getTeammates().contains("thr"));
		Assert.assertEquals(2, player1.getTeammates().size());
	}
	@Test
	public void ShouldBeOfflineTeammates()
	{
		//ASSEMBLE
		OfflinePlayer p1 = new FakeOfflinePlayer("one", true, true, true);
		OfflinePlayer p2 = new FakeOfflinePlayer("two", true, true, true);
		OfflinePlayer p3 = new FakeOfflinePlayer("thr", true, false, true);
		when(Data.BUKKIT.getOfflinePlayer("one")).thenReturn(p1);
		when(Data.BUKKIT.getOfflinePlayer("two")).thenReturn(p2);
		when(Data.BUKKIT.getOfflinePlayer("thr")).thenReturn(p3);
		Team team = new Team.Builder("test").build();
		TeamPlayer player1 = new TeamPlayer(p1);
		TeamPlayer player2 = new TeamPlayer(p2);
		TeamPlayer player3 = new TeamPlayer(p3);
		team.addPlayer(player1.getName());
		team.addPlayer(player2.getName());
		team.addPlayer(player3.getName());
		xTeam.tm.addTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(true, player1.getOfflineTeammates().contains("thr"));
		Assert.assertEquals(1, player1.getOfflineTeammates().size());
	}
	@Test
	public void ShouldBeOnlineTeammates()
	{
		//ASSEMBLE
		OfflinePlayer p1 = new FakeOfflinePlayer("one", true, true, true);
		OfflinePlayer p2 = new FakeOfflinePlayer("two", true, true, true);
		OfflinePlayer p3 = new FakeOfflinePlayer("thr", true, false, true);
		when(Data.BUKKIT.getOfflinePlayer("one")).thenReturn(p1);
		when(Data.BUKKIT.getOfflinePlayer("two")).thenReturn(p2);
		when(Data.BUKKIT.getOfflinePlayer("thr")).thenReturn(p3);
		Team team = new Team.Builder("test").build();
		TeamPlayer player1 = new TeamPlayer(p1);
		TeamPlayer player2 = new TeamPlayer(p2);
		TeamPlayer player3 = new TeamPlayer(p3);
		team.addPlayer(player1.getName());
		team.addPlayer(player2.getName());
		team.addPlayer(player3.getName());
		xTeam.tm.addTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(true, player1.getOnlineTeammates().contains("two"));
		Assert.assertEquals(1, player1.getOnlineTeammates().size());
	}
	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		TeamPlayer player1 = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		TeamPlayer player2 = new TeamPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		//ACT
		boolean equals = player1.equals(player2);
		//ASSERT
		Assert.assertTrue(equals);
	}
	@Test
	public void ShouldBeNotEquals()
	{
		//ASSEMBLE
		TeamPlayer player1 = new TeamPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		TeamPlayer player2 = new TeamPlayer(new FakeOfflinePlayer("kmlanglois", true, true, true));
		//ACT
		boolean equals = player1.equals(player2);
		//ASSERT
		Assert.assertFalse(equals);
	}
	@Test
	public void ShouldBeToString()
	{
		//ASSEMBLE
		player = new TeamPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		//ASSERT
		Assert.assertEquals("name:protocos hasPlayed:true team:ONE admin:false leader:false", player.toString());
	}
	@After
	public void takedown()
	{
	}
}