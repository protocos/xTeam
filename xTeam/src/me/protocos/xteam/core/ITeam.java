package me.protocos.xteam.core;

import java.util.List;

public interface ITeam
{
	public abstract String getName();
	public abstract TeamHeadquarters getHeadquarters();
	public abstract boolean addPlayer(String player);
	public abstract boolean containsPlayer(String player);
	public abstract boolean removePlayer(String player);
	public abstract void sendMessageToTeam(String message);
	public abstract List<String> getPlayers();
	public abstract List<String> getOnlinePlayers();
	public abstract List<String> getOfflinePlayers();
}
