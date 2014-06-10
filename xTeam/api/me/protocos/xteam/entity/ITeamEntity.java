package me.protocos.xteam.entity;

import java.util.List;
import me.protocos.xteam.message.IMessageRecipient;

public interface ITeamEntity extends IMessageRecipient
{
	public abstract ITeam getTeam();

	public abstract String getName();

	public abstract boolean hasTeam();

	public abstract boolean isOnSameTeam(ITeamEntity entity);

	public abstract boolean isOnline();

	public abstract boolean isVulnerable();

	public abstract List<ITeamPlayer> getTeammates();

	public abstract List<TeamPlayer> getOnlineTeammates();

	public abstract List<OfflineTeamPlayer> getOfflineTeammates();

	public abstract String getInfoFor(ITeamEntity entity);
}
