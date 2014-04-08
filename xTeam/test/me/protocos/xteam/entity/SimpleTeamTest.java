package me.protocos.xteam.entity;

import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.NullHeadquarters;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleTeamTest
{
	ITeam team;

	@Before
	public void setup()
	{
		team = new SimpleTeam.Builder("name").build();
	}

	@Test
	public void ShouldBeSelfReference()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(team, team.getTeam());
	}

	@Test
	public void ShouldBeHasTeam()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertTrue(team.hasTeam());
	}

	@Test
	public void ShouldBeOnSameTeam()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertTrue(team.isOnSameTeam(team));
	}

	@Test
	public void ShouldBeOnline()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertTrue(team.isOnline());
	}

	@Test
	public void ShouldBeNotVulnerable()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertFalse(team.isVulnerable());
	}

	@Test
	public void ShouldBeGetName()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals("name", team.getName());
	}

	@Test
	public void ShouldBeIsEmpty()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").build();
		//ACT
		//ASSERT
		Assert.assertTrue(team.isEmpty());
	}

	@Test
	public void ShouldBeContainsTeammates()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").players("protocos", "kmlanglois").build();
		//ACT
		//ASSERT
		Assert.assertTrue(team.containsPlayer("protocos"));
		Assert.assertTrue(team.containsPlayer("kmlanglois"));
	}

	@Test
	public void ShouldBeContainsAdmins()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").admins("protocos", "kmlanglois").build();
		//ACT
		//ASSERT
		Assert.assertTrue(team.containsPlayer("protocos"));
		Assert.assertTrue(team.containsPlayer("kmlanglois"));
	}

	@Test
	public void ShouldBeContainsLeader()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").leader("protocos").build();
		//ACT
		//ASSERT
		Assert.assertTrue(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeContainsIgnoreCase()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").leader("protocos").admins("kmlanglois").players("mastermind").build();
		//ACT
		//ASSERT
		Assert.assertTrue(team.containsPlayer("PROTOcos"));
		Assert.assertTrue(team.containsPlayer("kmLANGLOIS"));
		Assert.assertTrue(team.containsPlayer("MASTERmind"));
	}

	@Test
	public void ShouldBeNullHeadquarters()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").build();
		//ACT
		//ASSERT
		Assert.assertEquals(new NullHeadquarters(), team.getHeadquarters());
		Assert.assertFalse(team.hasHeadquarters());
	}

	@Test
	public void ShouldBeHasHeadquarters()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").headquarters(new Headquarters(null)).build();
		//ACT
		//ASSERT
		Assert.assertTrue(team.hasHeadquarters());
	}

	@Test
	public void ShouldBeHasTag()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").tag("tag").build();
		//ACT
		//ASSERT
		Assert.assertTrue(team.hasTag());
	}

	@Test
	public void ShouldBeRemovePlayer()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").players("protocos").build();
		//ACT
		team.removePlayer("protocos");
		//ASSERT
		Assert.assertFalse(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeRemoveLeader()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").leader("protocos").players("protocos").build();
		//ACT
		team.removePlayer("protocos");
		//ASSERT
		Assert.assertTrue(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeRemoveAdmin()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").admins("protocos").players("protocos").build();
		//ACT
		team.removePlayer("protocos");
		//ASSERT
		Assert.assertFalse(team.containsAdmin("protocos"));
		Assert.assertFalse(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeSetLeaderNotOnTeam()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").build();
		//ACT
		team.setLeader("protocos");
		//ASSERT
		Assert.assertFalse(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeSetLeaderNotAdmin()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").admins("protocos").players("protocos").build();
		//ACT
		team.setLeader("protocos");
		//ASSERT
		Assert.assertEquals("protocos", team.getLeader());
		Assert.assertFalse(team.containsAdmin("protocos"));
	}

	@Test
	public void ShouldBePromoteNonPlayer()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").build();
		//ACT
		team.promote("protocos");
		//ASSERT
		Assert.assertFalse(team.containsAdmin("protocos"));
		Assert.assertFalse(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBePromotePlayer()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").players("protocos").build();
		//ACT
		team.promote("protocos");
		//ASSERT
		Assert.assertTrue(team.containsAdmin("protocos"));
		Assert.assertTrue(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBePromoteAdmin()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").admins("protocos").players("protocos").build();
		//ACT
		team.promote("protocos");
		//ASSERT
		Assert.assertTrue(team.containsAdmin("protocos"));
		Assert.assertTrue(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBePromoteLeader()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").leader("protocos").players("protocos").build();
		//ACT
		team.promote("protocos");
		//ASSERT
		Assert.assertEquals("protocos", team.getLeader());
		Assert.assertFalse(team.containsAdmin("protocos"));
		Assert.assertTrue(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeDemoteNonPlayer()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").build();
		//ACT
		team.demote("protocos");
		//ASSERT
		Assert.assertFalse(team.containsAdmin("protocos"));
		Assert.assertFalse(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeDemotePlayer()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").players("protocos").build();
		//ACT
		team.demote("protocos");
		//ASSERT
		Assert.assertFalse(team.containsAdmin("protocos"));
		Assert.assertTrue(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeDemoteAdmin()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").admins("protocos").players("protocos").build();
		//ACT
		team.demote("protocos");
		//ASSERT
		Assert.assertFalse(team.containsAdmin("protocos"));
		Assert.assertTrue(team.containsPlayer("protocos"));
	}

	@Test
	public void ShouldBeDemoteLeader()
	{
		//ASSEMBLE
		team = new SimpleTeam.Builder("name").leader("protocos").players("protocos").build();
		//ACT
		team.demote("protocos");
		//ASSERT
		Assert.assertEquals("protocos", team.getLeader());
		Assert.assertFalse(team.containsAdmin("protocos"));
		Assert.assertTrue(team.containsPlayer("protocos"));
	}

	@After
	public void takedown()
	{
	}
}