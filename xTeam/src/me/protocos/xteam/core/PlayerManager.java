package me.protocos.xteam.core;

import java.util.HashMap;
import java.util.List;
import me.protocos.xteam.api.core.ITeamEntity;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerManager
{
	private static HashMap<String, Long> lastAttackedMap = CommonUtil.emptyHashMap();
	private static HashMap<String, Long> lastTeleportedMap = CommonUtil.emptyHashMap();
	private static HashMap<String, Location> returnLocationMap = CommonUtil.emptyHashMap();

	public PlayerManager()
	{
	}

	public static void clearData()
	{
		lastAttackedMap.clear();
		lastTeleportedMap.clear();
		returnLocationMap.clear();
	}

	public static TeamPlayer getPlayer(Player player)
	{
		if (player != null)
			return teamPlayerWithValues(player);
		return null;
	}

	public static OfflineTeamPlayer getPlayer(OfflinePlayer player)
	{
		if (player != null)
			return offlineTeamPlayerWithValues(player);
		return null;
	}

	public static ITeamPlayer getPlayer(String name)
	{
		Player player = Data.BUKKIT.getPlayer(name);
		if (player != null)
			return teamPlayerWithValues(player);
		OfflinePlayer offlinePlayer = Data.BUKKIT.getOfflinePlayer(name);
		if (offlinePlayer != null)
			return offlineTeamPlayerWithValues(offlinePlayer);
		return playerWithValues(name);
	}

	public static List<TeamPlayer> getOnlineTeammatesOf(ITeamEntity teamEntity)
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

	public static List<OfflineTeamPlayer> getOfflineTeammatesOf(ITeamEntity teamEntity)
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

	public static List<ITeamPlayer> getTeammatesOf(ITeamEntity teamEntity)
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

	private static TeamPlayer teamPlayerWithValues(Player player)
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

	private static OfflineTeamPlayer offlineTeamPlayerWithValues(OfflinePlayer player)
	{
		OfflineTeamPlayer returnPlayer = new OfflineTeamPlayer(player);
		String playerName = player.getName();
		returnPlayer.setLastAttacked(getLastAttacked(playerName));
		returnPlayer.setLastTeleported(getLastTeleported(playerName));
		returnPlayer.setReturnLocation(getReturnLocation(playerName));
		return returnPlayer;
	}

	private static ITeamPlayer playerWithValues(String playerName)
	{
		ITeamPlayer returnPlayer = getPlayer(playerName);
		if (returnPlayer != null)
		{
			returnPlayer.setLastAttacked(getLastAttacked(playerName));
			returnPlayer.setLastTeleported(getLastTeleported(playerName));
			returnPlayer.setReturnLocation(getReturnLocation(playerName));
			return returnPlayer;
		}
		return null;
	}

	public static Long getLastAttacked(String playerName)
	{
		if (!lastAttackedMap.containsKey(playerName))
			lastAttackedMap.put(playerName, 0L);
		return lastAttackedMap.get(playerName);
	}
	public static Long getLastTeleported(String playerName)
	{
		if (!lastTeleportedMap.containsKey(playerName))
			lastTeleportedMap.put(playerName, 0L);
		return lastTeleportedMap.get(playerName);
	}

	public static Location getReturnLocation(String playerName)
	{
		if (!returnLocationMap.containsKey(playerName))
			returnLocationMap.put(playerName, null);
		return returnLocationMap.get(playerName);
	}

	static void setLastAttacked(ITeamPlayer player, Long lastAttacked)
	{
		lastAttackedMap.put(player.getName(), lastAttacked);
	}

	static void setLastTeleported(ITeamPlayer player, Long lastTeleported)
	{
		lastTeleportedMap.put(player.getName(), lastTeleported);
	}

	static void setReturnLocation(ITeamPlayer player, Location returnLocation)
	{
		returnLocationMap.put(player.getName(), returnLocation);
	}
}
