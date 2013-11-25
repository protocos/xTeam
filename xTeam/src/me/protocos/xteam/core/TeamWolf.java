package me.protocos.xteam.core;

import java.util.List;
import java.util.UUID;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.*;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class TeamWolf implements ITeamWolf
{
	Wolf wolf;

	public TeamWolf(Wolf w)
	{
		this.wolf = w;
	}

	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof TeamWolf))
			return false;

		TeamWolf rhs = (TeamWolf) obj;
		return new EqualsBuilder().append(getOwner(), rhs.getOwner()).isEquals();
	}

	@Override
	public double getDistanceTo(ILocatable entity)
	{
		return getLocation().distance(entity.getLocation());
	}

	@Override
	public double getHealth()
	{
		return wolf.getHealth();
	}

	@Override
	public Location getLocation()
	{
		return wolf.getLocation();
	}

	@Override
	public ITeamPlayer getOwner()
	{
		if (wolf.getOwner() != null)
			return xTeam.getInstance().getPlayerManager().getPlayer(wolf.getOwner().getName());
		return null;
	}

	@Override
	public int getRelativeX()
	{
		Location loc = getLocation();
		return CommonUtil.round(loc.getX());
	}

	@Override
	public int getRelativeY()
	{
		Location loc = getLocation();
		return CommonUtil.round(loc.getY());
	}

	@Override
	public int getRelativeZ()
	{
		Location loc = getLocation();
		return CommonUtil.round(loc.getZ());
	}

	@Override
	public Server getServer()
	{
		return wolf.getServer();
	}

	@Override
	public ITeam getTeam()
	{
		if (hasTeam())
			return getOwner().getTeam();
		return null;
	}

	@Override
	public World getWorld()
	{
		return wolf.getWorld();
	}

	public int hashCode()
	{
		return new HashCodeBuilder(53, 89).append(getOwner()).append(getLocation()).toHashCode();
	}

	@Override
	public boolean hasOwner()
	{
		return getOwner() != null;
	}

	@Override
	public boolean hasTeam()
	{
		return hasOwner() && getOwner().hasTeam();
	}

	@Override
	public boolean isOnline()
	{
		return true;
	}

	@Override
	public boolean isOnSameTeam(ITeamEntity entity)
	{
		return getTeam().equals(entity.getTeam());
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		return teleport(entity.getLocation());
	}

	@Override
	public boolean teleport(Location location)
	{
		return wolf.teleport(location);
	}

	@Override
	public String toString()
	{
		String wolfData = "";
		return wolfData;
	}

	@Override
	public String getName()
	{
		if (this.hasOwner())
			return this.getOwner() + "'s  Wolfie";
		return "Wild Wolfie";
	}

	@Override
	public boolean isVulnerable()
	{
		return true;
	}

	@Override
	public void sendMessage(String message)
	{
		getWorld().playSound(getLocation(), Sound.WOLF_BARK, 10, 10);
	}

	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		return BukkitUtil.getNearbyEntities(this.getLocation(), radius);
	}

	@Override
	public boolean eject()
	{
		return wolf.eject();
	}

	@Override
	public int getEntityId()
	{
		return wolf.getEntityId();
	}

	@Override
	public float getFallDistance()
	{
		return wolf.getFallDistance();
	}

	@Override
	public int getFireTicks()
	{
		return wolf.getFireTicks();
	}

	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		return wolf.getLastDamageCause();
	}

	@Override
	public Location getLocation(Location arg0)
	{
		return wolf.getLocation(arg0);
	}

	@Override
	public int getMaxFireTicks()
	{
		return wolf.getMaxFireTicks();
	}

	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2)
	{
		return wolf.getNearbyEntities(arg0, arg1, arg2);
	}

	@Override
	public Entity getPassenger()
	{
		return wolf.getPassenger();
	}

	@Override
	public int getTicksLived()
	{
		return wolf.getTicksLived();
	}

	@Override
	public EntityType getType()
	{
		return wolf.getType();
	}

	@Override
	public UUID getUniqueId()
	{
		return wolf.getUniqueId();
	}

	@Override
	public Entity getVehicle()
	{
		return wolf.getVehicle();
	}

	@Override
	public Vector getVelocity()
	{
		return wolf.getVelocity();
	}

	@Override
	public boolean isDead()
	{
		return wolf.isDead();
	}

	@Override
	public boolean isEmpty()
	{
		return wolf.isEmpty();
	}

	@Override
	public boolean isInsideVehicle()
	{
		return wolf.isInsideVehicle();
	}

	@Override
	public boolean isOnGround()
	{
		return wolf.isOnGround();
	}

	@Override
	public boolean isValid()
	{
		return wolf.isValid();
	}

	@Override
	public boolean leaveVehicle()
	{
		return wolf.leaveVehicle();
	}

	@Override
	public void playEffect(EntityEffect arg0)
	{
		wolf.playEffect(arg0);
	}

	@Override
	public void remove()
	{
		wolf.remove();
	}

	@Override
	public void setFallDistance(float arg0)
	{
		wolf.setFallDistance(arg0);
	}

	@Override
	public void setFireTicks(int arg0)
	{
		wolf.setFireTicks(arg0);
	}

	@Override
	public void setLastDamageCause(EntityDamageEvent arg0)
	{
		wolf.setLastDamageCause(arg0);
	}

	@Override
	public boolean setPassenger(Entity arg0)
	{
		return wolf.setPassenger(arg0);
	}

	@Override
	public void setTicksLived(int arg0)
	{
		wolf.setTicksLived(arg0);
	}

	@Override
	public void setVelocity(Vector arg0)
	{
		wolf.setVelocity(arg0);
	}

	@Override
	public boolean teleport(Entity arg0)
	{
		return wolf.teleport(arg0);
	}

	@Override
	public boolean teleport(Location arg0, TeleportCause arg1)
	{
		return wolf.teleport(arg0, arg1);
	}

	@Override
	public boolean teleport(Entity arg0, TeleportCause arg1)
	{
		return wolf.teleport(arg0, arg1);
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0)
	{
		return wolf.getMetadata(arg0);
	}

	@Override
	public boolean hasMetadata(String arg0)
	{
		return wolf.hasMetadata(arg0);
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1)
	{
		wolf.removeMetadata(arg0, arg1);
	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1)
	{
		wolf.setMetadata(arg0, arg1);
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		List<ITeamPlayer> mates = CommonUtil.emptyList();
		if (hasTeam())
		{
			for (String p : getTeam().getPlayers())
			{
				mates.add(xTeam.getInstance().getPlayerManager().getPlayer(p));
			}
		}
		return mates;
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return xTeam.getInstance().getPlayerManager().getOfflineTeammatesOf(this);
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		return xTeam.getInstance().getPlayerManager().getOnlineTeammatesOf(this);
	}

	@Override
	public String getPublicInfo()
	{
		return "Someone else's wolfie";
	}

	@Override
	public String getPrivateInfo()
	{
		return this.getOwner() + "'s wolfie";
	}
}
