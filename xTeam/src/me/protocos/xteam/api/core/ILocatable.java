package me.protocos.xteam.api.core;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public interface ILocatable
{
	public abstract Location getLocation();
	public abstract World getWorld();
	public abstract Server getServer();
	public abstract int getRelativeX();
	public abstract int getRelativeY();
	public abstract int getRelativeZ();
	public abstract double getDistanceTo(ILocatable entity);
	public abstract boolean teleportTo(ILocatable entity);
	public abstract List<Entity> getNearbyEntities(int radius);
}
