package me.protocos.xteam.api.core;

import org.bukkit.Location;
import org.bukkit.World;

public interface ILocatable
{
	public abstract Location getLocation();
	public abstract World getWorld();
	public abstract int getRelativeX();
	public abstract int getRelativeY();
	public abstract int getRelativeZ();
}
