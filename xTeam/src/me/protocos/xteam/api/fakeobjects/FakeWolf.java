package me.protocos.xteam.api.fakeobjects;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class FakeWolf implements Wolf
{
	private String owner;
	private double health;
	private Location location;
	private Server server;

	public FakeWolf(String owner, int health, Location location)
	{
		this(owner, health, location, new FakeServer());
	}
	public FakeWolf(String owner, int health, Location location, Server server)
	{
		this.owner = owner;
		this.health = health;
		this.location = location;
		this.server = server;
	}
	@Override
	@Deprecated
	public void _INVALID_damage(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void _INVALID_damage(int arg0, Entity arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public int _INVALID_getHealth()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public int _INVALID_getLastDamage()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public int _INVALID_getMaxHealth()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public void _INVALID_setHealth(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void _INVALID_setLastDamage(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void _INVALID_setMaxHealth(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addPotionEffect(PotionEffect arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPotionEffect(PotionEffect arg0, boolean arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBreed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void damage(double arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void damage(double arg0, Entity arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean eject()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAge()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getAgeLock()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanPickupItems()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DyeColor getCollarColor()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEntityId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityEquipment getEquipment()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getEyeHeight()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEyeHeight(boolean arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location getEyeLocation()
	{
		// TODO Auto-generated method stub
		return null;
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
	public double getHealth()
	{
		return health;
	}

	@Override
	public Player getKiller()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getLastDamage()
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
	public List<Block> getLastTwoTargetBlocks(HashSet<Byte> arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Block> getLineOfSight(HashSet<Byte> arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation()
	{
		return location;
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
	public double getMaxHealth()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumAir()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumNoDamageTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNoDamageTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AnimalTamer getOwner()
	{
		return new FakeAnimalTamer(owner);
	}

	@Override
	public Entity getPassenger()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemainingAir()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getRemoveWhenFarAway()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Server getServer()
	{
		return server;
	}

	@Override
	public LivingEntity getTarget()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getTargetBlock(HashSet<Byte> arg0, int arg1)
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
		return location.getWorld();
	}

	@Override
	public boolean hasLineOfSight(Entity arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasMetadata(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPotionEffect(PotionEffectType arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdult()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAngry()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCustomNameVisible()
	{
		// TODO Auto-generated method stub
		return false;
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
	public boolean isSitting()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTamed()
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
	public <T extends Projectile> T launchProjectile(Class<? extends T> arg0)
	{
		// TODO Auto-generated method stub
		return null;
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
	public void removeMetadata(String arg0, Plugin arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removePotionEffect(PotionEffectType arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resetMaxHealth()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setAdult()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setAge(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setAgeLock(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setAngry(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBaby()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBreed(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCanPickupItems(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCollarColor(DyeColor arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCustomName(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCustomNameVisible(boolean arg0)
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
	public void setHealth(double health)
	{
		this.health = health;
	}

	@Override
	public void setLastDamage(double arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastDamageCause(EntityDamageEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaxHealth(double arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaximumAir(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaximumNoDamageTicks(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setNoDamageTicks(int arg0)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void setOwner(AnimalTamer arg0)
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
	public void setRemainingAir(int arg0)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void setRemoveWhenFarAway(boolean arg0)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void setSitting(boolean arg0)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void setTamed(boolean arg0)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void setTarget(LivingEntity arg0)
	{
		// TODO Auto-generated method stub

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
	@Deprecated
	public Arrow shootArrow()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean teleport(Entity arg0)
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
	public boolean teleport(Location arg0)
	{
		return true;
	}
	@Override
	public boolean teleport(Location arg0, TeleportCause arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	@Deprecated
	public Egg throwEgg()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@Deprecated
	public Snowball throwSnowball()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Entity getLeashHolder() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isLeashed()
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean setLeashHolder(Entity arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
