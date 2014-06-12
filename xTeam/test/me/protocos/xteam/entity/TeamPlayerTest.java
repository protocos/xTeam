package me.protocos.xteam.entity;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeOfflinePlayer;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeWorld;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamPlayerTest
{
	private TeamPlugin teamPlugin = FakeXTeam.asTeamPlugin();
	private IPlayerFactory playerFactory;
	private ITeamCoordinator teamCoordinator;
	private OfflineTeamPlayer teamPlayer;

	@Before
	public void setup()
	{
		playerFactory = teamPlugin.getPlayerFactory();
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		TeamPlayer player1 = playerFactory.getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation()));
		OfflineTeamPlayer player2 = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		boolean equals = player2.equals(player1);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeGetLocationWhileOnline()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		//ACT
		Location location = teamPlayer.getLocation();
		//ASSERT
		Assert.assertEquals(world, location.getWorld());
		Assert.assertEquals(0.0D, location.getX(), 0);
		Assert.assertEquals(64.0D, location.getY(), 0);
		Assert.assertEquals(0.0D, location.getZ(), 0);
	}

	@Test
	public void ShouldBeGetLocationWhileOffline()
	{
		//ASSEMBLE
		Location fakeLocation = new FakeLocation();
		playerFactory.getPlayer("protocos").setLastKnownLocation(fakeLocation);
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		Location location = new FakeLocation(teamPlayer.getLocation());
		//ASSERT
		Assert.assertEquals(fakeLocation, location);
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		ITeam team = teamPlayer.getTeam();
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
	public void ShouldBeGetWorld()
	{
		//ASSEMBLE
		Location fakeLocation = new FakeLocation();
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		teamPlayer.setLastKnownLocation(fakeLocation);
		//ACT
		//ASSERT
		Assert.assertEquals(fakeLocation.getWorld(), teamPlayer.getWorld());
	}

	@Test
	public void ShouldBeHasPermission()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		boolean hasPermission = teamPlayer.hasPermission(new IPermissible()
		{
			@Override
			public String getPermissionNode()
			{
				return "test";
			}
		});
		//ASSERT
		Assert.assertFalse(hasPermission);
	}

	@Test
	public void ShouldBeHasPlayedBefore()
	{
		//ASSEMBLE
		ITeamPlayer p = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		boolean hasPlayedbefore = p.hasPlayedBefore();
		//ASSERT
		Assert.assertTrue(hasPlayedbefore);
	}

	@Test
	public void ShouldBeHasTeam()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		boolean hasTeam = teamPlayer.hasTeam();
		//ASSERT
		Assert.assertTrue(hasTeam);
	}

	@Test
	public void ShouldBeIsOnline()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		boolean isOnline = teamPlayer.isOnline();
		//ASSERT
		Assert.assertFalse(isOnline);
	}

	@Test
	public void ShouldBeIsOnSameTeam()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player1 = playerFactory.getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = playerFactory.getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		boolean sameTeam = player1.isOnSameTeam(player2);
		//ASSERT
		Assert.assertEquals(true, sameTeam);
	}

	@Test
	public void ShouldBeIsOp()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		boolean isOp = teamPlayer.isOp();
		//ASSERT
		Assert.assertTrue(isOp);
	}

	@Test
	public void ShouldBeIsTeamAdmin()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		teamCoordinator.getTeam("one").promote("protocos");
		//ACT
		boolean isTeamAdmin = teamPlayer.isAdmin();
		//ASSERT
		Assert.assertTrue(isTeamAdmin);
	}

	@Test
	public void ShouldBeIsTeamLeader()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		teamCoordinator.getTeam("one").setLeader("protocos");
		//ACT
		boolean isTeamLeader = teamPlayer.isLeader();
		//ASSERT
		Assert.assertTrue(isTeamLeader);
	}

	@Test
	public void ShouldBeLastPlayed()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		String lastPlayed = teamPlayer.getLastPlayed();
		//ASSERT
		Assert.assertEquals("Dec 31 @ 6:00 PM", lastPlayed);
	}

	@Test
	public void ShouldBeNotEquals()
	{
		//ASSEMBLE
		ITeamPlayer player1 = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		ITeamPlayer player2 = playerFactory.getPlayer(new FakeOfflinePlayer("kmlanglois", true, false, true));
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
		Location location = new FakeLocation(new FakeWorld(), 64.4, 64.6, 64.4);
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		teamPlayer.setLastKnownLocation(location);
		//ACT
		double relativeX = teamPlayer.getRelativeX(), relativeY = teamPlayer.getRelativeY(), relativeZ = teamPlayer.getRelativeZ();
		//ASSERT
		Assert.assertEquals(Math.round(location.getX()), relativeX, 0);
		Assert.assertEquals(Math.round(location.getY()), relativeY, 0);
		Assert.assertEquals(Math.round(location.getZ()), relativeZ, 0);
	}

	@Test
	public void ShouldBeTeleportEntity()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player1 = playerFactory.getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = playerFactory.getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		boolean teleport = player1.teleportTo(player2);
		//ASSERT
		Assert.assertTrue(teleport);
		Assert.assertEquals(player2.getLocation(), player1.getLocation());
	}

	@Test
	public void ShouldBeTeleport()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("kmlanglois", true, false, true));
		//ACT
		boolean teleport = teamPlayer.teleportTo(playerFactory.getPlayer("protocos"));
		//ASSERT
		Assert.assertFalse(teleport);
	}

	@Test
	public void ShouldBeLastKnownLocationIsCurrentLocation()
	{
		//ASSEMBLE
		Location location = new FakeLocation(new FakeWorld(), 0, 64, 0);
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("kmlanglois", true, false, true));
		teamPlayer.setLastKnownLocation(location);
		//ACT
		Location lastKnownLocation = new FakeLocation(teamPlayer.getLastKnownLocation());
		//ASSERT
		Assert.assertEquals(location, lastKnownLocation);
	}

	@Test
	public void ShouldBeToString()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(new FakeOfflinePlayer("protocos", true, false, true));
		//ACT
		//ASSERT
		Assert.assertEquals("name:protocos hasPlayed:true team:ONE admin:false leader:false", teamPlayer.toString());
	}

	@After
	public void takedown()
	{
	}
}