package me.protocos.xteam.core;


public interface ITeamPlayerManager
{
	public abstract void logOn(ITeamPlayer player);
	public abstract void logOff(String name);
	public abstract ITeamPlayer getTeamPlayer(String name);
}
