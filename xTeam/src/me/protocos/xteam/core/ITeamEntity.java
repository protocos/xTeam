package me.protocos.xteam.core;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

public interface ITeamEntity extends Locatable
{
	public abstract Server getServer();
	public abstract World getWorld();
	public abstract boolean isOnline();
	public abstract int getHealth();
	public abstract double getDistanceTo(ITeamEntity entity);
	public abstract boolean isOnSameTeam(ITeamEntity entity);
	public abstract boolean isEnemy(ITeamEntity entity);
	public abstract boolean teleport(ITeamEntity entity);
	public abstract boolean teleport(Location location);
	public abstract boolean hasTeam();
	public abstract Team getTeam();
}
