package me.protocos.xteam.listener;

import java.util.List;
import java.util.UUID;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.fakeobjects.FakePlayer;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
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

class FakeProjectile implements Projectile
{
	private LivingEntity livingEntity;

	public FakeProjectile(LivingEntity livingEntity)
	{
		this.livingEntity = livingEntity;
	}

	@Override
	public boolean eject()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getEntityId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFallDistance()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFireTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation(Location arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFireTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getPassenger()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server getServer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTicksLived()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityType getType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getUniqueId()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getVehicle()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDead()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInsideVehicle()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOnGround()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leaveVehicle()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void playEffect(EntityEffect arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void remove()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFallDistance(float arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFireTicks(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastDamageCause(EntityDamageEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setPassenger(Entity arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTicksLived(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setVelocity(Vector arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean teleport(Location arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(Entity arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(Location arg0, TeleportCause arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(Entity arg0, TeleportCause arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasMetadata(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean doesBounce()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LivingEntity getShooter()
	{
		return this.livingEntity;
	}

	@Override
	public void setBounce(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setShooter(LivingEntity arg0)
	{
		this.livingEntity = arg0;
	}
}
