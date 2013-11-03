package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserTeleport extends UserCommand
{
	private TeamPlayer fromPlayer;
	private TeamPlayer toPlayer;

	public UserTeleport()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		teleporter.teleport(fromPlayer, toPlayer);
		originalSender.sendMessage(ChatColor.GREEN + "You've been teleported to " + toPlayer.getName());
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		String teammateName = null;
		super.checkRequirements(originalSender, parseCommand);
		if (parseCommand.size() == 2)
		{
			teammateName = parseCommand.get(1);
		}
		else
		{
			teammateName = "";
		}
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (teamPlayer.isDamaged())
		{
			throw new TeamPlayerDyingException();
		}
		long timeSinceLastTeleport = CommonUtil.getElapsedTimeSince(teamPlayer.getLastTeleported());
		if (timeSinceLastTeleport < Data.TELE_REFRESH_DELAY)
		{
			String error = "Player cannot teleport within " + Data.TELE_REFRESH_DELAY + " seconds of last teleport\nYou must wait " + (Data.TELE_REFRESH_DELAY - timeSinceLastTeleport) + " more seconds";
			if (teamPlayer.hasReturnLocation() && teamPlayer.hasPermission(getPermissionNode()))
				error += "\nYou can still use /team return";
			throw new TeamPlayerTeleException(error);
		}
		long timeSinceLastAttacked = CommonUtil.getElapsedTimeSince(teamPlayer.getLastAttacked());
		if (timeSinceLastAttacked < Data.LAST_ATTACKED_DELAY)
		{
			throw new TeamPlayerTeleException("Player was attacked in the last " + Data.LAST_ATTACKED_DELAY + " seconds\nYou must wait " + (Data.LAST_ATTACKED_DELAY - timeSinceLastAttacked) + " more seconds");
		}
		if (teamPlayer.getName().equals(teammateName))
		{
			throw new TeamPlayerTeleException("Player cannot teleport to themselves");
		}
		fromPlayer = teamPlayer;
		if ("".equals(teammateName))
		{
			if (fromPlayer.getOnlineTeammates().isEmpty())
			{
				throw new TeamPlayerTeammateException("Player has no teammates online");
			}
			toPlayer = TeleportScheduler.getInstance().getClosestTeammate(fromPlayer);
			//			teammateName = toPlayer.getName();
		}
		else
		{
			if (!team.getPlayers().contains(teammateName))
			{
				throw new TeamPlayerNotTeammateException();
			}
			for (TeamPlayer teammate : teamPlayer.getOnlineTeammates())
			{
				if (teammateName.equalsIgnoreCase(teammate.getName()))
				{
					toPlayer = teammate;
					break;
				}
			}
		}
		if (TeleportScheduler.getInstance().hasCurrentTask(teamPlayer))
		{
			throw new TeamPlayerTeleRequestException();
		}
		if (!teamPlayer.getLocation().getWorld().equals(toPlayer.getLocation().getWorld()) && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeammateException("Teammate is in a different world");
		}
		if (teamPlayer.getLocation().distance(toPlayer.getLocation()) > Data.TELE_RADIUS && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeammateException("There are no teammates near you\nClosest teammate: " + toPlayer.getName() + " @ " + (int) Math.ceil(teamPlayer.getLocation().distance(toPlayer.getLocation())) + " blocks away");
		}
		if (!toPlayer.isOnline())
		{
			throw new TeamPlayerTeammateException("Player teammate is not online");
		}
		if (teamPlayer.getLocation().distance(toPlayer.getLocation()) > Data.TELE_RADIUS && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeleException(toPlayer.getName() + " is too far away @ " + (int) Math.ceil(teamPlayer.getLocation().distance(toPlayer.getLocation())) + " blocks away");
		}
	}
	//	private void delayTeleTO(final TeamPlayer playerTele, TeamPlayer other)
	//	{
	//		final TeamPlayer finalPlayer = playerTele;
	//		final TeamPlayer finalTeammate = other;
	//		Data.countWaitTime.put(finalPlayer.getName(), 0);
	//		final Location save = finalPlayer.getLocation();
	//		finalPlayer.sendMessage(ChatColor.RED + "You cannot teleport with enemies nearby");
	//		finalPlayer.sendMessage(ChatColor.RED + "You must wait 10 seconds");
	//		Data.taskIDs.put(finalPlayer.getName(), Data.BUKKIT.getScheduler().scheduleSyncRepeatingTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
	//		{
	//			@Override
	//			public void run()
	//			{
	//				if (Data.damagedByPlayer.contains(finalPlayer.getName()))
	//				{
	//					finalPlayer.sendMessage(ChatColor.RED + "Teleport cancelled! You were attacked!");
	//					Data.damagedByPlayer.remove(finalPlayer.getName());
	//					Data.countWaitTime.remove(finalPlayer.getName());
	//					Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
	//				}
	//				Location loc = finalPlayer.getLocation();
	//				if (loc.getBlockX() != save.getBlockX() || loc.getBlockY() != save.getBlockY() || loc.getBlockZ() != save.getBlockZ())
	//				{
	//					finalPlayer.sendMessage(ChatColor.RED + "Teleport cancelled! You moved!");
	//					Data.countWaitTime.remove(finalPlayer.getName());
	//					Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
	//				}
	//				if (Data.taskIDs.containsKey(finalPlayer.getName()))
	//				{
	//					int temp = Data.countWaitTime.remove(finalPlayer.getName());
	//					if (temp == Data.TELE_DELAY * 10)
	//					{
	//						teleTO(finalPlayer, finalTeammate);
	//						Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
	//					}
	//					temp++;
	//					Data.countWaitTime.put(finalPlayer.getName(), temp);
	//				}
	//			}
	//		}, 0L, 2L));
	//	}
	//	private TeamPlayer getClosestTeammate()
	//	{
	//		List<TeamPlayer> onlineTeammates = teamPlayer.getOnlineTeammates();
	//		TeamPlayer closestTeammate = onlineTeammates.get(0);
	//		double distance = teamPlayer.getOnlinePlayer().getLocation().distance(closestTeammate.getOnlinePlayer().getLocation());
	//		for (ITeamPlayer mate : onlineTeammates)
	//		{
	//			double tempDistance = teamPlayer.getDistanceTo(mate);
	//			if (tempDistance < distance)
	//			{
	//				closestTeammate = mate;
	//				distance = tempDistance;
	//			}
	//		}
	//		return closestTeammate;
	//	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("teleport") + "(" + WHITE_SPACE + ANY_CHARS + ")?" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.tele";
	}
	@Override
	public String getUsage()
	{
		return "/team tele {Player}";
	}
	//	private void tele(String teammate)
	//	{
	//		ITeamPlayer mate = PlayerManager.getPlayer(teammate);
	//		List<Entity> nearbyEntities = teamPlayer.getOnlinePlayer().getNearbyEntities(Data.ENEMY_PROX, 5.0D, Data.ENEMY_PROX);
	//		for (Entity entity : nearbyEntities)
	//		{
	//			if (Functions.isEnemy(teamPlayer, entity))
	//			{
	//				delayTeleTO(teamPlayer, mate);
	//				return;
	//			}
	//		}
	//		teleTO(teamPlayer, mate);
	//	}
	//	private void teleTO(final TeamPlayer playerTele, TeamPlayer other)
	//	{
	//		Data.returnLocations.put(playerTele.getOnlinePlayer(), playerTele.getLocation());
	//		Data.hasTeleported.put(playerTele.getName(), Long.valueOf(System.currentTimeMillis()));
	//		Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
	//		{
	//			@Override
	//			public void run()
	//			{
	//				Data.hasTeleported.remove(playerTele.getName());
	//				if (Data.TELE_REFRESH_DELAY > 0)
	//					playerTele.sendMessage(ChatColor.GREEN + "Teleporting ability refreshed");
	//			}
	//		}, Data.TELE_REFRESH_DELAY * 20L);
	//		playerTele.teleport(other.getLocation());
	//		playerTele.sendMessage("You've been teleported to " + ChatColor.GREEN + other.getName());
	//		Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
	//		{
	//			@Override
	//			public void run()
	//			{
	//				Functions.updatePlayers();
	//			}
	//		}, 20L);
	//	}
}
