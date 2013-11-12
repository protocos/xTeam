package me.protocos.xteam.core;

import java.util.List;
import java.util.UUID;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.core.ILocatable;
import me.protocos.xteam.api.core.ITeamEntity;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.util.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class TeamPlayer implements ITeamPlayer, ILocatable, Entity
{
	private Player player;

	public TeamPlayer(Player player)
	{
		this.player = player;
	}

	public static TeamPlayer teamPlayerFromUnknown(ITeamPlayer player)
	{
		if (!player.isOnline())
		{
			throw new AssertionError(player.getName() + " is not online");
		}
		return (TeamPlayer) player;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof ITeamPlayer))
			return false;

		ITeamPlayer rhs = (ITeamPlayer) obj;
		return new EqualsBuilder().append(this.getName(), rhs.getName()).isEquals();
	}

	@Override
	public double getDistanceTo(ILocatable entity)
	{
		return this.getLocation().distance(entity.getLocation());
	}

	@Override
	public double getHealth()
	{
		return player.getHealth();
	}

	@Override
	public void setLastTeleported(long lastTeleported)
	{
		xTeamPlugin.getInstance().getPlayerManager().setLastTeleported(this, lastTeleported);
	}

	@Override
	public long getLastTeleported()
	{
		return xTeamPlugin.getInstance().getPlayerManager().getLastTeleported(this.getName());
	}

	@Override
	public String getLastPlayed()
	{
		return StringUtil.formatDateToMonthDay(player.getLastPlayed());
	}

	@Override
	public Location getLocation()
	{
		return player.getLocation();
	}

	@Override
	public String getName()
	{
		return player.getName();
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return xTeamPlugin.getInstance().getPlayerManager().getOfflineTeammatesOf(this);
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		return xTeamPlugin.getInstance().getPlayerManager().getOnlineTeammatesOf(this);
	}

	@Override
	public int getRelativeX()
	{
		Location loc = this.getLocation();
		return CommonUtil.round(loc.getX());
	}

	@Override
	public int getRelativeY()
	{
		Location loc = this.getLocation();
		return CommonUtil.round(loc.getY());
	}

	@Override
	public int getRelativeZ()
	{
		Location loc = this.getLocation();
		return CommonUtil.round(loc.getZ());
	}

	@Override
	public Server getServer()
	{
		return player.getServer();
	}

	@Override
	public Team getTeam()
	{
		return xTeamPlugin.getInstance().getTeamManager().getTeamFromPlayer(player.getName());
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		return xTeamPlugin.getInstance().getPlayerManager().getTeammatesOf(this);
	}

	@Override
	public World getWorld()
	{
		return player.getWorld();
	}

	public int hashCode()
	{
		return new HashCodeBuilder(17, 41).append(this.getName()).toHashCode();
	}

	@Override
	public boolean hasPermission(String permission)
	{
		return PermissionUtil.hasPermission(player, permission);
	}

	@Override
	public boolean hasPlayedBefore()
	{
		return true;
	}

	@Override
	public boolean hasTeam()
	{
		return getTeam() != null;
	}

	@Override
	public boolean isAdmin()
	{
		if (hasTeam())
		{
			return getTeam().getAdmins().contains(this.getName());
		}
		return false;
	}

	public boolean isEnemy(ITeamEntity entity)
	{
		return !this.isOnSameTeam(entity);
	}

	@Override
	public boolean isLeader()
	{
		if (this.hasTeam())
		{
			return getTeam().getLeader().equals(this.getName());
		}
		return false;
	}

	@Override
	public boolean isOnline()
	{
		return true;
	}

	@Override
	public boolean isOnSameTeam(ITeamEntity otherEntity)
	{
		if (this.hasTeam() && otherEntity.hasTeam())
		{
			return getTeam().equals(otherEntity.getTeam());
		}
		return false;
	}

	@Override
	public boolean isOp()
	{
		return player.isOp();
	}

	@Override
	public boolean sendMessage(String message)
	{
		player.sendMessage(message);
		return true;
	}

	@Override
	public void sendMessageToTeam(String message)
	{
		MessageUtil.sendMessageToTeam(this, message);
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		TeleportScheduler.getInstance().teleport(this, entity);
		return true;
	}

	@Override
	public boolean teleport(Location location)
	{
		return player.teleport(location);
	}

	@Override
	public String toString()
	{
		String playerData = "";
		playerData += "name:" + getName();
		playerData += " hasPlayed:" + hasPlayedBefore();
		playerData += " team:" + (hasTeam() ? getTeam().getName() : "none");
		playerData += " admin:" + (isAdmin() ? "true" : "false");
		playerData += " leader:" + (isLeader() ? "true" : "false");
		return playerData;
	}

	@Override
	public boolean eject()
	{
		return player.eject();
	}

	@Override
	public int getEntityId()
	{
		return player.getEntityId();
	}

	@Override
	public float getFallDistance()
	{
		return player.getFallDistance();
	}

	@Override
	public int getFireTicks()
	{
		return player.getFireTicks();
	}

	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		return player.getLastDamageCause();
	}

	@Override
	public Location getLocation(Location arg0)
	{
		return player.getLocation(arg0);
	}

	@Override
	public int getMaxFireTicks()
	{
		return player.getMaxFireTicks();
	}

	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2)
	{
		return player.getNearbyEntities(arg0, arg1, arg2);
	}

	@Override
	public Entity getPassenger()
	{
		return player.getPassenger();
	}

	@Override
	public int getTicksLived()
	{
		return player.getTicksLived();
	}

	@Override
	public EntityType getType()
	{
		return player.getType();
	}

	@Override
	public UUID getUniqueId()
	{
		return player.getUniqueId();
	}

	@Override
	public Entity getVehicle()
	{
		return player.getVehicle();
	}

	@Override
	public Vector getVelocity()
	{
		return player.getVelocity();
	}

	@Override
	public boolean isDead()
	{
		return player.isDead();
	}

	@Override
	public boolean isEmpty()
	{
		return player.isEmpty();
	}

	@Override
	public boolean isInsideVehicle()
	{
		return player.isInsideVehicle();
	}

	@Override
	@Deprecated
	public boolean isOnGround()
	{
		return player.isOnGround();
	}

	@Override
	public boolean isValid()
	{
		return player.isValid();
	}

	@Override
	public boolean leaveVehicle()
	{
		return player.leaveVehicle();
	}

	@Override
	public void playEffect(EntityEffect arg0)
	{
		player.playEffect(arg0);
	}

	@Override
	public void remove()
	{
		player.remove();
	}

	@Override
	public void setFallDistance(float arg0)
	{
		player.setFallDistance(arg0);
	}

	@Override
	public void setFireTicks(int arg0)
	{
		player.setFireTicks(arg0);
	}

	@Override
	public void setLastDamageCause(EntityDamageEvent arg0)
	{
		player.setLastDamageCause(arg0);
	}

	@Override
	public boolean setPassenger(Entity arg0)
	{
		return player.setPassenger(arg0);
	}

	@Override
	public void setTicksLived(int arg0)
	{
		player.setTicksLived(arg0);
	}

	@Override
	public void setVelocity(Vector arg0)
	{
		player.setVelocity(arg0);
	}

	@Override
	public boolean teleport(Entity arg0)
	{
		setLastTeleported(System.currentTimeMillis());
		return player.teleport(arg0);
	}

	@Override
	public boolean teleport(Location arg0, TeleportCause arg1)
	{
		setLastTeleported(System.currentTimeMillis());
		return player.teleport(arg0, arg1);
	}

	@Override
	public boolean teleport(Entity arg0, TeleportCause arg1)
	{
		setLastTeleported(System.currentTimeMillis());
		return player.teleport(arg0, arg1);
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0)
	{
		return player.getMetadata(arg0);
	}

	@Override
	public boolean hasMetadata(String arg0)
	{
		return player.hasMetadata(arg0);
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1)
	{
		player.removeMetadata(arg0, arg1);
	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1)
	{
		player.setMetadata(arg0, arg1);
	}

	@Override
	public boolean isVulnerable()
	{
		return true;
	}

	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		return BukkitUtil.getNearbyEntities(this.getLocation(), radius);
	}

	@Override
	public void setReturnLocation(Location returnLocation)
	{
		xTeamPlugin.getInstance().getPlayerManager().setReturnLocation(this, returnLocation);
	}

	@Override
	public Location getReturnLocation()
	{
		return xTeamPlugin.getInstance().getPlayerManager().getReturnLocation(this.getName());
	}

	@Override
	public boolean hasReturnLocation()
	{
		return this.getReturnLocation() != null;
	}

	@Override
	public void removeReturnLocation()
	{
		xTeamPlugin.getInstance().getPlayerManager().setReturnLocation(this, null);
	}

	@Override
	public void setLastAttacked(long lastAttacked)
	{
		xTeamPlugin.getInstance().getPlayerManager().setLastAttacked(this, lastAttacked);
	}

	@Override
	public long getLastAttacked()
	{
		return xTeamPlugin.getInstance().getPlayerManager().getLastAttacked(this.getName());
	}

	@Override
	public boolean isDamaged()
	{
		return player.getNoDamageTicks() > 0;
	}

	@Override
	public String getPublicInfo()
	{
		return ChatColor.GREEN + "    " + this.getName();
	}

	@Override
	public String getPrivateInfo()
	{
		String location = "";
		int health = (int) this.getHealth();
		if (Data.DISPLAY_COORDINATES)
			location += " Location: " + ChatColor.RED + this.getRelativeX() + " " + ChatColor.GREEN + this.getRelativeY() + " " + ChatColor.BLUE + this.getRelativeZ() + ChatColor.RESET + " in \"" + this.getWorld().getName() + "\"";
		return ChatColor.GREEN + "    " + this.getName() + ChatColor.RESET + " Health: " + (health >= 15 ? ChatColor.GREEN : ChatColor.RED) + health * 5 + "%" + ChatColor.RESET + location;
	}
}
