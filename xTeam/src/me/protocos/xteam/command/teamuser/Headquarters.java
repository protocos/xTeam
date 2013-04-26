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

public class Headquarters extends BaseUserCommand
{
	public Headquarters()
	{
		super();
	}
	public Headquarters(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		List<Entity> nearbyEntities = teamPlayer.getOnlinePlayer().getNearbyEntities(Data.ENEMY_PROX, 5.0D, Data.ENEMY_PROX);
		for (Entity entity : nearbyEntities)
		{
			if (Functions.isEnemy(teamPlayer, entity))
			{
				delayTeleHQ(teamPlayer);
				return;
			}
		}
		teleHQ(teamPlayer);
		player.sendMessage(ChatColor.GREEN + "WHOOSH!");
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
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(player, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!team.hasHQ())
		{
			throw new TeamNoHeadquartersException();
		}
		if (teamPlayer.getOnlinePlayer().getNoDamageTicks() > 0)
		{
			throw new TeamPlayerDyingException();
		}
		if (Data.hasTeleported.containsKey(teamPlayer.getName()))
		{
			String error = "Player cannot teleport within " + Data.REFRESH_DELAY + " seconds of last teleport\nYou must wait " + (Data.REFRESH_DELAY - (System.currentTimeMillis() - Data.hasTeleported.get(teamPlayer.getName())) / 1000) + " more seconds";
			if (Data.returnLocations.containsKey(teamPlayer) && teamPlayer.hasPermission("xteam.telereturn"))
				error += "Player can still use /team return";
			throw new TeamPlayerTeleException(error);
		}
		if (Data.lastAttacked.get(teamPlayer.getName()) != null && (System.currentTimeMillis() - Data.lastAttacked.get(teamPlayer.getName())) <= Data.LAST_ATTACKED_DELAY * 1000)
		{
			throw new TeamPlayerTeleException("Player was attacked in the last " + Data.LAST_ATTACKED_DELAY + " seconds\nYou must wait " + (Data.LAST_ATTACKED_DELAY - (System.currentTimeMillis() - Data.lastAttacked.get(teamPlayer.getName())) / 1000) + " more seconds");
		}
		if (Data.taskIDs.containsKey(teamPlayer.getName()))
		{
			throw new TeamPlayerTeleRequestException();
		}
	}
	private void delayTeleHQ(final TeamPlayer playerTele)
	{
		final TeamPlayer finalPlayer = playerTele;
		final Location finalLocation = finalPlayer.getLocation();
		Data.countWaitTime.put(finalPlayer.getName(), Integer.valueOf(0));
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
				if (loc.getBlockX() != finalLocation.getBlockX() || loc.getBlockY() != finalLocation.getBlockY() || loc.getBlockZ() != finalLocation.getBlockZ())
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
						teleHQ(finalPlayer);
						Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
					}
					temp++;
					Data.countWaitTime.put(finalPlayer.getName(), temp);
				}
			}
		}, 0L, 2L));
	}
	@Override
	public String getPattern()
	{
		return "hq" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.hq";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " hq";
	}
	private void teleHQ(final TeamPlayer playerTele)
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
		playerTele.teleport(playerTele.getTeam().getHeadquarters());
	}
}
