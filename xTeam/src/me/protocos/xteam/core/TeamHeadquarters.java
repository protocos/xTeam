package me.protocos.xteam.core;

import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.bukkit.World;

public class TeamHeadquarters extends Location
{
	public TeamHeadquarters()
	{
		this(CommonUtil.DOUBLE_ZERO, CommonUtil.DOUBLE_ZERO, CommonUtil.DOUBLE_ZERO);
	}
	public TeamHeadquarters(double x, double y, double z)
	{
		this(Data.BUKKIT.getWorld("world"), x, y, z, CommonUtil.FLOAT_ZERO, CommonUtil.FLOAT_ZERO);
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
}
