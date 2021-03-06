package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.data.IDataContainer;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.OfflineTeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.model.PropertyList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface IPlayerFactory extends IDataContainer
{
	public abstract ITeamPlayer getPlayer(String name);

	public abstract TeamPlayer getPlayer(Player player);

	public abstract OfflineTeamPlayer getPlayer(OfflinePlayer offlinePlayer);

	public abstract List<TeamPlayer> getOnlinePlayers();

	public abstract List<OfflineTeamPlayer> getOfflinePlayers();

	public abstract List<ITeamPlayer> getTeammatesOf(ITeamEntity teamEntity);

	public abstract List<TeamPlayer> getOnlineTeammatesOf(ITeamEntity teamEntity);

	public abstract List<OfflineTeamPlayer> getOfflineTeammatesOf(ITeamEntity teamEntity);

	public abstract void updateValues(PropertyList propertyList);

	public abstract PropertyList getPlayerPropertiesFor(String playerName);
}
