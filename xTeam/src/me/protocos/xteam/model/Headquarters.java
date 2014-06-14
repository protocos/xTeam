package me.protocos.xteam.model;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.LocationUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class Headquarters implements IHeadquarters
{
	private BukkitUtil bukkitUtil;
	private Location location;

	public Headquarters(TeamPlugin teamPlugin, Location location)
	{
		this.location = location;
		bukkitUtil = teamPlugin.getBukkitUtil();
	}

	public Headquarters(World world, double X, double Y, double Z, float yaw, float pitch)
	{
		this.location = new Location(world, X, Y, Z, yaw, pitch);
	}

	public boolean exists()
	{
		return getWorld() != null;
	}

	@Override
	public String getName()
	{
		return "the team headquarters";
	}

	@Override
	public Location getLocation()
	{
		return this.location;
	}

	public double getX()
	{
		return location.getX();
	}

	public double getY()
	{
		return location.getY();
	}

	public double getZ()
	{
		return location.getZ();
	}

	public float getYaw()
	{
		return location.getYaw();
	}

	public float getPitch()
	{
		return location.getPitch();
	}

	@Override
	public Server getServer()
	{
		return bukkitUtil.getServer();
	}

	@Override
	public int getRelativeX()
	{
		return CommonUtil.round(location.getX());
	}

	@Override
	public int getRelativeY()
	{
		return CommonUtil.round(location.getY());
	}

	@Override
	public int getRelativeZ()
	{
		return CommonUtil.round(location.getZ());
	}

	@Override
	public double getDistanceTo(ILocatable entity)
	{
		return location.distance(entity.getLocation());
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		return false;
	}

	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		return BukkitUtil.getNearbyEntities(location, radius);
	}

	@Override
	public World getWorld()
	{
		return location.getWorld();
	}

	@Override
	public boolean isValid()
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(61, 173).append(location.getWorld()).append(location.getX()).append(location.getY()).append(location.getZ()).append(location.getPitch()).append(location.getYaw()).toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Headquarters))
			return false;

		Headquarters rhs = (Headquarters) obj;
		return new EqualsBuilder().append(location.getWorld(), rhs.getWorld()).append(location.getX(), rhs.getX()).append(location.getY(), rhs.getY()).append(location.getZ(), rhs.getZ()).append(location.getPitch(), rhs.getPitch()).append(location.getYaw(), rhs.getYaw()).isEquals();
	}

	@Override
	public String toString()
	{
		return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
	}

	@Override
	public String getInfoFor(ITeamEntity entity)
	{
		if (!this.isValid())
			return MessageUtil.red("None set");
		if (Configuration.DISPLAY_RELATIVE_COORDINATES && entity instanceof ILocatable)
		{
			ILocatable locatable = CommonUtil.assignFromType(entity, ILocatable.class);
			return ChatColor.RESET + "Team Headquarters: " + LocationUtil.getRelativePosition(locatable.getLocation(), this.getLocation());
		}
		return ChatColor.RESET + "Team Headquarters: " + LocationUtil.getAbsolutePosition(this);
	}
}
