package me.protocos.xteam.core;


public interface ITeamWolf extends ITeamEntity
{
	public abstract boolean equals(ITeamWolf otherWolf);
	public abstract ITeamPlayer getOwner();
	public abstract boolean hasOwner();
}
