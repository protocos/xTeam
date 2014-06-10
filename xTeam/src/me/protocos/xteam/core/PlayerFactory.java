package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.Property;
import me.protocos.xteam.data.PropertyList;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.data.translator.LongDataTranslator;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.OfflineTeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerFactory implements IPlayerFactory
{
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;
	private HashList<String, PropertyList> playerProperties;

	public PlayerFactory(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.bukkitUtil = teamPlugin.getBukkitUtil();
		this.playerProperties = new HashList<String, PropertyList>();
	}

	public List<TeamPlayer> getOnlinePlayers()
	{
		Player[] players = bukkitUtil.getOnlinePlayers();
		List<TeamPlayer> onlinePlayers = CommonUtil.emptyList(players.length);
		for (Player player : players)
		{
			onlinePlayers.add(getPlayer(player));
		}
		return onlinePlayers;
	}

	public List<OfflineTeamPlayer> getOfflinePlayers()
	{
		OfflinePlayer[] players = bukkitUtil.getOfflinePlayers();
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

	public ITeamPlayer getPlayer(String playerName)
	{
		if (isOnline(playerName))
			return teamPlayerWithValues(bukkitUtil.getPlayer(playerName));
		if (hasPlayedBefore(playerName))
			return offlineTeamPlayerWithValues(bukkitUtil.getOfflinePlayer(playerName));
		return offlinePlayerNeverPlayedBefore(playerName);
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
		TeamPlayer returnPlayer = new TeamPlayer(teamPlugin, player);
		String playerName = player.getName();
		returnPlayer.setLastAttacked(this.getLastAttacked(playerName));
		returnPlayer.setLastTeleported(this.getLastTeleported(playerName));
		returnPlayer.setReturnLocation(this.getReturnLocation(playerName));
		return returnPlayer;
	}

	private OfflineTeamPlayer offlineTeamPlayerWithValues(OfflinePlayer player)
	{
		OfflineTeamPlayer returnPlayer = new OfflineTeamPlayer(teamPlugin, player);
		String playerName = player.getName();
		returnPlayer.setLastAttacked(this.getLastAttacked(playerName));
		returnPlayer.setLastTeleported(this.getLastTeleported(playerName));
		returnPlayer.setReturnLocation(this.getReturnLocation(playerName));
		returnPlayer.setLastKnownLocation(this.getLastKnownLocation(playerName));
		return returnPlayer;
	}

	private ITeamPlayer offlinePlayerNeverPlayedBefore(String playerName)
	{
		return new OfflineTeamPlayer(teamPlugin, bukkitUtil.getOfflinePlayer(playerName));
	}

	private boolean isOnline(String playerName)
	{
		return bukkitUtil.getPlayer(playerName) != null;
	}

	private boolean hasPlayedBefore(String playerName)
	{
		return bukkitUtil.getOfflinePlayer(playerName).hasPlayedBefore();
	}

	@Override
	public Long getLastAttacked(String playerName)
	{
		this.ensurePlayer(playerName);
		return playerProperties.get(playerName).get("lastAttacked").getValueUsing(new LongDataTranslator());
	}

	@Override
	public Long getLastTeleported(String playerName)
	{
		this.ensurePlayer(playerName);
		return playerProperties.get(playerName).get("lastTeleported").getValueUsing(new LongDataTranslator());
	}

	@Override
	public Location getReturnLocation(String playerName)
	{
		this.ensurePlayer(playerName);
		return playerProperties.get(playerName).get("returnLocation").getValueUsing(new LocationDataTranslator(teamPlugin));
	}

	@Override
	public Location getLastKnownLocation(String playerName)
	{
		this.ensurePlayer(playerName);
		return playerProperties.get(playerName).get("lastKnownLocation").getValueUsing(new LocationDataTranslator(teamPlugin));
	}

	@Override
	public void setLastAttacked(ITeamPlayer player, Long lastAttacked)
	{
		this.ensurePlayer(player.getName());
		playerProperties.get(player.getName()).remove("lastAttacked");
		playerProperties.get(player.getName()).put(new Property("lastAttacked", lastAttacked, new LongDataTranslator()));
	}

	@Override
	public void setLastTeleported(ITeamPlayer player, Long lastTeleported)
	{
		this.ensurePlayer(player.getName());
		playerProperties.get(player.getName()).remove("lastTeleported");
		playerProperties.get(player.getName()).put(new Property("lastTeleported", lastTeleported, new LongDataTranslator()));
	}

	@Override
	public void setReturnLocation(ITeamPlayer player, Location returnLocation)
	{
		this.ensurePlayer(player.getName());
		playerProperties.get(player.getName()).remove("returnLocation");
		playerProperties.get(player.getName()).put(new Property("returnLocation", returnLocation, new LocationDataTranslator(teamPlugin)));
	}

	@Override
	public void setLastKnownLocation(ITeamPlayer player, Location lastKnownLocation)
	{
		this.ensurePlayer(player.getName());
		playerProperties.get(player.getName()).remove("lastKnownLocation");
		playerProperties.get(player.getName()).put(new Property("lastKnownLocation", lastKnownLocation, new LocationDataTranslator(teamPlugin)));
	}

	private void ensurePlayer(String name)
	{
		if (!playerProperties.containsKey(name))
		{
			PropertyList propertyList = new PropertyList();
			propertyList.put("name", name);
			propertyList.put("lastAttacked", "0");
			propertyList.put("lastTeleported", "0");
			propertyList.put("returnLocation", "");
			propertyList.put("lastKnownLocation", "");
			playerProperties.put(name, propertyList);
		}
	}

	@Override
	public void updateValues(PropertyList propertyList)
	{
		playerProperties.put(propertyList.get("name").getValue(), propertyList);
	}

	@Override
	public List<String> exportData()
	{
		List<String> exportTeams = CommonUtil.emptyList(playerProperties.size());
		for (PropertyList propertyList : playerProperties)
		{
			exportTeams.add(propertyList.toString());
		}
		return exportTeams;
	}
}
