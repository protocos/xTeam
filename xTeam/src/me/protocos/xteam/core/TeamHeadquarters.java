package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.api.core.ILocatable;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class TeamHeadquarters extends Location implements ILocatable
{
	public TeamHeadquarters()
	{
		this(Data.BUKKIT.getWorld("world"), CommonUtil.DOUBLE_ZERO, CommonUtil.DOUBLE_ZERO, CommonUtil.DOUBLE_ZERO, CommonUtil.FLOAT_ZERO, CommonUtil.FLOAT_ZERO);
	}
	public TeamHeadquarters(Location location)
	{
		this(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}
	public TeamHeadquarters(World world, double X, double Y, double Z, float yaw, float pitch)
	{
		super(world, X, Y, Z, yaw, pitch);
	}
	public boolean exists()
	{
		return getWorld() != null;
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
		return (int) Math.round(this.getX());
	}
	@Override
	public int getRelativeY()
	{
		return (int) Math.round(this.getY());
	}
	@Override
	public int getRelativeZ()
	{
		return (int) Math.round(this.getZ());
	}
	@Override
	public double getDistanceTo(ILocatable entity)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean teleportTo(ILocatable entity)
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
