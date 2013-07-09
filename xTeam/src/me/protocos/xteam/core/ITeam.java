package me.protocos.xteam.core;

import java.util.List;

public interface ITeam
{
	public abstract boolean addPlayer(String player);
	public abstract boolean containsPlayer(String player);
	public abstract TeamHeadquarters getHeadquarters();
	public abstract String getName();
	public abstract List<String> getOfflinePlayers();
	public abstract List<String> getOnlinePlayers();
	public abstract List<String> getPlayers();
	public abstract boolean removePlayer(String player);
	public abstract void sendMessage(String message);
}
