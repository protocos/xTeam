package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import java.util.List;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class UserReturn extends UserCommand
{
	public UserReturn()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		List<Entity> nearbyEntities = teamPlayer.getOnlinePlayer().getNearbyEntities(Data.ENEMY_PROX, 5.0D, Data.ENEMY_PROX);
		for (Entity entity : nearbyEntities)
		{
			if (Functions.isEnemy(teamPlayer, entity))
			{
				delayTele();
				return;
			}
		}
		tele();
		originalSender.sendMessage(ChatColor.GREEN + "WHOOSH!");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		Location loc = Data.returnLocations.get(teamPlayer.getOnlinePlayer());
		if (loc == null)
		{
			throw new TeamPlayerHasNoReturnException();
		}
		if (teamPlayer.getOnlinePlayer().getNoDamageTicks() > 0)
		{
			throw new TeamPlayerDyingException();
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
	private void delayTele()
	{
		final TeamPlayer finalPlayer = teamPlayer;
		final Location finalLocation = teamPlayer.getLocation();
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
					finalPlayer.sendMessage(ChatColor.RED + "UserTeleport cancelled! You were attacked!");
					Data.damagedByPlayer.remove(finalPlayer.getName());
					Data.countWaitTime.remove(finalPlayer.getName());
					Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
				}
				Location loc1 = finalPlayer.getLocation();
				if (loc1.getBlockX() != finalLocation.getBlockX() || loc1.getBlockY() != finalLocation.getBlockY() || loc1.getBlockZ() != finalLocation.getBlockZ())
				{
					finalPlayer.sendMessage(ChatColor.RED + "UserTeleport cancelled! You moved!");
					Data.countWaitTime.remove(finalPlayer.getName());
					Data.BUKKIT.getScheduler().cancelTask(Data.taskIDs.remove(finalPlayer.getName()));
				}
				if (Data.taskIDs.containsKey(finalPlayer.getName()))
				{
					int temp = Data.countWaitTime.remove(finalPlayer.getName());
					if (temp == Data.TELE_DELAY * 10)
					{
						tele();
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
		return patternOneOrMore("return") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.return";
	}
	@Override
	public String getUsage()
	{
		return "/team return";
	}
	private void tele()
	{
		teamPlayer.teleport(Data.returnLocations.remove(teamPlayer.getOnlinePlayer()));
	}
}
