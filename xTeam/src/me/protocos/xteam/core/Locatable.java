package me.protocos.xteam.core;

import org.bukkit.Location;

public interface Locatable
{
	public abstract Location getLocation();
	public abstract int getRelativeX();
	public abstract int getRelativeY();
	public abstract int getRelativeZ();
}
