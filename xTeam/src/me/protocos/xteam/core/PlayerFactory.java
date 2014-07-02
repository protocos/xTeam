package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.PropertyList;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.data.translator.LongDataTranslator;
import me.protocos.xteam.data.translator.StringDataTranslator;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.OfflineTeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
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

	public TeamPlayer getPlayer(Player player)
	{
		if (player != null)
			return teamPlayerWithValues(player);
		return null;
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

	public OfflineTeamPlayer getPlayer(OfflinePlayer player)
	{
		if (player != null)
			return offlineTeamPlayerWithValues(player);
		return null;
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

	private TeamPlayer teamPlayerWithValues(Player player)
	{
		return new TeamPlayer(teamPlugin, player);
	}

	private OfflineTeamPlayer offlineTeamPlayerWithValues(OfflinePlayer player)
	{
		return new OfflineTeamPlayer(teamPlugin, player);
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

	public ITeamPlayer getPlayer(String playerName)
	{
		if (isOnline(playerName))
			return teamPlayerWithValues(bukkitUtil.getPlayer(playerName));
		if (hasPlayedBefore(playerName))
			return offlineTeamPlayerWithValues(bukkitUtil.getOfflinePlayer(playerName));
		return offlinePlayerNeverPlayedBefore(playerName);
	}

	public List<ITeamPlayer> getTeammatesOf(ITeamEntity teamEntity)
	{
		List<ITeamPlayer> mates = CommonUtil.emptyList();
		if (teamEntity.hasTeam())
		{
			for (String p : teamEntity.getTeam().getPlayers())
			{
				ITeamPlayer mate = this.getPlayer(p);
				if (!teamEntity.getName().equals(p))
					mates.add(mate);
			}
		}
		mates.remove(teamEntity);
		return mates;
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
		offlineMates.remove(teamEntity);
		return CommonUtil.subListOfType(offlineMates, OfflineTeamPlayer.class);
	}

	@Override
	public PropertyList getPlayerPropertiesFor(String playerName)
	{
		this.ensurePlayer(playerName);
		return this.playerProperties.get(playerName);
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
			playerProperties.put(name, propertyList);
		}
		if (!playerProperties.get(name).containsKey("lastKnownLocation"))
			playerProperties.get(name).put("lastKnownLocation", "");
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

	public static PropertyList generatePlayerFromProperties(TeamPlugin teamPlugin, String properties)
	{
		PropertyList list = PropertyList.fromString(properties);
		PropertyList propertyList = new PropertyList();
		propertyList.put("name", new StringDataTranslator().decompile(list.getAsType("name", new StringDataTranslator())));
		propertyList.put("lastAttacked", new LongDataTranslator().decompile(list.getAsType("lastAttacked", new LongDataTranslator())));
		propertyList.put("lastTeleported", new LongDataTranslator().decompile(list.getAsType("lastTeleported", new LongDataTranslator())));
		propertyList.put("returnLocation", new LocationDataTranslator(teamPlugin).decompile(list.getAsType("returnLocation", new LocationDataTranslator(teamPlugin))));
		propertyList.put("lastKnownLocation", new LocationDataTranslator(teamPlugin).decompile(list.getAsType("lastKnownLocation", new LocationDataTranslator(teamPlugin))));
		return propertyList;
	}
}
