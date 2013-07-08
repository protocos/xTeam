package me.protocos.xteam.core;


public interface ITeamPlayerManager
{
	public abstract ITeamPlayer getTeamPlayer(String name);
	public abstract void logOff(String name);
	public abstract void logOn(ITeamPlayer player);
}
