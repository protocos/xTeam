package me.protocos.xteam.listener;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.model.ILog;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class TeamPvPEntityListener implements Listener
{
	private ILog log;
	private IPlayerFactory playerFactory;

	public TeamPvPEntityListener(XTeam xteam)
	{
		this.log = xteam.getLog();
		this.playerFactory = xteam.getPlayerFactory();
	}

	private static void checkTeam(EntityDamageEvent event, ITeamPlayer attacker, ITeamPlayer defender)
	{
		if (Configuration.TEAM_FRIENDLY_FIRE)
		{
			defender.setLastAttacked(System.currentTimeMillis());
		}
		else if (attacker.isOnSameTeam(defender))
		{
			event.setCancelled(true);
		}
		else
		{
			defender.setLastAttacked(System.currentTimeMillis());
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

			if (entity instanceof Player)
			{
				ITeamPlayer attacker = null;
				ITeamPlayer defender = null;
				// Player hurt Player
				if (damager instanceof Player)
				{
					attacker = playerFactory.getPlayer((Player) damager);
					defender = playerFactory.getPlayer((Player) entity);
					checkTeam(event, attacker, defender);
				}
				// Projectile hurt Player
				else if (damager instanceof Projectile)
				{
					if (((Projectile) damager).getShooter() instanceof Player)
						attacker = playerFactory.getPlayer((Player) ((Projectile) damager).getShooter());
					else
						return;
					defender = playerFactory.getPlayer((Player) entity);
					checkTeam(event, attacker, defender);
				}
				else
					return;
			}
		}
		catch (Exception e)
		{
			log.exception(e);
		}
	}
}
