package me.protocos.xteam.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lib.PatPeter.SQLibrary.Database;
import me.protocos.xteam.xTeam;
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
	//	private static HashMap<String, Long> lastAttackedMap = CommonUtil.emptyHashMap();
	//	private static HashMap<String, Long> lastTeleportedMap = CommonUtil.emptyHashMap();
	//	private static HashMap<String, Location> returnLocationMap = CommonUtil.emptyHashMap();
	private Database db;

	public PlayerManager(Database db)
	{
		this.db = db;
		try
		{
			db.insert("CREATE TABLE IF NOT EXISTS player_data(name VARCHAR(17) PRIMARY KEY, lastAttacked BIGINT, lastTeleported BIGINT, returnLocation TEXT);");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
	}

	public void clear()
	{
		try
		{
			db.insert("UPDATE player_data SET lastAttacked=0,lastTeleported=0,returnLocation=null;");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		//		lastAttackedMap.clear();
		//		lastTeleportedMap.clear();
		//		returnLocationMap.clear();
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
		returnPlayer.setLastAttacked(this.getLastAttacked(playerName));
		returnPlayer.setLastTeleported(this.getLastTeleported(playerName));
		returnPlayer.setReturnLocation(this.getReturnLocation(playerName));
		return returnPlayer;
	}

	private OfflineTeamPlayer offlineTeamPlayerWithValues(OfflinePlayer player)
	{
		OfflineTeamPlayer returnPlayer = new OfflineTeamPlayer(player);
		String playerName = player.getName();
		returnPlayer.setLastAttacked(this.getLastAttacked(playerName));
		returnPlayer.setLastTeleported(this.getLastTeleported(playerName));
		returnPlayer.setReturnLocation(this.getReturnLocation(playerName));
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
			returnPlayer.setLastAttacked(this.getLastAttacked(playerName));
			returnPlayer.setLastTeleported(this.getLastTeleported(playerName));
			returnPlayer.setReturnLocation(this.getReturnLocation(playerName));
		}
		return returnPlayer;
	}

	public Long getLastAttacked(String playerName)
	{
		ResultSet set = select(playerName, "lastAttacked");
		long lastAttacked = 0L;
		try
		{
			lastAttacked = set.getLong(1);
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		return lastAttacked;
		//		if (!lastAttackedMap.containsKey(playerName))
		//			lastAttackedMap.put(playerName, 0L);
		//		return lastAttackedMap.get(playerName);
	}

	public Long getLastTeleported(String playerName)
	{
		ResultSet set = select(playerName, "lastTeleported");
		long lastTeleported = 0L;
		try
		{
			lastTeleported = set.getLong(1);
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		return lastTeleported;
		//		if (!lastTeleportedMap.containsKey(playerName))
		//			lastTeleportedMap.put(playerName, 0L);
		//		return lastTeleportedMap.get(playerName);
	}

	public Location getReturnLocation(String playerName)
	{
		ResultSet set = select(playerName, "returnLocation");
		Location returnLocation = null;
		try
		{
			returnLocation = parseLocation(set.getString(1));
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		return returnLocation;
		//		if (!returnLocationMap.containsKey(playerName))
		//			returnLocationMap.put(playerName, null);
		//		return returnLocationMap.get(playerName);
	}

	public void setLastAttacked(ITeamPlayer player, Long lastAttacked)
	{
		try
		{
			db.insert("UPDATE player_data SET lastAttacked=" + lastAttacked + " WHERE name='" + player.getName() + "';");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		//		lastAttackedMap.put(player.getName(), lastAttacked);
	}

	public void setLastTeleported(ITeamPlayer player, Long lastTeleported)
	{
		try
		{
			db.insert("UPDATE player_data SET lastTeleported=" + lastTeleported + " WHERE name='" + player.getName() + "';");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		//		lastTeleportedMap.put(player.getName(), lastTeleported);
	}

	public void setReturnLocation(ITeamPlayer player, Location returnLocation)
	{
		try
		{
			db.insert("UPDATE player_data SET returnLocation='" + locationData(returnLocation) + "' WHERE name='" + player.getName() + "';");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		//		returnLocationMap.put(player.getName(), returnLocation);
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

	private ResultSet select(String playerName, String variable)
	{
		ResultSet set = null;
		try
		{
			db.insert("INSERT INTO player_data(name, lastAttacked, lastTeleported) " +
					"SELECT '" + playerName + "',0,0 " +
					"WHERE NOT EXISTS (SELECT * FROM player_data WHERE name='" + playerName + "');");
			set = db.query("SELECT " + variable + " FROM player_data WHERE name='" + playerName + "';");
		}
		catch (SQLException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
		return set;
	}

	private String locationData(Location location)
	{
		if (location != null)
			return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getPitch() + "," + location.getYaw();
		return "";
	}

	private Location parseLocation(String location)
	{
		//Location{world=null,x=0.0,y=64.0,z=0.0,pitch=0.0,yaw=0.0}
		if (!"".equals(location) && location != null)
		{
			String[] data = location.split(",");
			Location parseLocation = BukkitUtil.getWorld(data[0]).getSpawnLocation();
			parseLocation.setX(Double.parseDouble(data[1]));
			parseLocation.setY(Double.parseDouble(data[2]));
			parseLocation.setZ(Double.parseDouble(data[3]));
			parseLocation.setPitch(Float.parseFloat(data[4]));
			parseLocation.setYaw(Float.parseFloat(data[5]));
			//			Location parseLocation = new Location(
			//					BukkitUtil.getWorld(data[0]),
			//					Double.parseDouble(data[1]),
			//					Double.parseDouble(data[2]),
			//					Double.parseDouble(data[3]),
			//					Float.parseFloat(data[4]),
			//					Float.parseFloat(data[5]));
			return parseLocation;
		}
		return null;
	}
}
