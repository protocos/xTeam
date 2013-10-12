package me.protocos.xteam.api.core;

import java.util.List;
import me.protocos.xteam.core.TeamPlayer;
import org.bukkit.entity.Entity;

public interface ITeamPlayer extends ITeamEntity, Entity
{
	public abstract String getName();
	public abstract double getHealth();
	public abstract boolean hasPlayedBefore();
	public abstract List<TeamPlayer> getOnlineTeammates();
}
