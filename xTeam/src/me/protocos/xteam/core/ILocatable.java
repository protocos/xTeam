package me.protocos.xteam.core;

import org.bukkit.Location;

public interface ILocatable
{
	public abstract Location getLocation();
	public abstract int getRelativeX();
	public abstract int getRelativeY();
	public abstract int getRelativeZ();
}
