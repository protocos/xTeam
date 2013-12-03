package me.protocos.xteam.core;

import java.util.HashMap;
import java.util.List;
import me.protocos.xteam.api.core.IPlayerManager;
import me.protocos.xteam.api.entity.ITeamEntity;
import me.protocos.xteam.api.entity.ITeamPlayer;
import me.protocos.xteam.entity.OfflineTeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerManager implements IPlayerManager
{
	private static HashMap<String, Long> lastAttackedMap = CommonUtil.emptyHashMap();
	private static HashMap<String, Long> lastTeleportedMap = CommonUtil.emptyHashMap();
	private static HashMap<String, Location> returnLocationMap = CommonUtil.emptyHashMap();

	public PlayerManager()
	{
	}

	public void clear()
	{
		lastAttackedMap.clear();
		lastTeleportedMap.clear();
		returnLocationMap.clear();
	}

	public List<TeamPlayer> getOnlinePlayers()
	{
		Player[] players = BukkitUtil.getOnlinePlayers();
		List<TeamPlayer> onlinePlayers = CommonUtil.emptyList(players.length);
		for (Player player : players)
		{
			onlinePlayers.add(getPlayer(player));
		}
		return onlinePlayers;
	}

	public List<OfflineTeamPlayer> getOfflinePlayers()
	{
		OfflinePlayer[] players = BukkitUtil.getOfflinePlayers();
		List<OfflineTeamPlayer> offlinePlayers = CommonUtil.emptyList(players.length);
		for (OfflinePlayer player : players)
		{
			if (!player.isOnline())
				offlinePlayers.add(getPlayer(player));
		}
		return offlinePlayers;
	}

	public TeamPlayer getPlayer(Player player)
	{
		if (player != null)
			return teamPlayerWithValues(player);
		return null;
	}

	public OfflineTeamPlayer getPlayer(OfflinePlayer player)
	{
		if (player != null)
			return offlineTeamPlayerWithValues(player);
		return null;
	}

	public ITeamPlayer getPlayer(String name)
	{
		Player player = BukkitUtil.getPlayer(name);
		if (player != null)
			return teamPlayerWithValues(player);
		OfflinePlayer offlinePlayer = BukkitUtil.getOfflinePlayer(name);
		if (offlinePlayer != null)
			return offlineTeamPlayerWithValues(offlinePlayer);
		return playerWithValues(name);
	}

	public List<TeamPlayer> getOnlineTeammatesOf(ITeamEntity teamEntity)
	{
		List<ITeamPlayer> onlineMates = CommonUtil.emptyList();
		if (teamEntity.hasTeam())
		{
			for (String p : teamEntity.getTeam().getPlayers())
			{
				ITeamPlayer mate = getPlayer(p);
				if (mate.isOnline())
					onlineMates.add(mate);
			}
		}
		if (teamEntity instanceof ITeamPlayer)
			onlineMates.remove(teamEntity);
		return CommonUtil.subListOfType(onlineMates, TeamPlayer.class);
	}

	public List<OfflineTeamPlayer> getOfflineTeammatesOf(ITeamEntity teamEntity)
	{
		List<ITeamPlayer> offlineMates = CommonUtil.emptyList();
		if (teamEntity.hasTeam())
		{
			for (String p : teamEntity.getTeam().getPlayers())
			{
				ITeamPlayer mate = getPlayer(p);
				if (!mate.isOnline())
					offlineMates.add(mate);
			}
		}
		if (teamEntity instanceof ITeamPlayer)
			offlineMates.remove(teamEntity);
		return CommonUtil.subListOfType(offlineMates, OfflineTeamPlayer.class);
	}

	public List<ITeamPlayer> getTeammatesOf(ITeamEntity teamEntity)
	{
		List<ITeamPlayer> mates = CommonUtil.emptyList();
		if (teamEntity.hasTeam())
		{
			for (String p : teamEntity.getTeam().getPlayers())
			{
				ITeamPlayer mate = getPlayer(p);
				if (!teamEntity.getName().equals(p))
					mates.add(mate);
			}
		}
		if (teamEntity instanceof ITeamPlayer)
			mates.remove(teamEntity);
		return mates;
	}

	private TeamPlayer teamPlayerWithValues(Player player)
	{
		TeamPlayer returnPlayer = new TeamPlayer(player);
		String playerName = player.getName();
		Long lastAttacked = getLastAttacked(playerName);
		returnPlayer.setLastAttacked(lastAttacked);
		//TODO when you setLastAttacked(), it saves last attacked, last teleported, and last return location, which are null until they are set = catch 22
		Long lastTeleported = getLastTeleported(playerName);
		returnPlayer.setLastTeleported(lastTeleported);
		Location returnLocation = getReturnLocation(playerName);
		returnPlayer.setReturnLocation(returnLocation);
		return returnPlayer;
	}

	private OfflineTeamPlayer offlineTeamPlayerWithValues(OfflinePlayer player)
	{
		OfflineTeamPlayer returnPlayer = new OfflineTeamPlayer(player);
		String playerName = player.getName();
		returnPlayer.setLastAttacked(getLastAttacked(playerName));
		returnPlayer.setLastTeleported(getLastTeleported(playerName));
		returnPlayer.setReturnLocation(getReturnLocation(playerName));
		return returnPlayer;
	}

	private ITeamPlayer playerWithValues(String playerName)
	{
		ITeamPlayer returnPlayer = null;
		if (BukkitUtil.getPlayer(playerName) != null)
			returnPlayer = getPlayer(BukkitUtil.getPlayer(playerName));
		else if (BukkitUtil.getOfflinePlayer(playerName) != null)
			returnPlayer = getPlayer(BukkitUtil.getOfflinePlayer(playerName));
		if (returnPlayer != null)
		{
			returnPlayer.setLastAttacked(getLastAttacked(playerName));
			returnPlayer.setLastTeleported(getLastTeleported(playerName));
			returnPlayer.setReturnLocation(getReturnLocation(playerName));
		}
		return returnPlayer;
	}

	public Long getLastAttacked(String playerName)
	{
		if (!lastAttackedMap.containsKey(playerName))
			lastAttackedMap.put(playerName, 0L);
		return lastAttackedMap.get(playerName);
	}

	public Long getLastTeleported(String playerName)
	{
		if (!lastTeleportedMap.containsKey(playerName))
			lastTeleportedMap.put(playerName, 0L);
		return lastTeleportedMap.get(playerName);
	}

	public Location getReturnLocation(String playerName)
	{
		if (!returnLocationMap.containsKey(playerName))
			returnLocationMap.put(playerName, null);
		return returnLocationMap.get(playerName);
	}

	public void setLastAttacked(ITeamPlayer player, Long lastAttacked)
	{
		lastAttackedMap.put(player.getName(), lastAttacked);
	}

	public void setLastTeleported(ITeamPlayer player, Long lastTeleported)
	{
		lastTeleportedMap.put(player.getName(), lastTeleported);
	}

	public void setReturnLocation(ITeamPlayer player, Location returnLocation)
	{
		returnLocationMap.put(player.getName(), returnLocation);
	}

	public String toString()
	{
		String output = "";
		List<ITeamPlayer> players = CommonUtil.emptyList();
		players.addAll(getOnlinePlayers());
		players.addAll(getOfflinePlayers());
		for (ITeamPlayer player : players)
			output += player.getName() + (player.isOnline() ? ChatColorUtil.positiveMessage(" online") : ChatColorUtil.negativeMessage(" offline")) + "\n";
		return output.trim();
	}
}
