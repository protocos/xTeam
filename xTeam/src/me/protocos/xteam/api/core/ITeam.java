package me.protocos.xteam.api.core;

import java.util.List;
import me.protocos.xteam.core.TeamHeadquarters;

public interface ITeam extends ITeamEntity, ILocatable
{
	public abstract boolean addPlayer(String player);
	public abstract boolean containsPlayer(String player);
	public abstract TeamHeadquarters getHeadquarters();
	public abstract String getName();
	public abstract List<String> getPlayers();
	public abstract boolean removePlayer(String player);
}
