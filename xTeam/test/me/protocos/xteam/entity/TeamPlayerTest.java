package me.protocos.xteam.entity;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.fakeobjects.*;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamPlayerTest
{
	private TeamPlugin teamPlugin;
	private IPlayerFactory playerFactory;
	private ITeamCoordinator teamCoordinator;
	private TeamPlayer teamPlayer;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		playerFactory = teamPlugin.getPlayerFactory();
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeDistanceTo()
	{
		//ASSEMBLE
		TeamPlayer player1 = playerFactory.getPlayer(FakePlayer.get("protocos"));
		TeamPlayer player2 = playerFactory.getPlayer(new FakePlayer.Builder().name("kmlanglois").location(new FakeLocation(200, 64, 0)).build());
		//ACT
		double distance = player1.getDistanceTo(player2);
		//ASSERT
		Assert.assertEquals(200.0D, distance, 0);
	}

	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		TeamPlayer player1 = playerFactory.getPlayer(FakePlayer.get("protocos"));
		OfflineTeamPlayer player2 = playerFactory.getPlayer(FakeOfflinePlayer.online("protocos"));
		//ACT
		boolean equals = player1.equals(player2);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeGetHealth()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		int health = teamPlayer.getHealthLevel();
		//ASSERT
		Assert.assertEquals(100, health);
	}

	@Test
	public void ShouldBeGetLocation()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		Location location = teamPlayer.getLocation();
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
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		//ASSERT
		Assert.assertEquals(server, teamPlayer.getServer());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
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
		World world = new FakeWorld();
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		//ASSERT
		Assert.assertEquals(world, teamPlayer.getWorld());
	}

	@Test
	public void ShouldBeHasPermission()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
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
		Assert.assertTrue(hasPermission);
	}

	@Test
	public void ShouldBeHasPlayedBefore()
	{
		//ASSEMBLE
		ITeamPlayer p = playerFactory.getPlayer(FakeOfflinePlayer.online("protocos"));
		//ACT
		boolean hasPlayedbefore = p.hasPlayedBefore();
		//ASSERT
		Assert.assertTrue(hasPlayedbefore);
	}

	@Test
	public void ShouldBeHasTeam()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		boolean hasTeam = teamPlayer.hasTeam();
		//ASSERT
		Assert.assertTrue(hasTeam);
	}

	@Test
	public void ShouldBeIsOnline()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		boolean isOnline = teamPlayer.isOnline();
		//ASSERT
		Assert.assertTrue(isOnline);
	}

	@Test
	public void ShouldBeIsOnSameTeam()
	{
		//ASSEMBLE
		TeamPlayer player1 = playerFactory.getPlayer(FakePlayer.get("protocos"));
		TeamPlayer player2 = playerFactory.getPlayer(FakePlayer.get("kmlanglois"));
		//ACT
		boolean sameTeam = player1.isOnSameTeam(player2);
		//ASSERT
		Assert.assertEquals(true, sameTeam);
	}

	@Test
	public void ShouldBeIsOp()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		boolean isOp = teamPlayer.isOp();
		//ASSERT
		Assert.assertTrue(isOp);
	}

	@Test
	public void ShouldBeIsTeamAdmin()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
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
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
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
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		String lastPlayed = teamPlayer.getLastPlayed();
		//ASSERT
		Assert.assertEquals("Dec 31 @ 6:00 PM", lastPlayed);
	}

	@Test
	public void ShouldBeNotEquals()
	{
		//ASSEMBLE
		ITeamPlayer player1 = playerFactory.getPlayer(FakePlayer.get("protocos"));
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
		Location location = new FakeLocation(new FakeWorld(), 64.4, 64.6, 64.4);
		teamPlayer = playerFactory.getPlayer(new FakePlayer.Builder().name("protocos").location(location).build());
		//ACT
		double relativeX = teamPlayer.getRelativeX(), relativeY = teamPlayer.getRelativeY(), relativeZ = teamPlayer.getRelativeZ();
		//ASSERT
		Assert.assertEquals(Math.round(location.getX()), relativeX, 0);
		Assert.assertEquals(Math.round(location.getY()), relativeY, 0);
		Assert.assertEquals(Math.round(location.getZ()), relativeZ, 0);
	}

	@Test
	public void ShouldBeSendMessage()
	{
		//ASSEMBLE
		FakePlayer fakePlayer = FakePlayer.get("protocos");
		teamPlayer = playerFactory.getPlayer(fakePlayer);
		//ACT
		teamPlayer.sendMessage("test message");
		//ASSERT
		Assert.assertEquals("test message", fakePlayer.getLastMessages());
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
		World world = new FakeWorld();
		Location location = new FakeLocation(world, 0, 64, 0);
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("kmlanglois"));
		//ACT
		boolean teleport = teamPlayer.teleport(location);
		//ASSERT
		Assert.assertTrue(teleport);
		Assert.assertEquals(location, teamPlayer.getLocation());
	}

	@Test
	public void ShouldBeToString()
	{
		//ASSEMBLE
		teamPlayer = playerFactory.getPlayer(FakePlayer.get("protocos"));
		//ACT
		//ASSERT
		Assert.assertEquals("name:protocos hasPlayed:true team:ONE admin:false leader:false", teamPlayer.toString());
	}

	@After
	public void takedown()
	{
	}
}