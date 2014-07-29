package me.protocos.xteam.listener;

import java.util.HashSet;
import java.util.Set;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.model.ITeamEntityRelationCriterion;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TeamFriendlyFireListener implements Listener
{
	private static final Set<ITeamEntityRelationCriterion> friendlyCriteria = new HashSet<ITeamEntityRelationCriterion>();

	public static void addFriendlyCriterion(ITeamEntityRelationCriterion criterion)
	{
		friendlyCriteria.add(criterion);
	}

	private ILog log;
	private IPlayerFactory playerFactory;

	public TeamFriendlyFireListener(TeamPlugin teamPlugin)
	{
		this.log = teamPlugin.getLog();
		this.playerFactory = teamPlugin.getPlayerFactory();
		addFriendlyCriterion(new ITeamEntityRelationCriterion()
		{
			@Override
			public boolean passes(ITeamEntity entity1, ITeamEntity teamEntity2)
			{
				return entity1.isOnSameTeam(teamEntity2);
			}
		});
	}

	@EventHandler
	public boolean onEntityDamage(EntityDamageByEntityEvent event)
	{
		try
		{
			if (event.isCancelled())
				return false;
			ITeamPlayer attacker = getAttacker(playerFactory, event);
			ITeamPlayer defender = getDefender(playerFactory, event);
			if (attacker != null && defender != null)
			{
				if (Configuration.DISABLED_WORLDS.contains(attacker.getWorld().getName()) || Configuration.DISABLED_WORLDS.contains(defender.getWorld().getName()))
					return false;
				if (!Configuration.TEAM_FRIENDLY_FIRE)
				{
					for (ITeamEntityRelationCriterion criterion : friendlyCriteria)
					{
						if (criterion.passes(attacker, defender))
						{
							event.setCancelled(true);
							return false;
						}
					}
				}
				defender.setLastAttacked(System.currentTimeMillis());
			}
			return true;
		}
		catch (Exception e)
		{
			log.exception(e);
		}
		return false;
	}

	public static ITeamPlayer getAttacker(IPlayerFactory playerFactory, EntityDamageByEntityEvent event)
	{
		Entity damager = event.getDamager();
		if (damager instanceof Player)
		{
			return playerFactory.getPlayer(CommonUtil.assignFromType(damager, Player.class));
		}
		else if (damager instanceof Projectile)
		{
			Projectile projectile = CommonUtil.assignFromType(damager, Projectile.class);
			LivingEntity livingEntity = projectile.getShooter();
			if (livingEntity instanceof Player)
			{
				return playerFactory.getPlayer(CommonUtil.assignFromType(livingEntity, Player.class));
			}
		}
		return null;
	}

	public static ITeamPlayer getDefender(IPlayerFactory playerFactory, EntityDamageByEntityEvent event)
	{
		Entity entity = event.getEntity();
		if (entity instanceof Player)
		{
			return playerFactory.getPlayer(CommonUtil.assignFromType(entity, Player.class));
		}
		return null;
	}
}

class SameTeamRelationCriterion implements ITeamEntityRelationCriterion
{
	@Override
	public boolean passes(ITeamEntity entity1, ITeamEntity teamEntity2)
	{
		return entity1.isOnSameTeam(teamEntity2);
	}
}
