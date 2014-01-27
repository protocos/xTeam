package me.protocos.xteam.fakeobjects;

import org.bukkit.Location;
import org.bukkit.World;

public class FakeLocation extends Location
{

	public FakeLocation()
	{
		this(new FakeWorld());
	}

	public FakeLocation(World world)
	{
		super(world, 0.0D, 0.0D, 0.0D);
	}

	public FakeLocation(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	public FakeLocation(World world, double x, double y, double z, float yaw, float pitch)
	{
		super(world, x, y, z, yaw, pitch);
	}

	public FakeLocation(double x, double y, double z, float yaw, float pitch)
	{
		super(new FakeWorld(), x, y, z, yaw, pitch);
	}

	public FakeLocation(Location location)
	{
		super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	public Location toLocation()
	{
		return new Location(this.getWorld(), this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
	}

	@Override
	public String toString()
	{
		return "Fake" + super.toString();
	}

}
