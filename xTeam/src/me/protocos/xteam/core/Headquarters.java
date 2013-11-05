package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.api.core.ILocatable;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class Headquarters extends Location implements ILocatable
{
	public Headquarters()
	{
		this(Data.BUKKIT.getWorld("world"), CommonUtil.DOUBLE_ZERO, CommonUtil.DOUBLE_ZERO, CommonUtil.DOUBLE_ZERO, CommonUtil.FLOAT_ZERO, CommonUtil.FLOAT_ZERO);
	}

	public Headquarters(Location location)
	{
		this(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	public Headquarters(World world, double X, double Y, double Z, float yaw, float pitch)
	{
		super(world, X, Y, Z, yaw, pitch);
	}

	public boolean exists()
	{
		return getWorld() != null;
	}

	@Override
	public String getName()
	{
		return "the " + ChatColor.GREEN + "team headquarters";
	}
	@Override
	public Location getLocation()
	{
		return this;
	}
	@Override
	public Server getServer()
	{
		return Data.BUKKIT;
	}
	@Override
	public int getRelativeX()
	{
		return CommonUtil.round(this.getX());
	}
	@Override
	public int getRelativeY()
	{
		return CommonUtil.round(this.getY());
	}
	@Override
	public int getRelativeZ()
	{
		return CommonUtil.round(this.getZ());
	}
	@Override
	public double getDistanceTo(ILocatable entity)
	{
		return this.distance(entity.getLocation());
	}
	@Override
	public boolean teleportTo(ILocatable entity)
	{
		return false;
	}
	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		return BukkitUtil.getNearbyEntities(this, radius);
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
		return new EqualsBuilder().append(this.getWorld(), rhs.getWorld()).append(this.getX(), rhs.getX()).append(this.getY(), rhs.getY()).append(this.getZ(), rhs.getZ()).append(this.getPitch(), rhs.getPitch()).append(this.getYaw(), rhs.getYaw()).isEquals();
	}
}
