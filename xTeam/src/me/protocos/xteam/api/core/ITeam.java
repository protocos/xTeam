package me.protocos.xteam.api.core;

import java.util.List;
import me.protocos.xteam.core.Headquarters;
import org.bukkit.Location;

public interface ITeam extends ITeamEntity, ILocatable
{
	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setTag(String tag);

	public abstract String getTag();

	public abstract boolean hasTag();

	public abstract void setHeadquarters(Headquarters headquarters);

	public abstract Headquarters getHeadquarters();

	public abstract boolean hasHeadquarters();

	public abstract boolean addPlayer(String player);

	public abstract boolean containsPlayer(String player);

	public abstract boolean removePlayer(String player);

	public abstract boolean isEmpty();

	public abstract void promote(String player);

	public abstract void demote(String player);

	public abstract void setOpenJoining(boolean open);

	public abstract boolean isOpenJoining();

	public abstract int size();

	public abstract void setPlayers(List<String> players);

	public abstract List<String> getPlayers();

	public abstract List<String> getAdmins();

	public abstract void setLeader(String playerName);

	public abstract String getLeader();

	public abstract void setDefaultTeam(boolean defaultTeam);

	public abstract boolean isDefaultTeam();

	public abstract void setRally(Location location);

	public abstract Location getRally();

	public abstract boolean hasRally();

	public abstract void setTimeLastSet(long currentTimeMillis);

	public abstract long getTimeLastSet();
}
