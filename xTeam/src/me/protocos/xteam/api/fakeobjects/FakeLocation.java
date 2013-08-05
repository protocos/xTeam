package me.protocos.xteam.api.fakeobjects;

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

}
