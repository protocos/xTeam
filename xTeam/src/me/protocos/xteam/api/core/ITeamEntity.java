package me.protocos.xteam.api.core;

import java.util.List;
import me.protocos.xteam.core.OfflineTeamPlayer;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;

public interface ITeamEntity
{
	public abstract Team getTeam();
	public abstract String getName();
	public abstract boolean hasTeam();
	public abstract boolean isOnSameTeam(ITeamEntity entity);
	public abstract boolean isOnline();
	public abstract boolean isVulnerable();
	public abstract List<OfflineTeamPlayer> getOfflineTeammates();
	public abstract List<TeamPlayer> getOnlineTeammates();
	public abstract List<ITeamPlayer> getTeammates();
	public abstract boolean sendMessage(String message);
	public abstract String getPublicInfo();
	public abstract String getPrivateInfo();
}
