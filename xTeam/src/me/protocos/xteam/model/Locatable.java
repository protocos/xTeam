package me.protocos.xteam.model;

import java.util.List;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class Locatable implements ILocatable
{
	private String name;
	private Location location;

	public Locatable(String name, Location location)
	{
		this.name = name;
		this.location = location;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public Location getLocation()
	{
		return this.location;
	}

	@Override
	public World getWorld()
	{
		return location.getWorld();
	}

	@Override
	public Server getServer()
	{
		return BukkitUtil.getServer();
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
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Location))
			return false;

		Location rhs = (Location) obj;
		return new EqualsBuilder().append(this.getWorld(), rhs.getWorld()).append(location.getX(), rhs.getX()).append(location.getY(), rhs.getY()).append(location.getZ(), rhs.getZ()).append(location.getPitch(), rhs.getPitch()).append(location.getYaw(), rhs.getYaw()).isEquals();
	}

	@Override
	public String toString()
	{
		return this.name + ": X(" + this.getRelativeX() + ") Y(" + this.getRelativeY() + ") Z(" + this.getRelativeZ() + ")";
	}
}
