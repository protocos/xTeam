package me.protocos.xteam.entity;

import org.bukkit.entity.Entity;

public interface ITeamWolf extends ITeamEntity, Entity
{
	public abstract ITeamPlayer getOwner();

	public abstract boolean hasOwner();

	public abstract double getHealth();
}
