package me.protocos.xteam.listener;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.TeamPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
		try
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
				//				else if (entity instanceof Wolf && Data.TEAM_WOLVES)
				//				{
				//					// Player hurt Wolf
				//					if (damager instanceof Player)
				//					{
				//						TeamPlayer sender = new TeamPlayer((Player) damager);
				//						TeamWolf wolf = new TeamWolf((Wolf) entity);
				//						if (sender.hasTeam() && wolf.hasTeam() && sender.getTeam().equals(wolf.getTeam()))
				//						{
				//							event.setCancelled(true);
				//							if (wolf.getOwner().equals(sender))
				//							{
				//								sender.sendMessage(ChatColor.GREEN + "You" + ChatColor.RESET + " pet your wolfie!");
				//							}
				//							else
				//							{
				//								ITeamPlayer owner = wolf.getOwner();
				//								if (owner.isOnline())
				//									owner.sendMessage(ChatColor.GREEN + sender.getName() + ChatColor.RESET + " pet your wolfie!");
				//								sender.sendMessage(ChatColor.GREEN + "You" + ChatColor.RESET + " pet " + ChatColor.GREEN + owner.getName() + "'s" + ChatColor.RESET + " wolfie!");
				//							}
				//						}
				//					}
				//					// Projectile hurt Wolf
				//					else if (damager instanceof Projectile)
				//					{
				//						TeamPlayer sender;
				//						if (((Projectile) damager).getShooter() instanceof Player)
				//							sender = new TeamPlayer((Player) ((Projectile) damager).getShooter());
				//						else
				//							return;
				//						TeamWolf wolf = new TeamWolf((Wolf) entity);
				//						if (sender.hasTeam() && wolf.hasTeam() && sender.getTeam().equals(wolf.getTeam()))
				//						{
				//							event.setCancelled(true);
				//						}
				//					}
				//					else
				//						return;
				//				}
			}
		}
		catch (Exception e)
		{
			xTeam.logger.exception(e);
			xTeam.log.info("[ERROR] Exception in xTeam onEntityDamage() class [check logs]");
		}
	}
}
