package me.protocos.xteam.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import me.protocos.xteam.xTeam;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TeamPlayer implements ITeamPlayer
{
	private String name;
	private OfflinePlayer offlinePlayer;
	private Player onlinePlayer;

	public TeamPlayer(String playerName)
	{
		name = playerName;
		onlinePlayer = Data.BUKKIT.getPlayer(playerName);
		offlinePlayer = Data.BUKKIT.getOfflinePlayer(playerName);
	}
	public TeamPlayer(Player player)
	{
		name = player.getName();
		onlinePlayer = player;
		offlinePlayer = Data.BUKKIT.getOfflinePlayer(name);
	}
	public TeamPlayer(OfflinePlayer player)
	{
		name = player.getName();
		onlinePlayer = Data.BUKKIT.getPlayer(name);
		offlinePlayer = player;
	}
	@Override
	public String getName()
	{
		return name;
	}
	@Override
	public OfflinePlayer getOfflinePlayer()
	{
		return offlinePlayer;
	}
	@Override
	public Player getOnlinePlayer()
	{
		return onlinePlayer;
	}
	@Override
	public Location getLocation()
	{
		if (isOnline())
		{
			return onlinePlayer.getLocation();
		}
		return null;
	}
	@Override
	public double getDistanceTo(ITeamEntity entity)
	{
		if (this.isOnline() && entity.isOnline())
			return getLocation().distance(entity.getLocation());
		return Double.MAX_VALUE;
	}
	@Override
	public int getHealth()
	{
		if (isOnline())
		{
			return onlinePlayer.getHealth();
		}
		return -1;
	}
	@Override
	public String getLastPlayed()
	{
		long milliSeconds = offlinePlayer.getLastPlayed();
		DateFormat formatter = new SimpleDateFormat("MMM d");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		String month_day = formatter.format(calendar.getTime());
		formatter = new SimpleDateFormat("h:mm a");
		String hour_minute_am_pm = formatter.format(calendar.getTime());
		return month_day + " @ " + hour_minute_am_pm;
	}
	@Override
	public int getRelativeX()
	{
		if (isOnline())
		{
			Location loc = getLocation();
			return (int) Math.round(loc.getX());
		}
		return 0;
	}
	@Override
	public int getRelativeY()
	{
		if (isOnline())
		{
			Location loc = getLocation();
			return (int) Math.round(loc.getY());
		}
		return 0;
	}
	@Override
	public int getRelativeZ()
	{
		if (isOnline())
		{
			Location loc = getLocation();
			return (int) Math.round(loc.getZ());
		}
		return 0;
	}
	@Override
	public Server getServer()
	{
		if (isOnline())
		{
			return onlinePlayer.getServer();
		}
		return null;
	}
	@Override
	public World getWorld()
	{
		if (isOnline())
		{
			return onlinePlayer.getWorld();
		}
		return null;
	}
	@Override
	public Team getTeam()
	{
		for (Team team : xTeam.tm.getAllTeams())
			if (team.contains(name))
				return team;
		return null;
	}
	@Override
	public boolean hasTeam()
	{
		return getTeam() != null;
	}
	@Override
	public boolean hasPermission(String permission)
	{
		if (isOnline())
		{
			return onlinePlayer.hasPermission(permission);
		}
		return false;
	}
	@Override
	public boolean hasPlayedBefore()
	{
		return offlinePlayer.hasPlayedBefore() || offlinePlayer.isOnline();
	}
	@Override
	public boolean isOnline()
	{
		if (hasPlayedBefore())
			return offlinePlayer.isOnline();
		return false;
	}
	@Override
	public boolean isOnSameTeam(ITeamEntity otherEntity)
	{
		if (hasTeam() && otherEntity.hasTeam())
		{
			return getTeam().equals(otherEntity.getTeam());
		}
		return false;
	}
	@Override
	public boolean isOp()
	{
		return offlinePlayer.isOp();
	}
	@Override
	public boolean isAdmin()
	{
		if (hasTeam())
		{
			return getTeam().getAdmins().contains(name);
		}
		return false;
	}
	@Override
	public boolean isLeader()
	{
		if (hasTeam())
		{
			return getTeam().getLeader().equals(name);
		}
		return false;
	}
	@Override
	public boolean sendMessage(String message)
	{
		if (isOnline())
		{
			onlinePlayer.sendMessage(message);
			return true;
		}
		return false;
	}
	@Override
	public boolean teleport(Location location)
	{
		if (isOnline())
		{
			return onlinePlayer.teleport(location);
		}
		return false;
	}
	@Override
	public boolean teleport(ITeamEntity entity)
	{
		if (isOnline() && entity.isOnline())
		{
			return onlinePlayer.teleport(entity.getLocation());
		}
		return false;
	}
	@Override
	public boolean equals(ITeamPlayer player)
	{
		return getName().equalsIgnoreCase(player.getName());
	}
	@Override
	public List<String> getTeammates()
	{
		List<String> mates = new ArrayList<String>();
		if (hasTeam())
		{
			for (String p : getTeam().getPlayers())
			{
				TeamPlayer mate = new TeamPlayer(p);
				if (!mate.isOnline() && !name.equals(p))
					mates.add(p);
			}
		}
		return mates;
	}
	@Override
	public List<String> getOfflineTeammates()
	{
		List<String> offlineMates = new ArrayList<String>();
		if (hasTeam())
		{
			for (String p : getTeam().getPlayers())
			{
				TeamPlayer mate = new TeamPlayer(p);
				if (!mate.isOnline() && !name.equals(p))
					offlineMates.add(p);
			}
		}
		return offlineMates;
	}
	@Override
	public List<String> getOnlineTeammates()
	{
		List<String> onlineMates = new ArrayList<String>();
		if (hasTeam())
		{
			for (String p : getTeam().getPlayers())
			{
				TeamPlayer mate = new TeamPlayer(p);
				if (mate.isOnline() && !name.equals(p))
					onlineMates.add(p);
			}
		}
		return onlineMates;
	}
	@Override
	public String toString()
	{
		String playerData = "";
		playerData += "name:" + getName();
		playerData += " hasPlayed:" + hasPlayedBefore();
		playerData += " team:" + (hasTeam() ? getTeam().getName() : "none");
		playerData += " admin:" + (isAdmin() ? "true" : "false");
		playerData += " leader:" + (isLeader() ? "true" : "false");
		return playerData;
	}
	@Override
	public boolean isEnemy(ITeamEntity entity)
	{
		if (this.isOnSameTeam(entity))
			return false;
		return true;
	}
}
