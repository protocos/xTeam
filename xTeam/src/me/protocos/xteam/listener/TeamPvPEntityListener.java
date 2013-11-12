package me.protocos.xteam.listener;

import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Data;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class TeamPvPEntityListener implements Listener
{
	private static void checkTeam(EntityDamageEvent event, ITeamPlayer attacker, ITeamPlayer defender)
	{
		if (attacker.isOnSameTeam(defender))
		{
			event.setCancelled(true);
		}
		else
		{
			ITeamPlayer player = xTeamPlugin.getInstance().getPlayerManager().getPlayer(defender.getName());
			player.setLastAttacked(System.currentTimeMillis());
			//			if (Data.SPOUT_ENABLED && Data.REVEAL_TIME > 0L)
			//				if (Data.SPOUT_ENABLED && Data.REVEAL_TIME > 0L)
			//				{
			//					SpoutManager.getPlayer(attacker.getOnlinePlayer()).resetTitleFor(SpoutManager.getPlayer(defender.getOnlinePlayer()));
			//					SpoutManager.getPlayer(defender.getOnlinePlayer()).resetTitleFor(SpoutManager.getPlayer(attacker.getOnlinePlayer()));
			//					BukkitUtil.getScheduler().scheduleSyncDelayedTask(BukkitUtil.getPluginManager().getPlugin("xTeamPlugin"), new Runnable()
			//					{
			//						@Override
			//						public void run()
			//						{
			//							Functions.updatePlayers();
			//						}
			//					}, Data.REVEAL_TIME * 20L);
			//				}
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
					ITeamPlayer attacker = null;
					ITeamPlayer defender = null;
					// Player hurt Player
					if (damager instanceof Player)
					{
						attacker = xTeamPlugin.getInstance().getPlayerManager().getPlayer((Player) damager);
						defender = xTeamPlugin.getInstance().getPlayerManager().getPlayer((Player) entity);
						checkTeam(event, attacker, defender);
					}
					// Projectile hurt Player
					else if (damager instanceof Projectile)
					{
						if (((Projectile) damager).getShooter() instanceof Player)
							attacker = xTeamPlugin.getInstance().getPlayerManager().getPlayer((Player) ((Projectile) damager).getShooter());
						else
							return;
						defender = xTeamPlugin.getInstance().getPlayerManager().getPlayer((Player) entity);
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
				//						ITeamPlayer sender = xTeamPlugin.getInstance().getPlayerManager().getPlayer((Player) damager);
				//						TeamWolf wolf = new TeamWolf((Wolf) entity);
				//						if (sender.hasTeam() && wolf.hasTeam() && sender.getTeam().equals(wolf.getTeam()))
				//						{
				//							mockEvent.setCancelled(true);
				//							if (wolf.getOwner().equals(sender))
				//							{
				//								sender.sendMessage(ChatColorUtil.formatPositive("You") + ChatColor.RESET + " pet your wolfie!");
				//							}
				//							else
				//							{
				//								ITeamPlayer owner = wolf.getOwner();
				//								if (owner.isOnline())
				//									owner.sendMessage(ChatColor.GREEN + sender.getName() + ChatColor.RESET + " pet your wolfie!");
				//								sender.sendMessage(ChatColorUtil.formatPositive("You") + ChatColor.RESET + " pet " + ChatColor.GREEN + owner.getName() + "'s" + ChatColor.RESET + " wolfie!");
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
				//							mockEvent.setCancelled(true);
				//						}
				//					}
				//					else
				//						return;
				//				}
			}
		}
		catch (Exception e)
		{
			xTeamPlugin.getInstance().getLog().exception(e);
			xTeamPlugin.getInstance().getLog().info("[ERROR] Exception in xTeamPlugin onEntityDamage() class [check logs]");
		}
	}
}
