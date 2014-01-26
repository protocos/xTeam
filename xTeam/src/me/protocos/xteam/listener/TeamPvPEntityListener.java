package me.protocos.xteam.listener;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.entity.ITeamPlayer;
import me.protocos.xteam.configuration.Configuration;
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
			ITeamPlayer player = xTeam.getInstance().getPlayerManager().getPlayer(defender.getName());
			player.setLastAttacked(System.currentTimeMillis());
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{
		if (event.isCancelled())
		{
			return;
		}
		try
		{
			Entity damager = event.getDamager();
			Entity entity = event.getEntity();
			if (Configuration.DISABLED_WORLDS.contains(damager.getWorld().getName()) && Configuration.DISABLED_WORLDS.contains(entity.getWorld().getName()))
			{
				return;
			}

			if (Configuration.TEAM_FRIENDLY_FIRE)
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
					attacker = xTeam.getInstance().getPlayerManager().getPlayer((Player) damager);
					defender = xTeam.getInstance().getPlayerManager().getPlayer((Player) entity);
					checkTeam(event, attacker, defender);
				}
				// Projectile hurt Player
				else if (damager instanceof Projectile)
				{
					if (((Projectile) damager).getShooter() instanceof Player)
						attacker = xTeam.getInstance().getPlayerManager().getPlayer((Player) ((Projectile) damager).getShooter());
					else
						return;
					defender = xTeam.getInstance().getPlayerManager().getPlayer((Player) entity);
					checkTeam(event, attacker, defender);
				}
				else
					return;
			}
		}
		catch (Exception e)
		{
			xTeam.getInstance().getLog().exception(e);
			xTeam.getInstance().getLog().info("[ERROR] Exception in xTeam onEntityDamage() class [check logs]");
		}
	}
}
