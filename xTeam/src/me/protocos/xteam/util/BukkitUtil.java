package me.protocos.xteam.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class BukkitUtil
{
	public static List<Entity> getNearbyEntities(Location location, int radius)
	{
		List<Entity> nearbyEntities = new ArrayList<Entity>();
		List<Entity> entities = location.getWorld().getEntities();
		for (Entity e : entities)
		{
			if (e.getLocation().distance(location) <= radius)
				if (Math.abs(e.getLocation().getY() - location.getY()) <= 4)
					nearbyEntities.add(e);
		}
		return nearbyEntities;
	}
}
