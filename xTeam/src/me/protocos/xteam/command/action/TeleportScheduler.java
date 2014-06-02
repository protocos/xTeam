package me.protocos.xteam.command.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerManager;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.exception.TeamPlayerHasNoOnlineTeammatesException;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitScheduler;

public class TeleportScheduler
{
	private HashMap<TeamPlayer, Integer> currentTaskIDs = new HashMap<TeamPlayer, Integer>();
	private HashMap<TeamPlayer, Integer> countWaitTime = new HashMap<TeamPlayer, Integer>();
	private HashSet<String> rallyUsed = new HashSet<String>();

	private TeamPlugin teamPlugin;
	private IPlayerManager playerFactory;
	private BukkitScheduler bukkitScheduler;

	public TeleportScheduler(TeamPlugin teamPlugin, IPlayerManager playerFactory, BukkitScheduler bukkitScheduler)
	{
		this.teamPlugin = teamPlugin;
		this.playerFactory = playerFactory;
		this.bukkitScheduler = bukkitScheduler;
	}

	public void clearTasks()
	{
		currentTaskIDs.clear();
	}

	public HashMap<TeamPlayer, Integer> getCurrentTasks()
	{
		return currentTaskIDs;
	}

	public void setCurrentTask(TeamPlayer teamPlayer, Integer ID)
	{
		currentTaskIDs.put(teamPlayer, ID);
	}

	public boolean hasCurrentTask(TeamPlayer teamPlayer)
	{
		return currentTaskIDs.containsKey(teamPlayer);
	}

	public boolean canRally(TeamPlayer teamPlayer)
	{
		return !rallyUsed.contains(teamPlayer.getName());
	}

	public void setRallyUsedFor(TeamPlayer teamPlayer)
	{
		rallyUsed.add(teamPlayer.getName());
	}

	public void clearTeamRally(ITeam team)
	{
		for (String player : team.getPlayers())
		{
			rallyUsed.remove(player);
		}
	}

	public void removeCurrentTask(TeamPlayer teamPlayer)
	{
		bukkitScheduler.cancelTask(currentTaskIDs.remove(teamPlayer));
	}

	public void teleport(TeamPlayer teamPlayer, ILocatable toLocation)
	{
		if (hasNearbyEnemies(teamPlayer))
			delayTeleportTo(teamPlayer, toLocation);
		else
			teleportTo(teamPlayer, toLocation);
	}

	private void delayTeleportTo(final TeamPlayer teamPlayer, final ILocatable toLocatable)
	{
		countWaitTime.put(teamPlayer, 0);
		final Location currentLocation = teamPlayer.getLocation();
		teamPlayer.sendMessage(MessageUtil.negativeMessage("You cannot teleport with enemies nearby"));
		teamPlayer.sendMessage(MessageUtil.negativeMessage("You must wait " + Configuration.TELE_DELAY + " seconds"));
		Runnable teleportWait = new TeleportWait(teamPlayer, toLocatable, currentLocation);
		setCurrentTask(teamPlayer, bukkitScheduler.scheduleSyncRepeatingTask(teamPlugin, teleportWait, CommonUtil.LONG_ZERO, 2L));
	}

	private void teleportTo(final TeamPlayer teamPlayer, final ILocatable toLocatable)
	{
		final Location toLocation = toLocatable.getLocation();
		final Location rallyLocation = teamPlayer.getTeam().getRally();
		if (toLocation.equals(rallyLocation))
		{
			setRallyUsedFor(teamPlayer);
		}
		else
		{
			Runnable teleRefreshMessage = new TeleportRefreshMessage(teamPlayer);
			bukkitScheduler.scheduleSyncDelayedTask(teamPlugin, teleRefreshMessage, Configuration.TELE_REFRESH_DELAY * BukkitUtil.ONE_SECOND_IN_TICKS);
			teamPlayer.setLastTeleported(System.currentTimeMillis());
		}
		teamPlayer.teleport(toLocation);
		teamPlayer.sendMessage("You've been " + MessageUtil.positiveMessage("teleported") + " to " + toLocatable.getName());
		if (toLocatable instanceof TeamPlayer)
		{
			TeamPlayer toPlayer = CommonUtil.assignFromType(toLocatable, TeamPlayer.class);
			toPlayer.sendMessage(teamPlayer.getName() + " has " + MessageUtil.positiveMessage("teleported") + " to you");
		}
	}

	private boolean hasNearbyEnemies(TeamPlayer entity)
	{
		List<Entity> entities = entity.getNearbyEntities(Configuration.ENEMY_PROX);
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
				ITeamPlayer otherPlayer = playerFactory.getPlayer(unknownPlayer);
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
			if (Configuration.TELE_REFRESH_DELAY > 0)
				fromEntity.sendMessage("Teleport " + MessageUtil.positiveMessage("refreshed"));
		}
	}

	class TeleportWait implements Runnable
	{
		private TeamPlayer fromEntity;
		private ILocatable toLocatable;
		private Location previousLocation;

		public TeleportWait(TeamPlayer fromEntity, ILocatable toLocatable, Location previousLocation)
		{
			this.fromEntity = fromEntity;
			this.toLocatable = toLocatable;
			this.previousLocation = previousLocation;
		}

		@Override
		public void run()
		{
			if ((System.currentTimeMillis() - fromEntity.getLastAttacked()) / 1000 < Configuration.LAST_ATTACKED_DELAY)
			{
				fromEntity.sendMessage("Teleport " + MessageUtil.negativeMessage("cancelled") + "! You were attacked!");
				countWaitTime.remove(fromEntity);
				removeCurrentTask(fromEntity);
			}
			Location loc = fromEntity.getLocation();
			if (loc.getBlockX() != previousLocation.getBlockX() || loc.getBlockY() != previousLocation.getBlockY() || loc.getBlockZ() != previousLocation.getBlockZ())
			{
				fromEntity.sendMessage(MessageUtil.negativeMessage("Teleport cancelled! You moved!"));
				countWaitTime.remove(fromEntity);
				removeCurrentTask(fromEntity);
			}
			if (hasCurrentTask(fromEntity))
			{
				int temp = countWaitTime.remove(fromEntity);
				if (temp == Configuration.TELE_DELAY * 10)
				{
					teleportTo(fromEntity, toLocatable);
					removeCurrentTask(fromEntity);
				}
				temp++;
				countWaitTime.put(fromEntity, temp);
			}
		}
	}
}
