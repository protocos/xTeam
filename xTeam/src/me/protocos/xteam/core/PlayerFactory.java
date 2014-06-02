package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.IDataManager;
import me.protocos.xteam.data.PlayerFlatFile;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.data.translator.LongDataTranslator;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.OfflineTeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerFactory implements IPlayerManager
{
	private TeamPlugin teamPlugin;
	private IDataManager dataManager;
	private BukkitUtil bukkitUtil;

	public PlayerFactory(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.dataManager = new PlayerFlatFile(teamPlugin);
		this.bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Override
	public void setDataManager(IDataManager dataManager)
	{
		if (this.dataManager.isOpen())
			this.dataManager.close();
		this.dataManager = dataManager;
		this.dataManager.open();
	}

	@Override
	public void open()
	{
		this.dataManager.open();
		this.dataManager.read();
	}

	@Override
	public void close()
	{
		this.dataManager.write();
		this.dataManager.close();
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

	public ITeamPlayer getPlayer(String name)
	{
		Player player = bukkitUtil.getPlayer(name);
		if (player != null)
			return teamPlayerWithValues(player);
		OfflinePlayer offlinePlayer = bukkitUtil.getOfflinePlayer(name);
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
		return returnPlayer;
	}

	private ITeamPlayer playerWithValues(String playerName)
	{
		ITeamPlayer returnPlayer = null;
		if (bukkitUtil.getPlayer(playerName) != null)
			returnPlayer = getPlayer(bukkitUtil.getPlayer(playerName));
		else if (bukkitUtil.getOfflinePlayer(playerName) != null)
			returnPlayer = getPlayer(bukkitUtil.getOfflinePlayer(playerName));
		if (returnPlayer != null)
		{
			returnPlayer.setLastAttacked(this.getLastAttacked(playerName));
			returnPlayer.setLastTeleported(this.getLastTeleported(playerName));
			returnPlayer.setReturnLocation(this.getReturnLocation(playerName));
		}
		return returnPlayer;
	}

	public Long getLastAttacked(String playerName)
	{
		return dataManager.getVariable(playerName, "lastAttacked", new LongDataTranslator());
	}

	public Long getLastTeleported(String playerName)
	{
		return dataManager.getVariable(playerName, "lastTeleported", new LongDataTranslator());
	}

	public Location getReturnLocation(String playerName)
	{
		return dataManager.getVariable(playerName, "returnLocation", new LocationDataTranslator(teamPlugin));
	}

	public void setLastAttacked(ITeamPlayer player, Long lastAttacked)
	{
		dataManager.setVariable(player.getName(), "lastAttacked", lastAttacked, new LongDataTranslator());
	}

	public void setLastTeleported(ITeamPlayer player, Long lastTeleported)
	{
		dataManager.setVariable(player.getName(), "lastTeleported", lastTeleported, new LongDataTranslator());
	}

	public void setReturnLocation(ITeamPlayer player, Location returnLocation)
	{
		dataManager.setVariable(player.getName(), "returnLocation", returnLocation, new LocationDataTranslator(teamPlugin));
	}

	public String toString()
	{
		String output = "";
		List<ITeamPlayer> players = CommonUtil.emptyList();
		players.addAll(getOnlinePlayers());
		players.addAll(getOfflinePlayers());
		for (ITeamPlayer player : players)
			output += player.getName() + (player.isOnline() ? MessageUtil.positiveMessage(" online") : MessageUtil.negativeMessage(" offline")) + "\n";
		return output.trim();
	}
}
