package me.protocos.xteam.listener;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeProjectile;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamFriendlyFireListenerTest
{
	private TeamPlugin teamPlugin;
	private TeamFriendlyFireListener listener;
	private EntityDamageByEntityEvent event;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		listener = new TeamFriendlyFireListener(teamPlugin);
	}

	@Test
	public void ShouldBeSameTeamFriendlyFireDisabled()
	{
		//ASSEMBLE
		event = new EntityDamageByEntityEvent(FakePlayer.get("protocos"), FakePlayer.get("kmlanglois"), DamageCause.ENTITY_ATTACK, 10.0D);
		//ACT
		boolean result = listener.onEntityDamage(event);
		//ASSERT
		Assert.assertFalse(result);
	}

	@Test
	public void ShouldBeSameTeamFriendlyFireEnabled()
	{
		//ASSEMBLE
		Configuration.TEAM_FRIENDLY_FIRE = true;
		event = new EntityDamageByEntityEvent(FakePlayer.get("protocos"), FakePlayer.get("kmlanglois"), DamageCause.ENTITY_ATTACK, 10.0D);
		//ACT
		boolean result = listener.onEntityDamage(event);
		//ASSERT
		Assert.assertTrue(result);
		Configuration.TEAM_FRIENDLY_FIRE = false;
	}

	@Test
	public void ShouldBeDifferentTeamsDisabledWorld()
	{
		//ASSEMBLE
		Configuration.DISABLED_WORLDS.add("world");
		event = new EntityDamageByEntityEvent(FakePlayer.get("protocos"), FakePlayer.get("mastermind"), DamageCause.ENTITY_ATTACK, 10.0D);
		//ACT
		boolean result = listener.onEntityDamage(event);
		//ASSERT
		Assert.assertFalse(result);
		Configuration.DISABLED_WORLDS.remove("world");
	}

	@Test
	public void ShouldBeDifferentTeamProjectileDamage()
	{
		//ASSEMBLE
		Projectile projectile = new FakeProjectile(FakePlayer.get("protocos"));
		event = new EntityDamageByEntityEvent(projectile, FakePlayer.get("mastermind"), DamageCause.ENTITY_ATTACK, 10.0D);
		//ACT
		boolean result = listener.onEntityDamage(event);
		//ASSERT
		Assert.assertTrue(result);
	}

	@After
	public void takedown()
	{
	}
}
