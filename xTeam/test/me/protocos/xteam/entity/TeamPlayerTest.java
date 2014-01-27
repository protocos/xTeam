package me.protocos.xteam.entity;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.fakeobjects.*;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.entity.OfflineTeamPlayer;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.entity.TeamPlayer;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamPlayerTest
{
	TeamPlayer teamPlayer;

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
		TeamPlayer player1 = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 200, 64, 0)));
		//ACT
		double distance = player1.getDistanceTo(player2);
		//ASSERT
		Assert.assertEquals(200.0D, distance, 0);
	}

	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		TeamPlayer player1 = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		OfflineTeamPlayer player2 = xTeam.getInstance().getPlayerManager().getPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		//ACT
		boolean equals = player1.equals(player2);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeGetHealth()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		double health = teamPlayer.getHealth();
		//ASSERT
		Assert.assertEquals(20.0, health, 0);
	}

	@Test
	public void ShouldBeGetLocation()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
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
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0), server));
		//ACT
		//ASSERT
		Assert.assertEquals(server, teamPlayer.getServer());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		ITeam team = teamPlayer.getTeam();
		//ASSERT
		Assert.assertEquals(xTeam.getInstance().getTeamManager().getTeam("one"), team);
	}

	@Test
	public void ShouldBeGetTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder("test").build();
		ITeamPlayer player1 = xTeam.getInstance().getPlayerManager().getPlayer("one");
		team.addPlayer("one");
		team.addPlayer("two");
		team.addPlayer("thr");
		xTeam.getInstance().getTeamManager().createTeam(team);
		//ACT
		//ASSERT
		Assert.assertEquals(2, player1.getTeammates().size());
	}

	@Test
	public void ShouldBeGetWorld()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		//ASSERT
		Assert.assertEquals(world, teamPlayer.getWorld());
	}

	@Test
	public void ShouldBeHasPermission()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
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
		ITeamPlayer p = xTeam.getInstance().getPlayerManager().getPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		//ACT
		boolean hasPlayedbefore = p.hasPlayedBefore();
		//ASSERT
		Assert.assertTrue(hasPlayedbefore);
	}

	@Test
	public void ShouldBeHasTeam()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean hasTeam = teamPlayer.hasTeam();
		//ASSERT
		Assert.assertTrue(hasTeam);
	}

	@Test
	public void ShouldBeIsOnline()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean isOnline = teamPlayer.isOnline();
		//ASSERT
		Assert.assertTrue(isOnline);
	}

	@Test
	public void ShouldBeIsOnSameTeam()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player1 = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		//ACT
		boolean sameTeam = player1.isOnSameTeam(player2);
		//ASSERT
		Assert.assertEquals(true, sameTeam);
	}

	@Test
	public void ShouldBeIsOp()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		boolean isOp = teamPlayer.isOp();
		//ASSERT
		Assert.assertTrue(isOp);
	}

	@Test
	public void ShouldBeIsTeamAdmin()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		xTeam.getInstance().getTeamManager().getTeam("one").promote("protocos");
		//ACT
		boolean isTeamAdmin = teamPlayer.isAdmin();
		//ASSERT
		Assert.assertTrue(isTeamAdmin);
	}

	@Test
	public void ShouldBeIsTeamLeader()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		xTeam.getInstance().getTeamManager().getTeam("one").setLeader("protocos");
		//ACT
		boolean isTeamLeader = teamPlayer.isLeader();
		//ASSERT
		Assert.assertTrue(isTeamLeader);
	}

	@Test
	public void ShouldBeLastPlayed()
	{
		//ASSEMBLE
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		String lastPlayed = teamPlayer.getLastPlayed();
		//ASSERT
		Assert.assertEquals("Dec 31 @ 6:00 PM", lastPlayed);
	}

	@Test
	public void ShouldBeNotEquals()
	{
		//ASSEMBLE
		ITeamPlayer player1 = xTeam.getInstance().getPlayerManager().getPlayer(new FakeOfflinePlayer("protocos", true, true, true));
		ITeamPlayer player2 = xTeam.getInstance().getPlayerManager().getPlayer(new FakeOfflinePlayer("kmlanglois", true, true, true));
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
	//		player = xTeam.getInstance().getPlayerManager().getPlayer(offlinePlayer);
	//		//ASSERT
	//		Assert.assertEquals(offlinePlayer, player.getOfflinePlayer());
	//	}
	@Test
	public void ShouldBeOfflineTeammates()
	{
		//ASSEMBLE
		Team team = new Team.Builder("test").build();
		ITeamPlayer player1 = xTeam.getInstance().getPlayerManager().getPlayer("one");
		ITeamPlayer player2 = xTeam.getInstance().getPlayerManager().getPlayer("two");
		ITeamPlayer player3 = xTeam.getInstance().getPlayerManager().getPlayer("thr");
		team.addPlayer(player1.getName());
		team.addPlayer(player2.getName());
		team.addPlayer(player3.getName());
		xTeam.getInstance().getTeamManager().createTeam(team);
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
		ITeamPlayer player1 = xTeam.getInstance().getPlayerManager().getPlayer("one");
		ITeamPlayer player2 = xTeam.getInstance().getPlayerManager().getPlayer("two");
		ITeamPlayer player3 = xTeam.getInstance().getPlayerManager().getPlayer("thr");
		team.addPlayer(player1.getName());
		team.addPlayer(player2.getName());
		team.addPlayer(player3.getName());
		xTeam.getInstance().getTeamManager().createTeam(team);
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
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, location));
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
		FakePlayerSender fakePlayer = new FakePlayerSender("protocos", new FakeLocation(), true);
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(fakePlayer);
		//ACT
		teamPlayer.sendMessage("test message");
		//ASSERT
		Assert.assertEquals("test message", fakePlayer.getLastMessage());
	}

	@Test
	public void ShouldBeTeleportEntity()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		TeamPlayer player1 = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(world, 0, 64, 0)));
		TeamPlayer player2 = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 0, 64, 0)));
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
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("kmlanglois", true, true, 20, new FakeLocation(world, 1, 64, 1)));
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
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(new FakePlayer("protocos", true, true, 20, new FakeLocation(new FakeWorld(), 0, 64, 0)));
		//ACT
		//ASSERT
		Assert.assertEquals("name:protocos hasPlayed:true team:ONE admin:false leader:false", teamPlayer.toString());
	}

	@After
	public void takedown()
	{
	}
}