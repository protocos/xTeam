package me.protocos.xteam.api.core;

public interface ITeamWolf extends ITeamEntity
{
	public abstract ITeamPlayer getOwner();
	public abstract boolean hasOwner();
}
