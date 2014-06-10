package me.protocos.xteam.entity;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.LocationUtil;
import me.protocos.xteam.util.PermissionUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class TeamPlayer implements ITeamPlayer, ILocatable, Entity, CommandSender
{
	private ILog log;
	private TeleportScheduler teleportScheduler;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private Player player;

	public TeamPlayer(TeamPlugin teamPlugin, Player player)
	{
		this.log = teamPlugin.getLog();
		this.teleportScheduler = teamPlugin.getTeleportScheduler();
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.playerFactory = teamPlugin.getPlayerFactory();
		this.player = player;
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
		if (this.getLocation().getWorld().equals(entity.getLocation().getWorld()))
			return this.getLocation().distance(entity.getLocation());
		return Double.MAX_VALUE;
	}

	@Override
	public double getHealth()
	{
		return player.getHealth();
	}

	@Override
	public void setLastTeleported(long lastTeleported)
	{
		playerFactory.setLastTeleported(this, lastTeleported);
	}

	@Override
	public long getLastTeleported()
	{
		return playerFactory.getLastTeleported(this.getName());
	}

	@Override
	public String getLastPlayed()
	{
		return CommonUtil.formatDateToMonthDay(player.getLastPlayed());
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
		return playerFactory.getOfflineTeammatesOf(this);
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		return playerFactory.getOnlineTeammatesOf(this);
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
	public ITeam getTeam()
	{
		return teamCoordinator.getTeamByPlayer(player.getName());
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		return playerFactory.getTeammatesOf(this);
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
	public boolean hasPermission(IPermissible permission)
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
			return getTeam().isAdmin(this.getName());
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
			return getTeam().isLeader(this.getName());
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
	public void sendMessageToTeam(String message)
	{
		Message m = new Message.Builder(message).addRecipients(this.getTeam()).build();
		m.send(log);
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		teleportScheduler.teleport(this, entity);
		return true;
	}

	@Override
	public boolean teleport(final Location location)
	{
		if (location.equals(this.getReturnLocation()))
		{
			this.removeReturnLocation();
		}
		else
		{
			this.setReturnLocation(this.getLocation());
		}
		player.getWorld().getChunkAt(location).load();
		while (!player.getWorld().getChunkAt(location).isLoaded())
		{
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
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
		playerFactory.setReturnLocation(this, returnLocation);
	}

	@Override
	public Location getReturnLocation()
	{
		return playerFactory.getReturnLocation(this.getName());
	}

	@Override
	public boolean hasReturnLocation()
	{
		return this.getReturnLocation() != null;
	}

	@Override
	public void removeReturnLocation()
	{
		playerFactory.setReturnLocation(this, null);
	}

	@Override
	public void setLastAttacked(long lastAttacked)
	{
		playerFactory.setLastAttacked(this, lastAttacked);
	}

	@Override
	public long getLastAttacked()
	{
		return playerFactory.getLastAttacked(this.getName());
	}

	@Override
	public boolean isDamaged()
	{
		return player.getNoDamageTicks() > 0;
	}

	@Override
	public String getInfoFor(ITeamEntity entity)
	{
		if (entity.isOnSameTeam(this))
		{
			String location = "";
			int health = (int) this.getHealth();
			if (Configuration.DISPLAY_COORDINATES)
			{
				if (Configuration.DISPLAY_RELATIVE_COORDINATES && entity instanceof ILocatable)
				{
					ILocatable locatable = (ILocatable) entity;
					if (!entity.getName().equals(this.getName()))
						location += " " + LocationUtil.getRelativePosition(locatable.getLocation(), this.getLocation());
				}
				else
					location += " Location: " + ChatColor.RED + this.getRelativeX() + " " + ChatColor.GREEN + this.getRelativeY() + " " + ChatColor.BLUE + this.getRelativeZ() + ChatColor.RESET + " in \"" + this.getWorld().getName() + "\"";
			}
			return ChatColor.GREEN + "    " + this.getName() + ChatColor.RESET + " (" + (health >= 15 ? ChatColor.GREEN : ChatColor.RED) + health * 5 + "%" + ChatColor.RESET + ")" + location;
		}
		return ChatColor.GREEN + "    " + this.getName();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0)
	{
		return player.addAttachment(arg0);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1)
	{
		return player.addAttachment(arg0, arg1);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2)
	{
		return player.addAttachment(arg0, arg1, arg2);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3)
	{
		return player.addAttachment(arg0, arg1, arg2, arg3);
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return player.getEffectivePermissions();
	}

	@Override
	public boolean hasPermission(String arg0)
	{
		return player.hasPermission(arg0);
	}

	@Override
	public boolean hasPermission(Permission arg0)
	{
		return player.hasPermission(arg0);
	}

	@Override
	public boolean isPermissionSet(String arg0)
	{
		return player.isPermissionSet(arg0);
	}

	@Override
	public boolean isPermissionSet(Permission arg0)
	{
		return player.isPermissionSet(arg0);
	}

	@Override
	public void recalculatePermissions()
	{
		player.recalculatePermissions();
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0)
	{
		player.removeAttachment(arg0);
	}

	@Override
	public void setOp(boolean arg0)
	{
		player.setOp(arg0);
	}

	@Override
	public void sendMessage(String message)
	{
		player.sendMessage(message);
	}

	@Override
	public void sendMessage(String[] arg0)
	{
		player.sendMessage(arg0);
	}
}
