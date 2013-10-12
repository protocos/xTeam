package me.protocos.xteam.command.action;

import java.util.List;
import me.protocos.xteam.api.core.ITeamEntity;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamPlayer;
import org.bukkit.entity.*;

public class TeleportAction
{
	private TeleportAction teleporter;

	private TeleportAction()
	{

	}
	public TeleportAction getInstance()
	{
		if (teleporter == null)
		{
			teleporter = new TeleportAction();
		}
		return teleporter;
	}
	public boolean teleport(ITeamPlayer fromEntity, ITeamPlayer toEntity)
	{
		if (fromEntity.isVulnerable() && hasNearbyEnemies(fromEntity))
		{

		}
		return false;
	}
	private boolean hasNearbyEnemies(ITeamPlayer entity)
	{
		List<Entity> entities = entity.getNearbyEntities(Data.ENEMY_PROX);
		for (Entity e : entities)
		{
			if (e instanceof Monster
					|| e instanceof Ghast
					|| e instanceof EnderDragon
					|| e instanceof Slime)
			{
				return true;
			}
			else if (e instanceof Golem
					|| e instanceof Wolf)
			{
				if (aggroCheck(entity, e))
					return true;
			}
			else if (e instanceof Player)
			{

			}
		}
		return false;
	}
	private TeamPlayer getClosestTeammate(ITeamEntity entity)
	{
		return null;
	}
	private boolean aggroCheck(Entity entity, Entity creature)
	{
		if (creature instanceof Creature)
		{
			Entity target = ((Creature) creature).getTarget();
			if (entity.equals(target))
				return true;
		}
		return false;
	}
}
