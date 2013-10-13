package me.protocos.xteam.command.action;

import java.util.HashMap;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamEntity;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoOnlineTeammatesException;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitScheduler;

public class TeleportAction
{
	private TeleportAction teleporter;
	private BukkitScheduler taskScheduler;

	private HashMap<ITeamEntity, Integer> currentTaskIDs = new HashMap<ITeamEntity, Integer>();
	private HashMap<ITeamEntity, Location> returnLocations = new HashMap<ITeamEntity, Location>();
	private HashMap<ITeamEntity, Integer> countWaitTime = new HashMap<ITeamEntity, Integer>();

	private TeleportAction()
	{
	}

	public boolean hasCurrentTask(ITeamEntity entity)
	{
		return currentTaskIDs.containsKey(entity);
	}

	public TeleportAction getInstance()
	{
		if (teleporter == null)
		{
			teleporter = new TeleportAction();
			taskScheduler = Bukkit.getScheduler();
		}
		return teleporter;
	}

	public void teleport(ITeamPlayer fromEntity, ITeamPlayer toEntity)
	{
		if (fromEntity.isVulnerable() && fromEntity.isTeleportable())
		{
			if (hasNearbyEnemies(fromEntity))
				delayTeleportTo(fromEntity, toEntity);
			else
				teleportTo(fromEntity, toEntity);
		}
	}

	private void delayTeleportTo(final ITeamPlayer fromEntity, final ITeamPlayer toEntity)
	{
		// TODO Auto-generated method stub
		countWaitTime.put(fromEntity, 0);
		final Location savedLocation = fromEntity.getLocation();
		fromEntity.sendMessage(ChatColor.RED + "You cannot teleport with enemies nearby");
		fromEntity.sendMessage(ChatColor.RED + "You must wait 10 seconds");
		Runnable teleportWait = new TeleportWait(fromEntity, toEntity, savedLocation);
		currentTaskIDs.put(fromEntity, taskScheduler.scheduleSyncRepeatingTask(xTeam.getSelf(), teleportWait, BukkitUtil.LONG_ZERO, 2L));
	}

	private void teleportTo(final ITeamPlayer fromEntity, final ITeamPlayer toEntity)
	{
		returnLocations.put(fromEntity, fromEntity.getLocation());
		Runnable teleRefreshMessage = new TeleportRefreshMessage(fromEntity);
		currentTaskIDs.put(fromEntity, taskScheduler.scheduleSyncDelayedTask(xTeam.getSelf(), teleRefreshMessage, Data.REFRESH_DELAY * BukkitUtil.ONE_SECOND_IN_TICKS));
		fromEntity.teleportTo(toEntity);
		fromEntity.sendMessage("You've been teleported to " + ChatColor.GREEN + toEntity.getName());
		Runnable refreshPlayerNames = new RefreshPlayerNames();
		currentTaskIDs.put(fromEntity, taskScheduler.scheduleSyncDelayedTask(xTeam.getSelf(), refreshPlayerNames, BukkitUtil.ONE_SECOND_IN_TICKS));
	}
	private boolean hasNearbyEnemies(ITeamPlayer entity)
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
				ITeamPlayer otherPlayer = xTeam.pm.getPlayer(unknownPlayer);
				if (!entity.isOnSameTeam(otherPlayer))
					return true;
			}
		}
		return false;
	}

	public static ITeamPlayer getClosestTeammate(ITeamEntity entity) throws TeamException
	{
		List<ITeamPlayer> teammates = entity.getTeammates();
		if (teammates.isEmpty())
			throw new TeamPlayerHasNoOnlineTeammatesException();
		ITeamPlayer closest = teammates.get(0);
		for (ITeamPlayer e : teammates)
		{
			if (e.isOnline() && e.getDistanceTo(entity) < e.getDistanceTo(closest))
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
		private ITeamPlayer fromEntity;

		public TeleportRefreshMessage(ITeamPlayer fromEntity)
		{
			this.fromEntity = fromEntity;
		}

		@Override
		public void run()
		{
			if (Data.REFRESH_DELAY > 0)
				fromEntity.sendMessage(ChatColor.GREEN + "Teleport refreshed");
		}
	}

	class RefreshPlayerNames implements Runnable
	{
		@Override
		public void run()
		{
			Functions.updatePlayers();
		}
	}

	class TeleportWait implements Runnable
	{
		private ITeamPlayer fromEntity, toEntity;
		private Location previousLocation;

		public TeleportWait(ITeamPlayer fromEntity, ITeamPlayer toEntity, Location previousLocation)
		{
			this.fromEntity = fromEntity;
			this.toEntity = toEntity;
			this.previousLocation = previousLocation;
		}

		@Override
		public void run()
		{
			if (Data.damagedByPlayer.contains(fromEntity.getEntityName()))
			{
				fromEntity.sendMessage(ChatColor.RED + "Teleport cancelled! You were attacked!");
				Data.damagedByPlayer.remove(fromEntity.getEntityName());
				countWaitTime.remove(fromEntity);
				taskScheduler.cancelTask(currentTaskIDs.remove(fromEntity));
			}
			Location loc = fromEntity.getLocation();
			if (loc.getBlockX() != previousLocation.getBlockX() || loc.getBlockY() != previousLocation.getBlockY() || loc.getBlockZ() != previousLocation.getBlockZ())
			{
				fromEntity.sendMessage(ChatColor.RED + "Teleport cancelled! You moved!");
				countWaitTime.remove(fromEntity);
				taskScheduler.cancelTask(currentTaskIDs.remove(fromEntity));
			}
			if (currentTaskIDs.containsKey(fromEntity))
			{
				int temp = countWaitTime.remove(fromEntity);
				if (temp == Data.TELE_DELAY * 10)
				{
					teleportTo(fromEntity, toEntity);
					taskScheduler.cancelTask(currentTaskIDs.remove(fromEntity));
				}
				temp++;
				countWaitTime.put(fromEntity, temp);
			}
		}
	}
}
