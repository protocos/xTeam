package me.protocos.xteam.util;

import me.protocos.xteam.model.Direction;
import org.bukkit.Location;

public class LocationUtil
{
	public static String getRelativePosition(Location location1, Location location2)
	{
		//If I really really want this to be precise, 
		//I would set location1 and location2 to have 
		//the same Y value so that the block distance is exact,
		//but meh.
		if (location1.getWorld().getName().equals(location2.getWorld().getName()))
		{
			String position = "";
			int distance = CommonUtil.round(location1.distance(location2));
			if (distance > 0)
				position += +distance + CommonUtil.pluralizeBasedOn(" block", distance) + " to " +
						getRelativeAngleBetween(location1, location2);
			else
				position += "Here";
			position += getVerticleDifference(location1, location2);
			return position;
		}
		return "in world: \"" + location2.getWorld().getName() + "\"";
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
