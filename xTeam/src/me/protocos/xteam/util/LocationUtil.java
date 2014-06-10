package me.protocos.xteam.util;

import org.bukkit.Location;

public class LocationUtil
{
	public static double getYawDiffToLocation(Location location1, Location location2)
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

	public static double getYawTranslation(Location location)
	{
		return (((360 - location.getYaw()) - 90) % 360);
	}

	public static double toPositiveAngle(double angle)
	{
		return (angle + 360) % 360;
	}
}