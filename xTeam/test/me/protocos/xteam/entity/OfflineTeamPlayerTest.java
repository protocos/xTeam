package me.protocos.xteam.entity;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeOfflinePlayer;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeWorld;
import me.protocos.xteam.model.Locatable;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OfflineTeamPlayerTest
{
	private TeamPlugin teamPlugin;
	private OfflineTeamPlayer offlineTeamPlayer;
	private IPlayerFactory playerFactory;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		offlineTeamPlayer = new OfflineTeamPlayer(teamPlugin, FakeOfflinePlayer.offline("protocos"));
		playerFactory = teamPlugin.getPlayerFactory();
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeGetHealth()
	{
		//ASSEMBLE
		//ACT
		int health = offlineTeamPlayer.getHealthLevel();
		//ASSERT
		Assert.assertEquals(-1, health);
	}

	@Test
	public void ShouldBeGetHunger()
	{
		//ASSEMBLE
		//ACT
		int hunger = offlineTeamPlayer.getHungerLevel();
		//ASSERT
		Assert.assertEquals(-1, hunger);
	}

	@Test
	public void ShouldBeGetLocation()
	{
		//ASSEMBLE
		FakeLocation fakeLocation = new FakeLocation();
		offlineTeamPlayer.setLastKnownLocation(fakeLocation);
		//ACT
		//ASSERT
		Assert.assertEquals(fakeLocation, new FakeLocation(offlineTeamPlayer.getLocation()));
	}

	@Test
	public void ShouldBeGetWorld()
	{
		//ASSEMBLE
		FakeLocation fakeLocation = new FakeLocation();
		offlineTeamPlayer.setLastKnownLocation(fakeLocation);
		//ACT
		//ASSERT
		Assert.assertEquals(fakeLocation.getWorld(), offlineTeamPlayer.getWorld());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		//ACT
		ITeam team = offlineTeamPlayer.getTeam();
		//ASSERT
		Assert.assertEquals(teamCoordinator.getTeam("one"), team);
	}

	@Test
	public void ShouldBeGetTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder(teamPlugin, "test").build();
		ITeamPlayer player1 = playerFactory.getPlayer("one");
		team.addPlayer("one");
		team.addPlayer("two");
		team.addPlayer("thr");
		teamCoordinator.createTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(2, player1.getTeammates().size());
	}

	@Test
	public void ShouldBeHasPlayedBefore()
	{
		//ASSEMBLE
		offlineTeamPlayer = playerFactory.getPlayer(FakeOfflinePlayer.online("protocos"));
		//ACT
		boolean hasPlayedbefore = offlineTeamPlayer.hasPlayedBefore();
		//ASSERT
		Assert.assertTrue(hasPlayedbefore);
	}

	@Test
	public void ShouldBeHasTeam()
	{
		//ASSEMBLE
		//ACT
		boolean hasTeam = offlineTeamPlayer.hasTeam();
		//ASSERT
		Assert.assertTrue(hasTeam);
	}

	@Test
	public void ShouldBeIsOnline()
	{
		//ASSEMBLE
		offlineTeamPlayer = playerFactory.getPlayer(FakeOfflinePlayer.online("protocos"));
		//ACT
		boolean isOnline = offlineTeamPlayer.isOnline();
		//ASSERT
		Assert.assertTrue(isOnline);
	}

	@Test
	public void ShouldBeIsOnSameTeam()
	{
		//ASSEMBLE
		OfflineTeamPlayer player1 = playerFactory.getPlayer(FakeOfflinePlayer.online("protocos"));
		OfflineTeamPlayer player2 = playerFactory.getPlayer(FakeOfflinePlayer.online("kmlanglois"));
		//ACT
		boolean sameTeam = player1.isOnSameTeam(player2);
		//ASSERT
		Assert.assertEquals(true, sameTeam);
	}

	@Test
	public void ShouldBeIsOp()
	{
		//ASSEMBLE
		offlineTeamPlayer = playerFactory.getPlayer(FakeOfflinePlayer.online("protocos"));
		//ACT
		boolean isOp = offlineTeamPlayer.isOp();
		//ASSERT
		Assert.assertTrue(isOp);
	}

	@Test
	public void ShouldBeIsTeamAdmin()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").promote("protocos");
		//ACT
		boolean isTeamAdmin = offlineTeamPlayer.isAdmin();
		//ASSERT
		Assert.assertTrue(isTeamAdmin);
	}

	@Test
	public void ShouldBeIsTeamLeader()
	{
		//ASSEMBLE
		teamCoordinator.getTeam("one").setLeader("protocos");
		//ACT
		boolean isTeamLeader = offlineTeamPlayer.isLeader();
		//ASSERT
		Assert.assertTrue(isTeamLeader);
	}

	@Test
	public void ShouldBeLastPlayed()
	{
		//ASSEMBLE
		//ACT
		String lastPlayed = offlineTeamPlayer.getLastPlayed();
		//ASSERT
		Assert.assertEquals("Dec 31 @ 6:00 PM", lastPlayed);
	}

	@Test
	public void ShouldBeNotEquals()
	{
		//ASSEMBLE
		ITeamPlayer player1 = playerFactory.getPlayer(FakeOfflinePlayer.online("protocos"));
		ITeamPlayer player2 = playerFactory.getPlayer(FakeOfflinePlayer.online("kmlanglois"));
		//ACT
		boolean equals = player1.equals(player2);
		//ASSERT
		Assert.assertFalse(equals);
	}

	@Test
	public void ShouldBeOfflineTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder(teamPlugin, "test").build();
		ITeamPlayer player1 = playerFactory.getPlayer("one");
		ITeamPlayer player2 = playerFactory.getPlayer("two");
		ITeamPlayer player3 = playerFactory.getPlayer("thr");
		team.addPlayer(player1.getName());
		team.addPlayer(player2.getName());
		team.addPlayer(player3.getName());
		teamCoordinator.createTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(true, player1.getOfflineTeammates().contains(player3));
		Assert.assertEquals(1, player1.getOfflineTeammates().size());
	}

	@Test
	public void ShouldBeOnlineTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder(teamPlugin, "test").build();
		ITeamPlayer player1 = playerFactory.getPlayer("one");
		ITeamPlayer player2 = playerFactory.getPlayer("two");
		ITeamPlayer player3 = playerFactory.getPlayer("thr");
		team.addPlayer(player1.getName());
		team.addPlayer(player2.getName());
		team.addPlayer(player3.getName());
		teamCoordinator.createTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(true, player1.getOnlineTeammates().contains(player2));
		Assert.assertEquals(1, player1.getOnlineTeammates().size());
	}

	@Test
	public void ShouldBeRelativeXYZ()
	{
		//ASSEMBLE
		FakeLocation location = new FakeLocation(new FakeWorld(), 64.4, 64.6, 64.4);
		offlineTeamPlayer.setLastKnownLocation(location);
		//ACT
		double relativeX = offlineTeamPlayer.getRelativeX(), relativeY = offlineTeamPlayer.getRelativeY(), relativeZ = offlineTeamPlayer.getRelativeZ();
		//ASSERT
		Assert.assertEquals(Math.round(location.getX()), relativeX, 0);
		Assert.assertEquals(Math.round(location.getY()), relativeY, 0);
		Assert.assertEquals(Math.round(location.getZ()), relativeZ, 0);
	}

	@Test
	public void ShouldBeTeleportEntity()
	{
		//ASSEMBLE
		TeamPlayer player1 = playerFactory.getPlayer(FakePlayer.get("protocos"));
		TeamPlayer player2 = playerFactory.getPlayer(FakePlayer.get("kmlanglois"));
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
		Locatable location = new Locatable(teamPlugin, "test", new FakeLocation(64, 64, 64));
		//ACT
		boolean teleport = offlineTeamPlayer.teleportTo(location);
		//ASSERT
		Assert.assertFalse(teleport);
	}

	@Test
	public void ShouldBeLastKnownLocationIsCurrentLocation()
	{
		//ASSEMBLE
		Location location = new FakeLocation(new FakeWorld(), 0, 64, 0);
		offlineTeamPlayer = playerFactory.getPlayer(FakeOfflinePlayer.offline("protocos"));
		offlineTeamPlayer.setLastKnownLocation(location);
		//ACT
		Location lastKnownLocation = new FakeLocation(offlineTeamPlayer.getLastKnownLocation());
		//ASSERT
		Assert.assertEquals(location, lastKnownLocation);
	}

	@Test
	public void ShouldBeToString()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals("name:protocos hasPlayed:true team:ONE admin:false leader:false", offlineTeamPlayer.toString());
	}

	@After
	public void takedown()
	{
	}
}