package me.protocos.xteam.entity;

import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.model.ILocatable;
import org.bukkit.Location;

public interface ITeamPlayer extends ITeamEntity, ILocatable
{
	public abstract int getHealthLevel();

	public abstract int getHungerLevel();

	public abstract String getLastPlayed();

	public abstract String getName();

	public abstract boolean hasPermission(IPermissible permission);

	public abstract boolean hasPlayedBefore();

	public abstract boolean isAdmin();

	public abstract boolean isDamaged();

	public abstract boolean isLeader();

	public abstract boolean isOp();

	//	public abstract void sendMessageToTeam(String message);

	public abstract void setLastTeleported(long lastTeleported);

	public abstract long getLastTeleported();

	public abstract void setLastAttacked(long lastAttacked);

	public abstract long getLastAttacked();

	public abstract void setReturnLocation(Location location);

	public abstract Location getReturnLocation();

	public abstract boolean hasReturnLocation();

	public abstract void removeReturnLocation();

	public abstract void setLastKnownLocation(Location location);

	public abstract Location getLastKnownLocation();

	public abstract boolean teleportTo(ILocatable entity);
}
