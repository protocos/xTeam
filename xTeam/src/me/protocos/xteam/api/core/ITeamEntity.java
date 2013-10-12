package me.protocos.xteam.api.core;

import java.util.List;
import me.protocos.xteam.core.Team;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public interface ITeamEntity extends ILocatable
{
	public abstract Team getTeam();
	public abstract String getEntityName();
	public abstract World getWorld();
	public abstract Server getServer();
	public abstract double getDistanceTo(ITeamEntity entity);
	public abstract boolean hasTeam();
	public abstract boolean teleportTo(ITeamEntity entity);
	public abstract boolean isOnSameTeam(ITeamEntity entity);
	public abstract boolean isOnline();
	public abstract boolean isTeleportable();
	public abstract boolean isVulnerable();
	public abstract boolean sendMessage(String message);
	public abstract List<Entity> getNearbyEntities(int radius);
	public abstract Location getLocation();
}
