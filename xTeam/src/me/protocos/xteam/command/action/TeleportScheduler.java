package me.protocos.xteam.command.action;

import java.util.HashMap;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoOnlineTeammatesException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitScheduler;

public class TeleportScheduler
{
	private static TeleportScheduler teleporter;
	private static BukkitScheduler taskScheduler;

	private HashMap<TeamPlayer, Integer> currentTaskIDs = new HashMap<TeamPlayer, Integer>();
	private HashMap<TeamPlayer, Integer> countWaitTime = new HashMap<TeamPlayer, Integer>();

	private TeleportScheduler()
	{
	}

	public void clearTasks()
	{
		currentTaskIDs.clear();
	}
	public HashMap<TeamPlayer, Integer> getCurrentTasks()
	{
		return currentTaskIDs;
	}

	public Integer setCurrentTask(TeamPlayer teamPlayer, Integer ID)
	{
		return currentTaskIDs.put(teamPlayer, ID);
	}

	public boolean hasCurrentTask(TeamPlayer entity)
	{
		return currentTaskIDs.containsKey(entity);
	}

	public Integer removeCurrentTask(TeamPlayer teamPlayer)
	{
		return currentTaskIDs.remove(teamPlayer);
	}
	public static TeleportScheduler getInstance()
	{
		if (teleporter == null)
		{
			teleporter = new TeleportScheduler();
			taskScheduler = Data.BUKKIT.getScheduler();
		}
		return teleporter;
	}

	public void teleport(TeamPlayer fromPlayer, TeamPlayer toPlayer)
	{
		if (fromPlayer.isVulnerable() && toPlayer.isOnline())
		{
			if (hasNearbyEnemies(fromPlayer))
				delayTeleportTo(fromPlayer, toPlayer);
			else
				teleportTo(fromPlayer, toPlayer);
		}
	}

	public void teleport(TeamPlayer teamPlayer, Location toLocation)
	{
		if (teamPlayer.isVulnerable())
		{
			if (hasNearbyEnemies(teamPlayer))
				delayTeleportTo(teamPlayer, toLocation);
			else
				teleportTo(teamPlayer, toLocation);
		}
	}

	private void delayTeleportTo(final TeamPlayer fromPlayer, final TeamPlayer toEntity)
	{
		delayTeleportTo(fromPlayer, toEntity.getLocation());
	}

	private void delayTeleportTo(final TeamPlayer teamPlayer, final Location toLocation)
	{
		countWaitTime.put(teamPlayer, 0);
		final Location currentLocation = teamPlayer.getLocation();
		teamPlayer.sendMessage(ChatColor.RED + "You cannot teleport with enemies nearby");
		teamPlayer.sendMessage(ChatColor.RED + "You must wait " + Data.TELE_DELAY + " seconds");
		Runnable teleportWait = new TeleportWait(teamPlayer, toLocation, currentLocation);
		setCurrentTask(teamPlayer, taskScheduler.scheduleSyncRepeatingTask(xTeam.getSelf(), teleportWait, CommonUtil.LONG_ZERO, 2L));
	}

	private void teleportTo(final TeamPlayer fromEntity, final TeamPlayer toEntity)
	{
		teleportTo(fromEntity, toEntity.getLocation());
		fromEntity.sendMessage("You've been teleported to " + ChatColor.GREEN + toEntity.getName());
	}

	private void teleportTo(final TeamPlayer teamPlayer, final Location location)
	{
		if (location.equals(teamPlayer.getReturnLocation()))
		{
			teamPlayer.removeReturnLocation();
		}
		else
		{
			teamPlayer.setReturnLocation(teamPlayer.getLocation());
			Runnable teleRefreshMessage = new TeleportRefreshMessage(teamPlayer);
			taskScheduler.scheduleSyncDelayedTask(xTeam.getSelf(), teleRefreshMessage, Data.TELE_REFRESH_DELAY * BukkitUtil.ONE_SECOND_IN_TICKS);
			teamPlayer.setLastTeleported(System.currentTimeMillis());
		}
		teamPlayer.teleport(location);
	}

	private boolean hasNearbyEnemies(TeamPlayer entity)
	{
		List<Entity> entities = entity.getNearbyEntities(Data.ENEMY_PROX);
		for (Entity e : entities)
		{
			if (e instanceof Monster
					|| e instanceof Ghast
					|| e instanceof EnderDragon
					|| e instanceof Slime)
			{
				return true;
			}
			else if (e instanceof Golem
					|| e instanceof Wolf)
			{
				if (aggroCheck(entity, e))
					return true;
			}
			else if (e instanceof Player)
			{
				Player unknownPlayer = (Player) e;
				ITeamPlayer otherPlayer = PlayerManager.getPlayer(unknownPlayer);
				if (!entity.isOnSameTeam(otherPlayer))
					return true;
			}
		}
		return false;
	}

	public TeamPlayer getClosestTeammate(TeamPlayer teamPlayer) throws TeamException
	{
		List<TeamPlayer> teammates = teamPlayer.getOnlineTeammates();
		if (teammates.isEmpty())
			throw new TeamPlayerHasNoOnlineTeammatesException();
		TeamPlayer closest = teammates.get(0);
		for (TeamPlayer e : teammates)
		{
			if (e.isOnline() && e.getDistanceTo(teamPlayer) < e.getDistanceTo(closest))
			{
				closest = e;
			}
		}
		return closest;
	}

	private boolean aggroCheck(Entity entity, Entity creature)
	{
		if (creature instanceof Creature)
		{
			Entity target = ((Creature) creature).getTarget();
			if (entity.equals(target))
				return true;
		}
		return false;
	}

	class TeleportRefreshMessage implements Runnable
	{
		private TeamPlayer fromEntity;

		public TeleportRefreshMessage(TeamPlayer fromEntity)
		{
			this.fromEntity = fromEntity;
		}

		@Override
		public void run()
		{
			if (Data.TELE_REFRESH_DELAY > 0)
				fromEntity.sendMessage(ChatColor.GREEN + "Teleport refreshed");
		}
	}

	class TeleportWait implements Runnable
	{
		private TeamPlayer fromEntity;
		private Location toLocation;
		private Location previousLocation;

		public TeleportWait(TeamPlayer fromEntity, Location toLocation, Location currentLocation)
		{
			this.fromEntity = fromEntity;
			this.toLocation = toLocation;
			this.previousLocation = currentLocation;
		}

		@Override
		public void run()
		{
			if ((System.currentTimeMillis() - fromEntity.getLastAttacked()) / 1000 < Data.LAST_ATTACKED_DELAY)
			{
				fromEntity.sendMessage(ChatColor.RED + "Teleport cancelled! You were attacked!");
				countWaitTime.remove(fromEntity);
				removeCurrentTask(fromEntity);
			}
			Location loc = fromEntity.getLocation();
			if (loc.getBlockX() != previousLocation.getBlockX() || loc.getBlockY() != previousLocation.getBlockY() || loc.getBlockZ() != previousLocation.getBlockZ())
			{
				fromEntity.sendMessage(ChatColor.RED + "Teleport cancelled! You moved!");
				countWaitTime.remove(fromEntity);
				removeCurrentTask(fromEntity);
			}
			if (hasCurrentTask(fromEntity))
			{
				int temp = countWaitTime.remove(fromEntity);
				if (temp == Data.TELE_DELAY * 10)
				{
					teleportTo(fromEntity, toLocation);
					removeCurrentTask(fromEntity);
				}
				temp++;
				countWaitTime.put(fromEntity, temp);
			}
		}
	}
}
