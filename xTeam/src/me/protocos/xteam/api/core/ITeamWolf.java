package me.protocos.xteam.api.core;

import me.protocos.xteam.core.TeamPlayer;
import org.bukkit.entity.Entity;

public interface ITeamWolf extends ITeamEntity, Entity
{
	public abstract TeamPlayer getOwner();
	public abstract boolean hasOwner();
	public abstract double getHealth();
}
