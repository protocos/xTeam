package me.protocos.xteam.listener;

import me.protocos.xteam.core.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.getspout.spoutapi.SpoutManager;

public class TeamPvPEntityListener implements Listener
{
	private static void checkTeam(EntityDamageEvent event, TeamPlayer attacker, TeamPlayer defender)
	{
		if (attacker.isOnSameTeam(defender))
		{
			event.setCancelled(true);
		}
		else
		{
			if (Data.taskIDs.containsKey(defender.getName()))
			{
				Data.damagedByPlayer.add(defender.getName());
			}
			Data.lastAttacked.put(defender.getName(), System.currentTimeMillis());
			if (Data.SPOUT_ENABLED && Data.REVEAL_TIME > 0L)
				if (Data.SPOUT_ENABLED && Data.REVEAL_TIME > 0L)
				{
					SpoutManager.getPlayer(attacker.getOnlinePlayer()).resetTitleFor(SpoutManager.getPlayer(defender.getOnlinePlayer()));
					SpoutManager.getPlayer(defender.getOnlinePlayer()).resetTitleFor(SpoutManager.getPlayer(attacker.getOnlinePlayer()));
					Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
					{
						@Override
						public void run()
						{
							Functions.updatePlayers();
						}
					}, Data.REVEAL_TIME * 20L);
				}
		}
	}
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (event.isCancelled())
		{
			return;
		}
		if (event instanceof EntityDamageByEntityEvent)
		{
			EntityDamageByEntityEvent entEvent = (EntityDamageByEntityEvent) event;
			Entity damager = entEvent.getDamager();
			Entity entity = entEvent.getEntity();
			if (Data.DISABLED_WORLDS.contains(damager.getWorld().getName()) && Data.DISABLED_WORLDS.contains(entity.getWorld().getName()))
			{
				return;
			}

			if (entity instanceof Player)
			{
				TeamPlayer attacker = null;
				TeamPlayer defender = null;
				// Player hurt Player
				if (damager instanceof Player)
				{
					attacker = new TeamPlayer((Player) damager);
					defender = new TeamPlayer((Player) entity);
					checkTeam(event, attacker, defender);
				}
				// Projectile hurt Player
				else if (damager instanceof Projectile)
				{
					if (((Projectile) damager).getShooter() instanceof Player)
						attacker = new TeamPlayer((Player) ((Projectile) damager).getShooter());
					else
						return;
					defender = new TeamPlayer((Player) entity);
					checkTeam(event, attacker, defender);
				}
				else
					return;
			}
			else if (entity instanceof Wolf && Data.TEAM_WOLVES)
			{
				// Player hurt Wolf
				if (damager instanceof Player)
				{
					TeamPlayer player = new TeamPlayer((Player) damager);
					TeamWolf wolf = new TeamWolf((Wolf) entity);
					if (player.hasTeam() && wolf.hasTeam() && player.getTeam().equals(wolf.getTeam()))
					{
						event.setCancelled(true);
						if (wolf.getOwner().equals(player))
						{
							player.sendMessage(ChatColor.GREEN + "You" + ChatColor.WHITE + " pet your wolfie!");
						}
						else
						{
							ITeamPlayer owner = wolf.getOwner();
							if (owner.isOnline())
								owner.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " pet your wolfie!");
							player.sendMessage(ChatColor.GREEN + "You" + ChatColor.WHITE + " pet " + ChatColor.GREEN + owner.getName() + "'s" + ChatColor.WHITE + " wolfie!");
						}
					}
				}
				// Projectile hurt Wolf
				else if (damager instanceof Projectile)
				{
					TeamPlayer player;
					if (((Projectile) damager).getShooter() instanceof Player)
						player = new TeamPlayer((Player) ((Projectile) damager).getShooter());
					else
						return;
					TeamWolf wolf = new TeamWolf((Wolf) entity);
					if (player.hasTeam() && wolf.hasTeam() && player.getTeam().equals(wolf.getTeam()))
					{
						event.setCancelled(true);
					}
				}
				else
					return;
			}
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// if (entEvent.getEntity() instanceof Wolf && Data.TEAM_WOLVES)
			// {
			// Wolf wolfie = (Wolf) entEvent.getEntity();
			// Player owner = (Player) wolfie.getOwner();
			// if (entEvent.getDamager() instanceof Player)
			// {
			// Player teamPlayer = (Player) entEvent.getDamager();
			// if (owner != null && wolfie.getOwner().equals(teamPlayer))
			// {
			// teamPlayer.sendMessage(ChatColor.GREEN + "You" + ChatColor.WHITE +
			// " pet your wolfie!");
			// event.setCancelled(true);
			// }
			// else if (owner != null && xTeam.tm.getTeam(owner) != null &&
			// xTeam.tm.getTeam(owner).getPlayers().contains(teamPlayer.getName()))
			// {
			// owner.sendMessage(ChatColor.GREEN + teamPlayer.getName() +
			// ChatColor.WHITE + " pet your wolfie!");
			// teamPlayer.sendMessage(ChatColor.GREEN + "You" + ChatColor.WHITE +
			// " pet " + ChatColor.GREEN + owner.getName() + "'s" +
			// ChatColor.WHITE + " wolfie!");
			// event.setCancelled(true);
			// }
			// }
			// else if (entEvent.getDamager() instanceof Projectile)
			// {
			// if (((Projectile) entEvent.getDamager()).getShooter() instanceof
			// Player)
			// {
			// Player teamPlayer = (Player) ((Projectile)
			// entEvent.getDamager()).getShooter();
			// if (owner != null && wolfie.getOwner().equals(teamPlayer))
			// {
			// event.setCancelled(true);
			// event.setDamage(0);
			// }
			// else if (owner != null && (new TeamPlayer(owner)).getTeam() !=
			// null && (new
			// TeamPlayer(owner)).getTeam().getPlayers().contains(teamPlayer.getName()))
			// {
			// event.setCancelled(true);
			// }
			// }
			// else
			// return;
			// }
			// return;
			// }
			// if (entEvent.getDamager() instanceof Projectile &&
			// entEvent.getEntity() instanceof Player)
			// {
			// if (((Projectile) entEvent.getDamager()).getShooter() instanceof
			// Player)
			// {
			// attacker = (Player) ((Projectile)
			// entEvent.getDamager()).getShooter();
			// defender = (Player) entEvent.getEntity();
			// }
			// else
			// return;
			// }
			// else if (entEvent.getDamager() instanceof Player &&
			// entEvent.getEntity() instanceof Player)
			// {
			// attacker = (Player) entEvent.getDamager();
			// defender = (Player) entEvent.getEntity();
			// }
			// else
			// return;
			// if (Data.DISABLED_WORLDS.contains(attacker.getWorld().getName())
			// && Data.DISABLED_WORLDS.contains(defender.getWorld().getName()))
			// {
			// return;
			// }
			// Team attTeam = xTeam.tm.getTeam(attacker);
			// Team defTeam = xTeam.tm.getTeam(defender);
			// try
			// {
			// if (attTeam.getName().equalsIgnoreCase(defTeam.getName()) &&
			// !Data.TEAM_FRIENDLY_FIRE)
			// {
			// event.setCancelled(true);
			// return;
			// }
			// throw new NullPointerException();
			// }
			// catch (NullPointerException n)
			// {
			// if (Data.taskIDs.containsKey(defender.getName()))
			// {
			// Data.damagedByPlayer.add(defender.getName());
			// }
			// Data.lastAttacked.put(defender.getName(),
			// System.currentTimeMillis());
			// }
			// if (Data.SPOUT && Data.REVEAL_TIME > 0L)
			// {
			// SpoutManager.getPlayer(attacker).resetTitleFor(SpoutManager.getPlayer(defender));
			// SpoutManager.getPlayer(defender).resetTitleFor(SpoutManager.getPlayer(attacker));
			// Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"),
			// new Runnable()
			// {
			// public void run()
			// {
			// updatePlayers();
			// }
			// }, Data.REVEAL_TIME * 20L);
			// }
		}
	}
}
