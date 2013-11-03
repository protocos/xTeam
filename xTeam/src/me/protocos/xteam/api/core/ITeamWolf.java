package me.protocos.xteam.api.core;

import org.bukkit.entity.Entity;

public interface ITeamWolf extends ITeamEntity, ILocatable, Entity
{
	public abstract ITeamPlayer getOwner();
	public abstract boolean hasOwner();
	public abstract double getHealth();
}
