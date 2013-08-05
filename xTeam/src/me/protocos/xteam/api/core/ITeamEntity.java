package me.protocos.xteam.api.core;

import me.protocos.xteam.core.Team;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

public interface ITeamEntity extends ILocatable
{
	public abstract double getDistanceTo(ITeamEntity entity);
	public abstract double getHealth();
	public abstract Server getServer();
	public abstract Team getTeam();
	public abstract World getWorld();
	public abstract boolean hasTeam();
	public abstract boolean isEnemy(ITeamEntity entity);
	public abstract boolean isOnline();
	public abstract boolean isOnSameTeam(ITeamEntity entity);
	public abstract boolean teleport(ITeamEntity entity);
	public abstract boolean teleport(Location location);
}
