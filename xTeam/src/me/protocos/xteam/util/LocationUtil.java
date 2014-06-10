package me.protocos.xteam.util;

import me.protocos.xteam.model.Direction;
import org.bukkit.Location;
import org.bukkit.util.NumberConversions;

public class LocationUtil
{
	public static String getRelativePosition(Location location1, Location location2)
	{
		Location flatLocation1 = new FlatLocation(location1);
		Location flatLocation2 = new FlatLocation(location2);
		if (flatLocation1.getWorld().equals(flatLocation2.getWorld()))
		{
			String position = "";
			int distance = CommonUtil.round(flatLocation1.distance(flatLocation2));
			if (distance > 0)
				position += distance + CommonUtil.pluralizeBasedOn(" block", distance) + " to " +
						getRelativeAngleBetween(flatLocation1, flatLocation2);
			else
				position += "Here";
			position += getVerticleDifference(location1, location2);
			return position;
		}
		return "in world: \"" + flatLocation2.getWorld().getName() + "\"";
	}

	private static String getVerticleDifference(Location location1, Location location2)
	{
		int diffY = CommonUtil.round(location2.getY() - location1.getY());
		if (diffY != 0)
			return ", " + (diffY >= 0 ? diffY + CommonUtil.pluralizeBasedOn(" block", diffY) + " up" : -1 * diffY + CommonUtil.pluralizeBasedOn(" block", diffY) + " down");
		return "";
	}

	public static Direction getRelativeAngleBetween(Location location1, Location location2)
	{
		return Direction.fromAngle(getYawAngleToLocation(location1, location2));
	}

	public static double getYawAngleToLocation(Location location1, Location location2)
	{
		return toPositiveAngle(getAngleBetween(location1, location2) - getYawTranslation(location1));
	}

	public static double getAngleBetween(Location location1, Location location2)
	{
		double diffX = location2.getX() - location1.getX();
		double diffZ = -1 * location2.getZ() + location1.getZ();
		double angle = Math.atan2(diffZ, diffX);
		return toPositiveAngle(Math.toDegrees(angle));
	}

	static double getYawTranslation(Location location)
	{
		return (((360 - location.getYaw()) - 90) % 360);
	}

	static double toPositiveAngle(double angle)
	{
		return (angle + 360) % 360;
	}
}

class FlatLocation extends Location
{
	public FlatLocation(Location location)
	{
		super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	@Override
	public double getY()
	{
		return 0.0D;
	}

	@Override
	public double distance(Location location)
	{
		if (location == null)
			throw new IllegalArgumentException("Cannot measure distance to a null location");
		if ((location.getWorld() == null) || (getWorld() == null))
			throw new IllegalArgumentException("Cannot measure distance to a null world");
		if (!this.getWorld().equals(location.getWorld()))
		{
			throw new IllegalArgumentException("Cannot measure distance between " + getWorld().getName() + " and " + location.getWorld().getName());
		}
		return Math.sqrt(NumberConversions.square(this.getX() - location.getX()) + NumberConversions.square(this.getY() - location.getY()) + NumberConversions.square(this.getZ() - location.getZ()));
	}
}
