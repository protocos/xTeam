package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.command.teamuser.Headquarters;

public interface ITeam
{
	public abstract String getName();
	public abstract Headquarters getHeadquarters();
	public abstract boolean addPlayer(ITeamPlayer player);
	public abstract ITeamPlayer getPlayer(String playerName);
	public abstract ITeamPlayer removePlayer(String playerName);
	public abstract boolean containsPlayer(String playerName);
	public abstract List<String> getTeamPlayers();
}
