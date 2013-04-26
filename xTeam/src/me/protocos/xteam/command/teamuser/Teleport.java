package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Teleport extends BaseUserCommand
{
	private String playerName;

	public Teleport()
	{
		super();
	}
	public Teleport(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		String closestTeammate = getClosestTeammate().getName();
		tele(closestTeammate);
		originalSender.sendMessage("You've been teleported to " + ChatColor.GREEN + closestTeammate);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 1)
		{
		}
		else if (parseCommand.size() == 2)
		{
			playerName = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (teamPlayer.getOnlinePlayer().getNoDamageTicks() > 0)
		{
			throw new TeamPlayerDyingException();
		}
		if (Data.hasTeleported.containsKey(teamPlayer.getName()))
		{
			String error = "Player cannot teleport within " + Data.REFRESH_DELAY + " seconds of last teleport\nYou must wait " + (Data.REFRESH_DELAY - (System.currentTimeMillis() - Data.hasTeleported.get(teamPlayer.getName())) / 1000) + " more seconds";
			if (Data.returnLocations.containsKey(teamPlayer) && teamPlayer.hasPermission("xteam.telereturn"))
				error += "You can still use /team return";
			throw new TeamPlayerTeleException(error);
		}
		if (Data.lastAttacked.get(teamPlayer.getName()) != null && (System.currentTimeMillis() - Data.lastAttacked.get(teamPlayer.getName())) <= Data.LAST_ATTACKED_DELAY * 1000)
		{
			throw new TeamPlayerTeleException("Player was attacked in the last " + Data.LAST_ATTACKED_DELAY + " seconds\nYou must wait " + (Data.LAST_ATTACKED_DELAY - (System.currentTimeMillis() - Data.lastAttacked.get(teamPlayer.getName())) / 1000) + " more seconds");
		}
		if (teamPlayer.getName().equals(playerName))
		{
			throw new TeamPlayerTeleException("Player cannot teleport to themselves");
		}
		TeamPlayer teammate;
		if (playerName == null)
		{
			List<String> onlineTeammates = teamPlayer.getOnlineTeammates();
			if (onlineTeammates.isEmpty())
			{
				throw new TeamPlayerTeammateException("Player has no teammates online");
			}
			teammate = getClosestTeammate();
			playerName = teammate.getName();
		}
		else
		{
			teammate = new TeamPlayer(playerName);
		}
		if (!team.getPlayers().contains(playerName))
		{
			throw new TeamPlayerNotTeammateException();
		}
		if (Data.taskIDs.containsKey(teamPlayer.getName()))
		{
			throw new TeamPlayerTeleRequestException();
		}
		if (!teamPlayer.getLocation().getWorld().equals(teammate.getLocation().getWorld()) && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeammateException("Teammate is in a different world");
		}
		if (teamPlayer.getLocation().distance(teammate.getLocation()) > Data.TELE_RADIUS && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeammateException("There are no teammates near you\nClosest teammate: " + teammate.getName() + " @ " + (int) Math.ceil(teamPlayer.getLocation().distance(teammate.getLocation())) + " blocks away");
		}
		if (!teammate.isOnline())
		{
			throw new TeamPlayerTeammateException("Player teammate is not online");
		}
		if (teamPlayer.getLocation().distance(teammate.getLocation()) > Data.TELE_RADIUS && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeleException(teammate.getName() + " is too far away @ " + (int) Math.ceil(teamPlayer.getLocation().distance(teammate.getLocation())) + " blocks away");
		}
	}
	private void delayTeleTO(final TeamPlayer playerTele, TeamPlayer other)
	{
		final TeamPlayer finalPlayer = playerTele;
		final TeamPlayer finalTeammate = other;
		Data.countWaitTime.put(finalPlayer.getName(), 0);
		final Location save = finalPlayer.getLocation();
		finalPlayer.sendMessage(ChatColor.RED + "You cannot teleport with enemies nearby");
		finalPlayer.sendMessage(ChatColor.RED + "You must wait 10 seconds");
		Data.taskIDs.put(finalPlayer.getName(), Data.BUKKIT.getScheduler().scheduleSyncRepeatingTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
		{
			@Override
			public void run()
			{
				if (Data.damagedByPlayer.contains(finalPlayer.getName()))
				{
					finalPlayer.sendMessage(ChatColor.RED + "Teleport cancelled! You were attacked!");
					Data.damagedByPlayer.remove(finalPlayer.getName());
					Data.countWaitTime.remove(finalPlayer.getName());
					Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
				}
				Location loc = finalPlayer.getLocation();
				if (loc.getBlockX() != save.getBlockX() || loc.getBlockY() != save.getBlockY() || loc.getBlockZ() != save.getBlockZ())
				{
					finalPlayer.sendMessage(ChatColor.RED + "Teleport cancelled! You moved!");
					Data.countWaitTime.remove(finalPlayer.getName());
					Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
				}
				if (Data.taskIDs.containsKey(finalPlayer.getName()))
				{
					int temp = Data.countWaitTime.remove(finalPlayer.getName());
					if (temp == Data.TELE_DELAY * 10)
					{
						teleTO(finalPlayer, finalTeammate);
						Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
					}
					temp++;
					Data.countWaitTime.put(finalPlayer.getName(), temp);
				}
			}
		}, 0L, 2L));
	}
	private TeamPlayer getClosestTeammate()
	{
		List<String> onlineTeammates = teamPlayer.getOnlineTeammates();
		TeamPlayer closestTeammate = new TeamPlayer(onlineTeammates.get(0));
		double distance = teamPlayer.getOnlinePlayer().getLocation().distance(closestTeammate.getOnlinePlayer().getLocation());
		for (String p : onlineTeammates)
		{
			TeamPlayer mate = new TeamPlayer(p);
			double tempDistance = teamPlayer.getDistanceTo(mate);
			if (tempDistance < distance)
			{
				closestTeammate = mate;
				distance = tempDistance;
			}
		}
		return closestTeammate;
	}
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
		return baseCommand + " tele {Player}";
	}
	private void tele(String teammate)
	{
		TeamPlayer mate = new TeamPlayer(teammate);
		List<Entity> nearbyEntities = teamPlayer.getOnlinePlayer().getNearbyEntities(Data.ENEMY_PROX, 5.0D, Data.ENEMY_PROX);
		for (Entity entity : nearbyEntities)
		{
			if (Functions.isEnemy(teamPlayer, entity))
			{
				delayTeleTO(teamPlayer, mate);
				return;
			}
		}
		teleTO(teamPlayer, mate);
	}
	private void teleTO(final TeamPlayer playerTele, TeamPlayer other)
	{
		Data.returnLocations.put(playerTele.getOnlinePlayer(), playerTele.getLocation());
		Data.hasTeleported.put(playerTele.getName(), Long.valueOf(System.currentTimeMillis()));
		Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
		{
			@Override
			public void run()
			{
				Data.hasTeleported.remove(playerTele.getName());
				if (Data.REFRESH_DELAY > 0)
					playerTele.sendMessage(ChatColor.GREEN + "Teleporting ability refreshed");
			}
		}, Data.REFRESH_DELAY * 20L);
		playerTele.teleport(other.getLocation());
		Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
		{
			@Override
			public void run()
			{
				Functions.updatePlayers();
			}
		}, 20L);
	}
}
