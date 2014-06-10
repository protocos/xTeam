package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.data.IDataContainer;
import me.protocos.xteam.data.PropertyList;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.OfflineTeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
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

	//	public abstract void setReturnLocation(ITeamPlayer teamPlayer, Location returnLocation);
	//
	//	public abstract Location getReturnLocation(String name);
	//
	//	public abstract void setLastKnownLocation(ITeamPlayer teamPlayer, Location lastKnownLocation);
	//
	//	public abstract Location getLastKnownLocation(String name);
	//
	//	public abstract void setLastAttacked(ITeamPlayer teamPlayer, Long lastAttacked);
	//
	//	public abstract Long getLastAttacked(String name);
	//
	//	public abstract void setLastTeleported(ITeamPlayer teamPlayer, Long lastTeleported);
	//
	//	public abstract Long getLastTeleported(String name);

	public abstract void updateValues(PropertyList propertyList);

	public abstract PropertyList getPlayerPropertiesFor(String playerName);
}
