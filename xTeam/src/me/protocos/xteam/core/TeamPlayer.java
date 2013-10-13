package me.protocos.xteam.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamEntity;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
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

public class TeamPlayer implements ITeamPlayer
{
	private String name;
	private OfflinePlayer offlinePlayer;
	private Player onlinePlayer;
	private Long lastTeleported = 0L;

	public TeamPlayer(OfflinePlayer player)
	{
		name = player.getName();
		onlinePlayer = xTeam.sm.getPlayer(name);
		offlinePlayer = player;
	}
	public TeamPlayer(Player player)
	{
		name = player.getName();
		onlinePlayer = player;
		offlinePlayer = xTeam.sm.getOfflinePlayer(name);
	}
	public TeamPlayer(String playerName)
	{
		name = playerName;
		offlinePlayer = xTeam.sm.getOfflinePlayer(playerName);
		onlinePlayer = xTeam.sm.getPlayer(playerName);
	}
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof TeamPlayer))
			return false;

		TeamPlayer rhs = (TeamPlayer) obj;
		return new EqualsBuilder().append(name, rhs.name).isEquals();

	}
	@Override
	public double getDistanceTo(ITeamEntity entity)
	{
		if (this.isOnline() && entity.isOnline())
			return getLocation().distance(entity.getLocation());
		return Double.MAX_VALUE;
	}
	@Override
	public double getHealth()
	{
		if (this.isOnline())
		{
			return onlinePlayer.getHealth();
		}
		return -1.0;
	}

	public Long getLastTeleported()
	{
		return lastTeleported;
	}

	public String getLastPlayed()
	{
		long milliSeconds = offlinePlayer.getLastPlayed();
		DateFormat formatter = new SimpleDateFormat("MMM d");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		String month_day = formatter.format(calendar.getTime());
		formatter = new SimpleDateFormat("h:mm a");
		String hour_minute_am_pm = formatter.format(calendar.getTime());
		return month_day + " @ " + hour_minute_am_pm;
	}
	@Override
	public Location getLocation()
	{
		if (this.isOnline())
		{
			return onlinePlayer.getLocation();
		}
		return null;
	}
	public String getName()
	{
		return name;
	}
	public OfflinePlayer getOfflinePlayer()
	{
		return offlinePlayer;
	}
	public List<String> getOfflineTeammates()
	{
		List<String> offlineMates = new ArrayList<String>();
		if (hasTeam())
		{
			for (String p : getTeam().getPlayers())
			{
				TeamPlayer mate = new TeamPlayer(p);
				if (!mate.isOnline() && !name.equals(p))
					offlineMates.add(p);
			}
		}
		return offlineMates;
	}
	public Player getOnlinePlayer()
	{
		return onlinePlayer;
	}
	public List<TeamPlayer> getOnlineTeammates()
	{
		List<TeamPlayer> onlineMates = new ArrayList<TeamPlayer>();
		if (hasTeam())
		{
			for (String p : getTeam().getPlayers())
			{
				TeamPlayer mate = new TeamPlayer(p);
				if (mate.isOnline() && !name.equals(p))
					onlineMates.add(mate);
			}
		}
		return onlineMates;
	}
	@Override
	public int getRelativeX()
	{
		if (this.isOnline())
		{
			Location loc = getLocation();
			return (int) Math.round(loc.getX());
		}
		return 0;
	}
	@Override
	public int getRelativeY()
	{
		if (this.isOnline())
		{
			Location loc = getLocation();
			return (int) Math.round(loc.getY());
		}
		return 0;
	}
	@Override
	public int getRelativeZ()
	{
		if (this.isOnline())
		{
			Location loc = getLocation();
			return (int) Math.round(loc.getZ());
		}
		return 0;
	}
	@Override
	public Server getServer()
	{
		if (this.isOnline())
		{
			return onlinePlayer.getServer();
		}
		return null;
	}
	@Override
	public Team getTeam()
	{
		for (Team team : xTeam.tm.getAllTeams())
			if (team.containsPlayer(name))
				return team;
		return null;
	}
	@Override
	public List<ITeamPlayer> getTeammates()
	{
		List<ITeamPlayer> mates = CommonUtil.emptyList();
		if (hasTeam())
		{
			for (String p : getTeam().getPlayers())
			{
				if (!name.equals(p))
					mates.add(new TeamPlayer(p));
			}
		}
		return mates;
	}
	@Override
	public World getWorld()
	{
		if (this.isOnline())
		{
			return onlinePlayer.getWorld();
		}
		return null;
	}
	public int hashCode()
	{
		return new HashCodeBuilder(17, 41).append(name).toHashCode();
	}
	public boolean hasPermission(String permission)
	{
		if (this.isOnline())
		{
			return onlinePlayer.hasPermission(permission);
		}
		return false;
	}
	public boolean hasPlayedBefore()
	{
		if (offlinePlayer == null)
			return false;
		return offlinePlayer.hasPlayedBefore() || offlinePlayer.isOnline();
	}
	@Override
	public boolean hasTeam()
	{
		return getTeam() != null;
	}
	public boolean isAdmin()
	{
		if (hasTeam())
		{
			return getTeam().getAdmins().contains(name);
		}
		return false;
	}
	public boolean isEnemy(ITeamEntity entity)
	{
		if (this.isOnSameTeam(entity))
			return false;
		return true;
	}
	public boolean isLeader()
	{
		if (this.hasTeam())
		{
			return getTeam().getLeader().equals(name);
		}
		return false;
	}
	@Override
	public boolean isOnline()
	{
		if (this.hasPlayedBefore())
			return offlinePlayer.isOnline();
		return false;
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
	public boolean isOp()
	{
		return offlinePlayer.isOp();
	}
	@Override
	public boolean sendMessage(String message)
	{
		if (this.isOnline())
		{
			onlinePlayer.sendMessage(message);
			return true;
		}
		return false;
	}
	public void sendMessageToTeam(String message)
	{
		List<TeamPlayer> onlinePlayers = getOnlineTeammates();
		for (TeamPlayer player : onlinePlayers)
		{
			player.sendMessage(message);
		}
	}
	public void sendMessageToTeam(String message, Player exclude)
	{
		List<TeamPlayer> onlinePlayers = getOnlineTeammates();
		for (TeamPlayer player : onlinePlayers)
		{
			if (!player.getName().equals(exclude.getName()))
			{
				player.sendMessage(message);
			}
		}
	}
	@Override
	public boolean teleportTo(ITeamEntity entity)
	{
		if (this.isOnline() && entity.isOnline())
		{
			lastTeleported = Long.valueOf(System.currentTimeMillis());
			return onlinePlayer.teleport(entity.getLocation());
		}
		return false;
	}
	@Override
	public boolean teleport(Location location)
	{
		if (this.isOnline())
		{
			lastTeleported = Long.valueOf(System.currentTimeMillis());
			return onlinePlayer.teleport(location);
		}
		return false;
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
		return onlinePlayer.eject();
	}
	@Override
	public int getEntityId()
	{
		return onlinePlayer.getEntityId();
	}
	@Override
	public float getFallDistance()
	{
		return onlinePlayer.getFallDistance();
	}
	@Override
	public int getFireTicks()
	{
		return onlinePlayer.getFireTicks();
	}
	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		return onlinePlayer.getLastDamageCause();
	}
	@Override
	public Location getLocation(Location arg0)
	{
		return onlinePlayer.getLocation(arg0);
	}
	@Override
	public int getMaxFireTicks()
	{
		return onlinePlayer.getMaxFireTicks();
	}
	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2)
	{
		return onlinePlayer.getNearbyEntities(arg0, arg1, arg2);
	}
	@Override
	public Entity getPassenger()
	{
		return onlinePlayer.getPassenger();
	}
	@Override
	public int getTicksLived()
	{
		return onlinePlayer.getTicksLived();
	}
	@Override
	public EntityType getType()
	{
		return onlinePlayer.getType();
	}
	@Override
	public UUID getUniqueId()
	{
		return onlinePlayer.getUniqueId();
	}
	@Override
	public Entity getVehicle()
	{
		return onlinePlayer.getVehicle();
	}
	@Override
	public Vector getVelocity()
	{
		return onlinePlayer.getVelocity();
	}
	@Override
	public boolean isDead()
	{
		return onlinePlayer.isDead();
	}
	@Override
	public boolean isEmpty()
	{
		return onlinePlayer.isEmpty();
	}
	@Override
	public boolean isInsideVehicle()
	{
		return onlinePlayer.isInsideVehicle();
	}
	@Override
	@Deprecated
	public boolean isOnGround()
	{
		return onlinePlayer.isOnGround();
	}
	@Override
	public boolean isValid()
	{
		return onlinePlayer.isValid();
	}
	@Override
	public boolean leaveVehicle()
	{
		return onlinePlayer.leaveVehicle();
	}
	@Override
	public void playEffect(EntityEffect arg0)
	{
		onlinePlayer.playEffect(arg0);
	}
	@Override
	public void remove()
	{
		onlinePlayer.remove();
	}
	@Override
	public void setFallDistance(float arg0)
	{
		onlinePlayer.setFallDistance(arg0);
	}
	@Override
	public void setFireTicks(int arg0)
	{
		onlinePlayer.setFireTicks(arg0);
	}
	@Override
	public void setLastDamageCause(EntityDamageEvent arg0)
	{
		onlinePlayer.setLastDamageCause(arg0);
	}
	@Override
	public boolean setPassenger(Entity arg0)
	{
		return onlinePlayer.setPassenger(arg0);
	}
	@Override
	public void setTicksLived(int arg0)
	{
		onlinePlayer.setTicksLived(arg0);
	}
	@Override
	public void setVelocity(Vector arg0)
	{
		onlinePlayer.setVelocity(arg0);
	}
	@Override
	public boolean teleport(Entity arg0)
	{
		lastTeleported = Long.valueOf(System.currentTimeMillis());
		return onlinePlayer.teleport(arg0);
	}
	@Override
	public boolean teleport(Location arg0, TeleportCause arg1)
	{
		lastTeleported = Long.valueOf(System.currentTimeMillis());
		return onlinePlayer.teleport(arg0, arg1);
	}
	@Override
	public boolean teleport(Entity arg0, TeleportCause arg1)
	{
		lastTeleported = Long.valueOf(System.currentTimeMillis());
		return onlinePlayer.teleport(arg0, arg1);
	}
	@Override
	public List<MetadataValue> getMetadata(String arg0)
	{
		return onlinePlayer.getMetadata(arg0);
	}
	@Override
	public boolean hasMetadata(String arg0)
	{
		return onlinePlayer.hasMetadata(arg0);
	}
	@Override
	public void removeMetadata(String arg0, Plugin arg1)
	{
		onlinePlayer.removeMetadata(arg0, arg1);
	}
	@Override
	public void setMetadata(String arg0, MetadataValue arg1)
	{
		onlinePlayer.setMetadata(arg0, arg1);
	}
	@Override
	public boolean isTeleportable()
	{
		return true;
	}
	@Override
	public boolean isVulnerable()
	{
		return true;
	}
	@Override
	public String getEntityName()
	{
		return onlinePlayer.getName();
	}
	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		return BukkitUtil.getNearbyEntities(this.getLocation(), radius);
	}
}
